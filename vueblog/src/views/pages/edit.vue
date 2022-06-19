<template>
  <div class="bg3">
    <el-form
      ref="ruleForm"
      :model="ruleForm"
      :rules="rules"
      label-width="120px"
      class="edit"
    >
      <el-form-item label="标题" prop="title">
        <el-input v-model="ruleForm.title"></el-input>
      </el-form-item>
      <el-form-item label="摘要" prop="summary">
        <el-input v-model="ruleForm.summary"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <!-- 编辑框 -->
        <mavon-editor
          ref="md"
          @imgAdd="$imgAdd"
          @imgDel="$imgDel"
          v-model="ruleForm.content"
          style="height:500px"
        ></mavon-editor>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addArticle1">发布</el-button>
        <el-button @click="addArticle0">暂存草稿</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ruleForm: {
        title: "",
        summary: "",
        content: "",
        uid: 0,
        aid: 0
      },
      rules: {
        title: [
          {
            required: true,
            message: "请输入标题",
            trigger: "blur"
          }
        ],
        summary: [
          {
            required: true,
            message: "请输入摘要",
            trigger: "blur"
          }
        ],
        content: [
          {
            required: true,
            message: "请输入内容",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    addArticle1() {
      this.$refs.ruleForm.validate(valid => {
        let params = new URLSearchParams();
        params.append("title", this.ruleForm.title);
        params.append("summary", this.ruleForm.summary);
        params.append("content", this.ruleForm.content);
        params.append("uid", this.ruleForm.uid);
        params.append("status", 1);
        params.append("aid", this.ruleForm.aid);
        if (valid) {
          this.$axios
            .post("/article/addArticle", params)
            .then(res => {
              this.$notify({
                title: "Success",
                // 支持html文本
                dangerouslyUseHTMLString: true,
                message: "<text style='color:#058282'>发布成功~</text>",
                // 自动关闭，0 为点击关闭，>0是到时间关闭
                duration: 3 * 1000,
                // 提示的分类图标
                type: "success"
              });
              this.$router.push("/my");
            })
            .catch(err => {
              console.error(err);
            });
        } else {
          return false;
        }
      });
    },
    addArticle0() {
      this.$refs.ruleForm.validate(valid => {
        let params = new URLSearchParams();
        params.append("title", this.ruleForm.title);
        params.append("summary", this.ruleForm.summary);
        params.append("content", this.ruleForm.content);
        params.append("uid", this.ruleForm.uid);
        params.append("status", 0);
        params.append("aid", this.ruleForm.aid);
        if (valid) {
          this.$axios
            .post("/article/addArticle", params)
            .then(res => {
              this.$notify({
                title: "Success",
                // 支持html文本
                dangerouslyUseHTMLString: true,
                message: "<text style='color:red'>已经记录好草稿~</text>",
                // 自动关闭，0 为点击关闭，>0是到时间关闭
                duration: 3 * 1000,
                // 提示的分类图标
                type: "warning"
              });
              this.$router.push("/my");
            })
            .catch(err => {
              console.error(err);
            });
        } else {
          return false;
        }
      });
    },
    // 绑定@imgAdd event
    $imgAdd(pos, $file) {
      // 第一步.将图片上传到服务器.
      var formdata = new FormData();
      formdata.append("file", $file);
      this.$axios({
        url: "/minio/put",
        method: "post",
        data: formdata,
        headers: { "Content-Type": "multipart/form-data" }
      }).then(url => {
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
        /**
         * $vm 指为mavonEditor实例，可以通过如下两种方式获取
         * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
         * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
         */
        this.$refs.md.$img2Url(pos, url.data);
      });
    },
    // 绑定@imgDel event
    $imgDel(pos) {
      var arr = pos[0].split("/");
      var name = arr[arr.length - 2] + "/" + arr[arr.length - 1];
      let params = new URLSearchParams();
      params.append("name", name);
      this.$axios
        .post("/minio/delete", params)
        .then(res => {
          this.$message.error("该图片已删除!");
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    if (user) this.ruleForm.uid = user.uid;
    else {
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

    let aid = this.$route.query.aid;
    if (aid != 0) {
      this.ruleForm.aid = this.$route.query.aid;
      let params = new URLSearchParams();
      params.append("aid", this.$route.query.aid);
      this.$axios
        .post("/article/queryArticleByAid", params)
        .then(res => {
          const article = res.data.data;
          this.ruleForm.title = article.title;
          this.ruleForm.summary = article.summary;
          this.ruleForm.content = article.content;
        })
        .catch(err => {
          console.error(err);
        });
    }
  }
};
</script>

<style>
.bg3 {
  background: url("http://120.79.198.192:9003/school/2021-10-02/xiaolu.jpg")
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
.edit {
  width: 88%;
  margin: 10px auto;
}
</style>
