<template>
  <div class="bg2">
    <el-container class="content1">
      <el-aside width="30%"
        ><!-- 博主的信息 -->
        <div class="m-content1">
          <div class="block">
            <el-avatar :size="80" :src="user.avatar"></el-avatar>
            <div>{{ user.username }}</div>
          </div>
          <div class="maction" style="margin: 10px 0">
            <span><el-link href="/Shouye">首页</el-link></span>
            <el-divider direction="vertical"></el-divider>
            <span
              ><el-link type="danger" href="/my" v-show="canEdit"
                >内容管理</el-link
              ></span
            >
            <el-divider direction="vertical" v-show="canEdit"></el-divider>
            <span
              ><el-link type="success" @click="sendMessage(user.uid)"
                >私信</el-link
              ></span
            >
          </div>
        </div>
        <!-- 博主的博文 -->
        <div class="list">
          <h4 style="margin: 5px 0; line-height: 53px">博主的博文</h4>
          <hr style="border: 1px #dcdfe6 solid" />
          <div v-for="item in blogs" :key="item.aid">
            <el-link @click="getArticleByAid(item.aid)" style="margin: 5px 0">{{
              item.title
            }}</el-link>
          </div>
          <div style="width: 100%; height: 20px"></div>
        </div>
      </el-aside>
      <!-- 博客详情 -->
      <el-main>
        <h3>
          {{ blog.title }}
          <el-link :href="'edit?aid=' + blog.aid" v-if="canEdit"> 编辑</el-link>
        </h3>
        <el-divider></el-divider>
        <div class="article" style="height: 80vh">
          <!-- 注意需要给 el-scrollbar 设置高度，判断是否滚动是看它的height判断的 -->
          <div id="top"></div>
          <el-scrollbar style="height: 100%" ref="scrollbar">
            <!-- 正文 -->
            <div
              class="markdown-body"
              v-html="blog.content"
              style="
                padding: 0 10px;
                background-color: rgba(255, 253, 253, 0.3);
                text-align: left;
              "
            ></div>
            <!-- 评论框 -->
            <div class="comment1 inputBox1" v-if="isShowInput">
              <el-row>
                <el-avatar
                  :size="46"
                  :src="myself.avatar"
                  style="float: left; margin: 5px"
                ></el-avatar
                ><el-input
                  style="float: left; margin: 5px; width: 80%"
                  v-model="inputComment"
                  type="textarea"
                  :placeholder="
                    replayCid > 0
                      ? '回复' + replayName + ':'
                      : '发一条善意的评论吧...'
                  "
                ></el-input>
                <el-button
                  type="primary"
                  style="margin: 5px"
                  round
                  @click="replay()"
                  >评论</el-button
                >
              </el-row>
            </div>
            <!-- 评论区 -->
            <div class="comment1">
              <div
                v-for="item1 in CommentsList"
                :key="item1.cid"
                v-show="item1.replayCid == 0"
              >
                <!-- 父项回答 -->
                <el-row>
                  <el-avatar
                    :size="46"
                    :src="item1.avatar"
                    class="avatar"
                  ></el-avatar>
                  <p class="c_input1">
                    <span style="color: #8d9092">{{ item1.nickName }} : </span
                    >{{ item1.remark }}
                    <span
                      style="color: #8d9092; font-size: 12px; margin-left: 20px"
                    >
                      {{ item1.time }}</span
                    >
                  </p>
                  <span
                    class="replay1"
                    style="margin-top: 5px"
                    @click="pre(item1.cid, item1.nickName)"
                    v-if="canComment"
                    >回复</span
                  >
                </el-row>
                <!-- 子项回答 -->
                <div
                  v-for="item2 in CommentsList"
                  :key="item2.cid"
                  v-show="item1.cid == item2.replayCid"
                >
                  <el-row style="margin-left: 50px">
                    <el-avatar
                      :size="46"
                      :src="item2.avatar"
                      class="avatar"
                    ></el-avatar>
                    <p class="c_input1">
                      <span style="color: #8d9092"
                        >{{ item2.nickName }} @ {{ item2.replayName }} :</span
                      >{{ item2.remark
                      }}<span
                        style="
                          color: #8d9092;
                          font-size: 12px;
                          margin-left: 20px;
                        "
                      >
                        {{ item1.time }}</span
                      >
                    </p>
                    <span
                      class="replay1"
                      style="margin-top: 5px"
                      @click="pre(item1.cid, item2.nickName)"
                      v-if="canComment"
                      >回复</span
                    >
                  </el-row>
                </div>
              </div>
            </div>
          </el-scrollbar>
        </div>
        <!-- 页脚 选择 -->
        <div class="footer1">
          <el-button
            type="info"
            size="mini"
            round
            @click="showInput"
            v-if="canComment"
            >评论</el-button
          >
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import "github-markdown-css";
export default {
  data() {
    return {
      //博客详情
      blog: {
        title: "",
        content: "",
        uid: 0,
        aid: 0,
      },
      //本人信息
      myself: {
        uid: 0,
        avatar: "",
      },
      //能否评论
      canComment: false,
      //是否是本人所写
      canEdit: false,
      //博主信息
      user: {
        username: "请先登录",
        avatar:
          "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        uid: 0,
      },
      //博主的所有博文
      blogs: [],
      //评论列表
      CommentsList: [],
      //是否显示回复框
      isShowInput: false,
      //评论内容
      inputComment: "",
      //回复的对象所属的父项评论
      replayCid: 0,
      //回复的对象名
      replayName: "",
    };
  },
  methods: {
    //获得文章信息
    getArticleByAid(aid) {
      // 引入markdown
      var markdownIt = require("markdown-it");
      // new markdown对象
      var md = new markdownIt();
      let params = new URLSearchParams();
      params.append("aid", aid);
      this.$axios
        .post("/article/queryArticleByAid", params)
        .then((res) => {
          const article = res.data.data;
          this.blog.title = article.title;
          this.blog.aid = article.aid;
          this.blog.content = md.render(article.content);
          this.getCommentByAid();
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //获得评论信息
    getCommentByAid() {
      let params = new URLSearchParams();
      params.append("aid", this.blog.aid);
      this.$axios
        .post("/article/getCommentByAid", params)
        .then((res) => {
          if (res.data.code == 200) this.CommentsList = res.data.data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //评论
    replay() {
      let params = new URLSearchParams();
      params.append("aid", this.blog.aid);
      params.append("uid", this.myself.uid);
      params.append("remark", this.inputComment);
      params.append("replayCid", this.replayCid);
      params.append("replayName", this.replayName);
      this.$axios
        .post("/article/addComment", params)
        .then((res) => {
          this.$message.success("评论成功");
          this.inputComment = "";
          //重新加载评论信息
          this.getCommentByAid();
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //回复
    pre(cid, nickName) {
      this.replayCid = cid;
      this.replayName = nickName;
      this.isShowInput = true;
    },
    //评论文章
    showInput() {
      this.replayCid = 0;
      this.replayName = "";
      this.isShowInput = !this.isShowInput;
    },
    //私信
    sendMessage(uid) {
      let src = this.$router.resolve({
        path: "/wechat?uid=" + uid,
      });
      // 打开一个新的页面
      window.open(src.href, "_blank");
    },
  },
  created() {
    // 引入markdown
    var markdownIt = require("markdown-it");
    // new markdown对象
    var md = new markdownIt();

    //获得登录人的信息
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    if (user) {
      this.canComment = true;
      this.myself.uid = user.uid;
      this.myself.avatar = user.avatar;
    }
    //获取路由上的参数aid
    let params = new URLSearchParams();
    params.append("aid", this.$route.query.aid);
    this.blog.aid = this.$route.query.aid;
    //请求文章内容
    this.$axios
      .post("/article/queryArticleByAid", params)
      .then((res) => {
        const article = res.data.data;
        this.blog.title = article.title;
        this.blog.uid = article.uid;
        this.blog.content = md.render(article.content);
        if (!user || this.blog.uid != user.uid) this.canEdit = false;
        else this.canEdit = true;
        //获得博主信息
        let params2 = new URLSearchParams();
        params2.append("uid", this.blog.uid);
        //获取博主信息
        this.$axios
          .post("/user/queryTheUser", params2)
          .then((res) => {
            this.user.avatar = res.data.data.avatar;
            this.user.username = res.data.data.nickName;
            this.user.uid = res.data.data.uid;
          })
          .catch((err) => {
            console.error(err);
          });
        //请求博主的博文
        this.$axios
          .post("/article/queryArticleByUid", params2)
          .then((res) => {
            this.blogs = res.data.data;
          })
          .catch((err) => {
            console.error(err);
          });
      })
      .catch((err) => {
        console.error(err);
      });
    this.getCommentByAid();
  },
};
</script>

<style>
.bg2 {
  background: url("http://120.79.198.192:9003/school/2021-10-02/xieshan.jpg")
    no-repeat top;
  background-size: cover;
  background-attachment: fixed;
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
.content1 {
  width: 96%;
  height: 96%;
  margin: 20px auto;
  border-radius: 20px;
  background-color: rgba(245, 237, 237, 0.3);
}

/* 博主信息框 */
.m-content1 {
  margin: 20px auto;
  text-align: center;
}
.list {
  margin-left: 20px;
  border-radius: 20px;
  background-color: rgba(255, 253, 253, 0.3);
}
/*评论区 */
.comment1 {
  text-align: left;
  margin-top: 10px;
  border-radius: 20px;
  background-color: rgba(255, 253, 253, 0.3);
}
.comment1 .avatar {
  float: left;
  margin: 5px 5px;
}
.c_input1 {
  float: left;
  margin: 5px 5px;
  width: 80%;
}
.but1 {
  margin: 5px;
  float: left;
}
.replay1 {
  color: #8d9092;
  font-size: 16px;
}
/*将评论框放置底部 */
.inputBox1 {
  position: fixed;
  width: 60%;
  left: 32%;
  bottom: 2%;
}
/* 页脚 */
.footer1 {
  position: fixed;
  bottom: 2%;
  right: 3%;
}
</style>
