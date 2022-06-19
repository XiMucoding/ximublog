<template>
  <div class="bg8">
    <el-form
      ref="ruleForm"
      :model="ruleForm"
      :rules="rules"
      label-width="120px"
      class="ruleForm8"
    >
      <el-form-item>
        <h1>欢迎注册</h1>
      </el-form-item>
      <el-form-item label="账号" prop="userName">
        <el-input v-model="ruleForm.userName" placeholder="userName"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickName">
        <el-input v-model="ruleForm.nickName" placeholder="nickName"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          v-model="ruleForm.password"
          type="password"
          placeholder="password"
        ></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="code">
        <el-input v-model="ruleForm.code" class="code"></el-input
        ><el-button
          type="primary"
          class="getCodeBut"
          @click="getCode"
          :disabled="isable"
          >获取</el-button
        >
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ruleForm: {
        userName: "",
        password: "",
        nickName: "",
        code: ""
      },
      isable: false,
      rules: {
        userName: [
          {
            required: true,
            message: "请输入账号！",
            trigger: "blur"
          },
          {
            pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
            message: "邮箱格式不正确！"
          }
        ],
        nickName: [
          {
            required: true,
            message: "请输入账号！",
            trigger: "blur"
          }
        ],
        password: [
          {
            required: true,
            message: "请输入密码！",
            trigger: "blur"
          },
          {
            min: 3,
            max: 18,
            message: "长度为3-18！",
            trigger: "blur"
          }
        ],
        code: [
          {
            required: true,
            message: "请输入验证码！",
            trigger: "blur"
          },
          {
            min: 4,
            max: 4,
            message: "长度4！",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    getCode() {
      //将按钮变为不可点击
      this.isable = true;
      this.$notify({
        title: "提示",
        // 支持html文本
        dangerouslyUseHTMLString: true,
        message: "<text style='color:#058282'>正在发送请等待10s~</text>",
        // 自动关闭，0 为点击关闭，>0是到时间关闭
        duration: 3 * 1000,
        // 提示的分类图标
        type: "success"
      });
      // 邮箱格式认证
      var pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      var flag = pattern.test(this.ruleForm.userName);
      //邮箱必须正确无误
      if (this.ruleForm.userName != null && flag) {
        let param = new URLSearchParams();
        param.append("userName", this.ruleForm.userName),
          this.$axios
            .post("/user/getCode", param)
            .then(res => {
              if (res.data.code == 200) {
                this.$notify({
                  title: "邮件发送成功",
                  // 支持html文本
                  dangerouslyUseHTMLString: true,
                  message:
                    "<text style='color:#058282'>验证码已发送到您的邮箱，请查收</text>",
                  // 自动关闭，0 为点击关闭，>0是到时间关闭
                  duration: 3 * 1000,
                  // 提示的分类图标
                  type: "success"
                });
              }
              //恢复点击
              this.isable = false;
            })
            .catch(error => {
              console.info(error);
              //恢复点击
              this.isable = false;
            });
      } else {
        this.$notify({
          title: "提示",
          // 支持html文本
          dangerouslyUseHTMLString: true,
          message: "<text style='color:red'>未输入邮箱或是邮箱格式错误</text>",
          // 自动关闭，0 为点击关闭，>0是到时间关闭
          duration: 3 * 1000,
          // 提示的分类图标
          type: "warning"
        });
        //恢复点击
        this.isable = false;
      }
    },
    register() {
      let param = new URLSearchParams();
      param.append("userName", this.ruleForm.userName),
        param.append("password", this.ruleForm.password),
        param.append("nickName", this.ruleForm.nickName),
        param.append("code", this.ruleForm.code),
        this.$axios
          .post("/user/register", param)
          .then(res => {
            if (res.data.code != 200) {
              //账号已被注册
              this.$notify({
                title: "警告",
                // 支持html文本
                dangerouslyUseHTMLString: true,
                message: "<text style='color:red'>" + res.data.msg + "</text>",
                // 自动关闭，0 为点击关闭，>0是到时间关闭
                duration: 3 * 1000,
                // 提示的分类图标
                type: "warning"
              });
            } else {
              //注册成功跳转登陆页面
              this.$notify({
                title: "Success",
                // 支持html文本
                dangerouslyUseHTMLString: true,
                message: "<text style='color:#058282'>注册成功，请登录~</text>",
                // 自动关闭，0 为点击关闭，>0是到时间关闭
                duration: 3 * 1000,
                // 提示的分类图标
                type: "success"
              });
              this.$router.push("/");
            }
          })
          .catch(err => {
            console.error(err);
          });
    }
  }
};
</script>

<style>
.bg8 {
  background: url("http://120.79.198.192:9003/school/2021-10-02/fangzi.jpg")
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
.ruleForm8 {
  max-width: 460px;
  margin: 10% auto;
}
.code {
  max-width: 230px;
}
.getCodeBut {
  margin-left: 10px;
}
</style>
