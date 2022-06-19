<template>
  <div class="bg9">
    <!-- 头像等信息 -->
    <div class="m-content9">
      <div>
        <el-avatar :size="100" :src="user.avatar"></el-avatar>
        <div>{{ user.username }}</div>
      </div>
      <div class="maction">
        <span
          ><el-link
            type="primary"
            :href="blogs.length == 0 ? '' : '/blog?aid=' + blogs[0].aid"
            >主页</el-link
          ></span
        >
        <el-divider direction="vertical"></el-divider>
        <span><el-link href="/edit?aid=0">发表博客</el-link></span>
        <el-divider direction="vertical"></el-divider>
        <span><el-link type="danger" href="/Shouye">首页</el-link></span>
      </div>
    </div>
    <!-- 本人写的博客 -->
    <div class="blog9">
      <el-timeline v-for="item in blogs" :key="item.aid">
        <!-- 发布与否徽章 -->
        <el-timeline-item :timestamp="item.time" placement="top">
          <el-card style="position: relative;">
            <el-link :href="'/edit?aid=' + item.aid"
              ><h2>{{ item.title }}</h2></el-link
            >
            <el-tag
              :type="item.status == 1 ? 'success' : 'info'"
              class="status"
              >{{ item.status == 1 ? "发布" : "草稿" }}</el-tag
            >
            <!-- 删除按钮 -->
            <el-button
              type="danger"
              size="mini"
              plain
              class="delete"
              @click="deleteArticle(item.aid)"
              >删除</el-button
            >
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
    <!-- 分页 -->
    <div class="block9">
      <el-pagination
        layout="prev, pager, next"
        background
        :total="pageSum * 10"
        :current-page.sync="pageNum"
        hide-on-single-page
        @current-change="get4Article"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        username: "请先登录",
        avatar:
          "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
        uid: 0
      },
      blogs: [],
      //总页数
      pageSum: 0,
      //获取的页码
      pageNum: 1
    };
  },
  methods: {
    //获取4篇文章信息
    get4Article() {
      let params = new URLSearchParams();
      params.append("uid", this.user.uid);
      params.append("start", (this.pageNum - 1) * 4);
      params.append("end", 4);
      this.$axios
        .post("/article/query4ArticleByUid", params)
        .then(res => {
          console.log(res.data.data);
          this.blogs = res.data.data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    //删除博文
    deleteArticle(aid) {
      let params = new URLSearchParams();
      params.append("aid", aid);
      this.$axios
        .post("/article/deleteArticle", params)
        .then(res => {
          if (res.data.code == 200) {
            this.$notify({
              title: "Success",
              message: "删除成功",
              type: "success"
            });
            //重新加载页面
            location.reload();
          }
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    if (user) {
      this.user.avatar = user.avatar;
      this.user.username = user.nickName;
      this.user.uid = user.uid;
    } else {
      //未登录跳转登录界面
      this.$router.push("/");
      this.$notify({
        title: "提示",
        // 支持html文本
        dangerouslyUseHTMLString: true,
        message: "请先登录！",
        // 自动关闭，0 为点击关闭，>0是到时间关闭
        duration: 3 * 1000,
        // 提示的分类图标
        type: "warning"
      });
    }

    let params = new URLSearchParams();
    params.append("uid", this.user.uid);
    //获得页数
    this.$axios
      .post("/article/getArticleSumOfUser", params)
      .then(res => {
        console.log(res.data.data);
        this.pageSum = res.data.data;
      })
      .catch(err => {
        console.error(err);
      });
    //获得当前页数
    this.get4Article();
  }
};
</script>

<style>
.bg9 {
  background: url("http://120.79.198.192:9003/school/2021-10-02/shenlin.jpg")
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

.m-content9 {
  max-width: 960px;
  margin: 10px auto;
  text-align: center;
}
.maction {
  margin: 10px 0;
}
.blog9 {
  width: 40%;
  height: 300px;
  margin: 20px auto;
}
/* 发布状态 */
.status {
  position: absolute;
  top: -2px;
  left: -4px;
}
/* 删除按钮 */
.delete {
  position: absolute;
  top: 20px;
  right: 4px;
}
/*分页 */
.block9 {
  position: fixed;
  width: 100%;
  margin: 0 auto;
  bottom: 35px;
}
</style>
