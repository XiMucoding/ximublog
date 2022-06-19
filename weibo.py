# 编写 Lzk
# 时间 2021/6/28 0:03

# 爬取微博热搜

import urllib.request
from bs4 import BeautifulSoup  # 网页解析，获取数据
import re  # 正则表达式，进行文字匹配`
import urllib.request, urllib.error  # 制定URL，获取网页数据
import pymysql

def getHtml(url):
    head = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36"
    }
    # 封装请求体
    request = urllib.request.Request(url, headers=head)
    html = ""
    try:
        # 发送请求
        response = urllib.request.urlopen(request)
        # decode:对文件进行编码，就是格式化
        html = response.read().decode("utf-8")
    except urllib.error.URLError as e:
        if hasattr(e, "code"):
            print(e.code)
    return html


url = "https://www.kaolamedia.com/hot"
res = getHtml(url)

findLink = re.compile(r'<a class="keyword" data-v-246e1b4c="" href="(.*?)&amp;Refer=top"')
findTitle = re.compile(r'<a class="keyword" data-v-246e1b4c="" href=".*&amp;Refer=top".*target="_blank">(.*?)</a>')

# html爬取的文档
bs = BeautifulSoup(res, "html.parser")    # 造个靓汤
# 遍历文档获取目标部分
# 将爬取的数据存储好
data = []
for item in bs.find_all("div", class_="row"):
    item = str(item)  # 字符化，用于正则获取内容
    link = re.findall(findLink, item)
    title = re.findall(findTitle, item)
    if len(title) > 0:
        temp = []
        temp.append(title[0])
        temp.append(link[0])
        data.append(temp)

# 存进数据库

#连接数据库
conn = pymysql.connect(host = '127.0.0.1' # 连接名称，默认127.0.0.1
,user = 'root' # 用户名
,passwd='lizhikun' # 密码
,port= 3306 # 端口，默认为3306
,db= 'vueblog' # 数据库名称
,charset='utf8' # 字符编码
)
cur = conn.cursor() # 生成游标对象

# 删除表中的所有记录
sql1 = "delete from hot where hid>0"
try:
    cur.execute(sql1)  # 执行sql1语句
    conn.commit()  # 提交执行
except:
    conn.rollback()  # 如果发生错误则回滚
# 插入信息
sql2 = "insert into hot(title,link) VALUES("

for i in range(10):
    temp = sql2 + '"' + data[i][0] + '","' + data[i][1]+'")'
    try:
        cur.execute(temp)  # 执行插入的sql语句
        conn.commit()  # 提交到数据库执行
    except:
        conn.rollback()# 如果发生错误则回滚

conn.close() # 关闭数据库连接