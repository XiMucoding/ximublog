<template>
  <div class="bg7">
    <el-form
      ref="ruleForm"
      :model="ruleForm"
      :rules="rules"
      label-width="120px"
      class="ruleForm"
    >
      <el-form-item>
        <h1>欢迎你登录</h1>
      </el-form-item>
      <el-form-item label="账号" prop="userName">
        <el-input v-model="ruleForm.userName" placeholder="userName"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          v-model="ruleForm.password"
          type="password"
          placeholder="password"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="goToIndex()">登录</el-button>
      </el-form-item>
      <el-form-item>
        <el-link href="/register" type="danger" class="register"
          >注册账号</el-link
        >
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
        password: ""
      },
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
        password: [
          {
            required: true,
            message: "请输入密码！",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    goToIndex() {
      let param = new URLSearchParams();
      param.append("userName", this.ruleForm.userName),
        param.append("password", this.ruleForm.password),
        this.$refs.ruleForm.validate(valid => {
          if (valid) {
            this.$axios
              .post("/user/login", param)
              .then(res => {
                console.log(res);
                if (res.data.code == 200) {
                  //将信息存储起来
                  window.sessionStorage.setItem(
                    "user",
                    JSON.stringify(res.data.data)
                  );
                  this.$router.push("/Shouye");
                  this.$notify({
                    title: "欢迎你的使用",
                    // 支持html文本
                    dangerouslyUseHTMLString: true,
                    message:
                      "<text style='color:#058282'>希望您用的开心，有bug欢迎反馈~</text>",
                    // 自动关闭，0 为点击关闭，>0是到时间关闭
                    duration: 3 * 1000,
                    // 提示的分类图标
                    type: "success"
                  });
                } else {
                  this.$notify({
                    title: "警告",
                    // 支持html文本
                    dangerouslyUseHTMLString: true,
                    message:
                      "<text style='color:red'>" + res.data.msg + "</text>",
                    // 自动关闭，0 为点击关闭，>0是到时间关闭
                    duration: 3 * 1000,
                    // 提示的分类图标
                    type: "warning"
                  });
                }
              })
              .catch(err => {
                console.error(err);
              });
          } else {
            return false;
          }
        });
    }
  }
};
</script>

<style>
.bg7 {
  background: url("http://120.79.198.192:9003/school/2021-10-02/xiaolu1.jpg")
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
.ruleForm {
  max-width: 460px;
  margin: 10% auto;
}
.register {
  font-size: 12px;
}
</style>
