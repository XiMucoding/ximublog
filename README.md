# ximublog（一年前写的，即是大二下暑假）
springboot+vue【前后端分离】博客项目
# 详细介绍该项目的地址
[详细介绍](https://blog.csdn.net/weixin_48557496/article/details/125353823)
# 一、技术栈
1. 【前端】vue、element-ui
2. 【后端】springboot、mybaits
3. 【数据库】mysql
4. 对象存储（部署一个minio到自己的服务器）
5. python爬虫微博热点

**用到的**
[centos安装minio](https://blog.csdn.net/qq2523208472/article/details/119007574)
[springboot配置minio上传下载删除](https://blog.csdn.net/weixin_48557496/article/details/120506154?spm=1001.2014.3001.5501)
[springboot发送邮件](https://blog.csdn.net/weixin_48557496/article/details/121034610?spm=1001.2014.3001.5501)


# 二、模块展示
> 表单都有判空、格式验证，就不展示了

## 注册页
1. 验证码发送自注册的邮箱
2. 每个邮箱自能注册一次

![在这里插入图片描述](https://img-blog.csdnimg.cn/265f9cdd428245f5b0e552fc43907a99.png)
## 登录页
![在这里插入图片描述](https://img-blog.csdnimg.cn/f0191f35b22e458d8c5d337179d34d06.png)
## 首页
![在这里插入图片描述](https://img-blog.csdnimg.cn/fb81c5f68af74a2e88f4451c1934bc4e.png)
1. 热搜索

![在这里插入图片描述](https://img-blog.csdnimg.cn/ec6a369b482c4956a024a797db227b54.png)
2. 留言
![在这里插入图片描述](https://img-blog.csdnimg.cn/72f63193e2f348968f3849c2182e43e0.png)
3. 功能项
![在这里插入图片描述](https://img-blog.csdnimg.cn/d0f8ca399d764c3cbaeb506e482849c9.png)
4. 更换头像

![在这里插入图片描述](https://img-blog.csdnimg.cn/e5d838e466e747309a48b156c60e527d.png)
5. 修改个人信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/b4ba5b2e1d8e4c8eb3682f08308402fa.png)
6. 内容管理

![在这里插入图片描述](https://img-blog.csdnimg.cn/c203b48a90f545bea06158af6cd1c9d2.png)

## 博客编写/修改页
![在这里插入图片描述](https://img-blog.csdnimg.cn/2f9b3498f676439c89ce96c51e0ba65e.png)
## 搜索页
**热点是python写的爬虫，部署到服务器，定时爬取数据**
![在这里插入图片描述](https://img-blog.csdnimg.cn/d9a023b16fd04a1d865b7eb1b1fb4213.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/e4fc06571728478dbf4f7a3db23a694f.png)

## 个人主页
![在这里插入图片描述](https://img-blog.csdnimg.cn/2c580218f4184390adbb7988fc67c379.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/307d16fe3d62472c8a1211aad64bc01c.png)
## 私信/消息页
![在这里插入图片描述](https://img-blog.csdnimg.cn/738d0515f92c48e993d06304ea13fb7d.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/9b2bfc5c52f040e589b706cf3991639c.png)
## 音乐页
![在这里插入图片描述](https://img-blog.csdnimg.cn/2c01088c0bc34418988f807fdc413fb0.png)

## 放映室
![在这里插入图片描述](https://img-blog.csdnimg.cn/3d241570ce1b4419877b7473f415f3f2.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/a7e3fd7ff2a34c69b7ceb0a041c10d7e.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/19c1728b37854c249facb7c373e1ac28.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/02d4afa684374e85913a790ec4420c49.png)

## 后台管理
![在这里插入图片描述](https://img-blog.csdnimg.cn/a0dc8d9c0b18457588f7c8076994882d.png)

### 用户管理
![在这里插入图片描述](https://img-blog.csdnimg.cn/952651f078994e629a7beb402576fbc3.png)
### 博客管理

![在这里插入图片描述](https://img-blog.csdnimg.cn/3007c3664210474eb196437765a95e0c.png)

### 公告/通知
![在这里插入图片描述](https://img-blog.csdnimg.cn/9385641f15284043971ddbfd7e2f93c1.png)
### 留言管理
![在这里插入图片描述](https://img-blog.csdnimg.cn/11de26a2083c43548b016ffec950de4f.png)
# 特别注意
1. [vueblog]前端 npm run serve启动
2. [myblog]后端 注意换成你的mysql、minio
