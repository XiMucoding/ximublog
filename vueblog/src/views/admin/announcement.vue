<template>
  <div>
    <el-container>
      <el-aside width="50%">
        <el-tabs v-model="activeName">
          <el-tab-pane label="公告" name="first">
            <div
              style="font-size: 14px;color: rgb(150, 150, 150);margin:10px 0 20px 0;"
            >
              公告是展示在首页通知！
            </div>
            <el-form
              ref="ruleForm1"
              :model="ruleForm1"
              :rules="rules"
              label-width="120px"
            >
              <el-form-item label="公告标题" prop="title">
                <el-input v-model="ruleForm1.title"></el-input>
              </el-form-item>
              <el-form-item label="公告内容" prop="context">
                <el-input
                  v-model="ruleForm1.context"
                  type="textarea"
                  rows="16"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="addNotice(0)">发布</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="通知" name="second"
            ><el-form
              ref="ruleForm2"
              :model="ruleForm2"
              :rules="rules"
              label-width="120px"
            >
              <div
                style="font-size: 14px;color: rgb(150, 150, 150);margin:10px 0 20px 0;"
              >
                通知是发消息到所有用户的私信！
              </div>
              <el-form-item label="通知标题" prop="title">
                <el-input v-model="ruleForm2.title"></el-input>
              </el-form-item>
              <el-form-item label="通知内容" prop="context">
                <el-input
                  v-model="ruleForm2.context"
                  type="textarea"
                  rows="16"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="addNotice(1)">发布</el-button>
              </el-form-item>
            </el-form></el-tab-pane
          >
        </el-tabs>
      </el-aside>
      <el-main>
        <div style="width:80%;margin:0 auto;">
          <!-- 空时展示 -->
          <el-empty
            v-if="AllNotice.length == 0"
            description="暂无发布信息~"
          ></el-empty>
          <el-collapse v-model="activeName_right">
            <div
              style="position: relative;"
              v-for="item in AllNotice"
              :key="item.nid"
            >
              <el-tag
                type="success"
                style="position: absolute;right: 100px;top: 8px;"
                >{{ item.status == 0 ? "公告" : "通知" }}</el-tag
              >
              <el-tag
                type="info"
                style="position: absolute;right: 46px;top: 8px;"
                @click="deleteOneNotice(item.nid)"
                >删除</el-tag
              >
              <el-collapse-item :title="item.title" :name="index">
                <div>
                  {{ item.context }}
                </div>
              </el-collapse-item>
            </div>
          </el-collapse>
        </div>
      </el-main>
    </el-container>
  </div></template
>

<script>
export default {
  data() {
    return {
      //一开始选中的分页
      activeName: "first",
      activeName_right: "0",
      ruleForm1: {
        title: "",
        context: ""
      },
      ruleForm2: {
        title: "",
        context: ""
      },
      //登录的人的uid
      uid: 0,
      //公告和通知
      AllNotice: [],
      rules: {
        title: [
          {
            required: true,
            message: "请输入标题",
            trigger: "blur"
          },
          {
            min: 1,
            max: 18,
            message: "Length should be 1 to 18",
            trigger: "blur"
          }
        ],
        context: [
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
    //发布公告
    addNotice(status) {
      var params = new URLSearchParams();
      params.append("uid", this.uid);
      params.append("status", status);
      if (status == 0) {
        this.$refs.ruleForm1.validate(valid => {
          if (valid) {
            params.append("title", this.ruleForm1.title);
            params.append("context", this.ruleForm1.context);
            this.$axios
              .post("/admin/addNotice", params)
              .then(res => {
                if ((res.data.code = 200)) {
                  this.$notify({
                    title: "Success",
                    message: "公告发布成功！",
                    duration: 3 * 1000,
                    // 提示的分类图标
                    type: "success"
                  });
                  //将内容置为空
                  this.ruleForm1.title = "";
                  this.ruleForm1.context = "";
                  this.getAllNotice();
                }
              })
              .catch(err => {
                console.error(err);
              });
          }
        });
      } else {
        this.$refs.ruleForm2.validate(valid => {
          if (valid) {
            params.append("title", this.ruleForm2.title);
            params.append("context", this.ruleForm2.context);
            this.$axios
              .post("/admin/addNotice", params)
              .then(res => {
                if ((res.data.code = 200)) {
                  this.$notify({
                    title: "Success",
                    message: "通知发布成功！",
                    duration: 3 * 1000,
                    // 提示的分类图标
                    type: "success"
                  });
                  //将内容置为空
                  this.ruleForm2.title = "";
                  this.ruleForm2.context = "";
                  this.getAllNotice();
                }
              })
              .catch(err => {
                console.error(err);
              });
          }
        });
      }
    },
    //获得公告和通知
    getAllNotice() {
      this.$axios
        .post("/admin/getAllNotice")
        .then(res => {
          this.AllNotice = res.data.data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    //删除通知
    deleteOneNotice(nid) {
      var params = new URLSearchParams();
      params.append("nid", nid);
      this.$axios
        .post("/admin/deleteOneNotice", params)
        .then(res => {
          if (res.data.code == 200) {
            this.$notify({
              title: "Success",
              message: "删除成功！",
              duration: 3 * 1000,
              // 提示的分类图标
              type: "success"
            });
            this.getAllNotice();
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    if (user != null) {
      this.uid = user.uid;
    }
    //获得公告和通知
    this.getAllNotice();
  }
};
</script>

<style></style>
