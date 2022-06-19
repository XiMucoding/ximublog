<template>
  <div style="position: absolute">
    <el-header class="header2">
      <!-- 首页头部 -->
      <el-menu
        :default-active="activeIndex"
        class="menu"
        mode="horizontal"
        background-color="#212529"
        text-color="#ffffff"
        active-text-color="#fff"
      >
        <el-menu-item index="3" style="position: relative; top: -15px"
          ><el-link style="font-style: oblique; font-size: 24px" href="/music"
            >稀牧</el-link
          ></el-menu-item
        >
        <el-menu-item
          index="1"
          style="float: right"
          v-show="user.uid == 0"
          @click="login"
          >登录</el-menu-item
        >

        <el-menu-item
          style="position: relative; left: calc(92vw); top: -75px"
          v-show="user.uid != 0"
          ><el-dropdown>
            <el-link href="/my"
              ><el-avatar :size="40" :src="user.avatar" fit="fill"> </el-avatar>
            </el-link>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @mouseover.native="showBut"
                  >个人信息 ></el-dropdown-item
                >
                <el-dropdown-item
                  style="text-indent: 8px"
                  v-show="isShow"
                  @mouseover.native="showBut"
                  @mouseout.native="hideBut"
                  @click.native="dialogVisible1 = true"
                  >用户头像</el-dropdown-item
                >
                <el-dropdown-item
                  style="text-indent: 8px"
                  v-show="isShow"
                  @mouseover.native="showBut"
                  @mouseout.native="hideBut"
                  @click.native="dialogVisible2 = true"
                  >修改信息</el-dropdown-item
                >
                <el-dropdown-item
                  ><el-link href="/my">内容管理</el-link></el-dropdown-item
                >
                <el-dropdown-item
                  ><el-link @click="sendMessage"
                    >消息</el-link
                  ></el-dropdown-item
                >
                <el-dropdown-item
                  ><el-link @click="goToReview"
                    >放映室</el-link
                  ></el-dropdown-item
                >
                <el-dropdown-item v-if="user.status == 3"
                  ><el-link href="/admin">后台管理</el-link></el-dropdown-item
                >
                <el-dropdown-item @click.native="logout"
                  >退出登录</el-dropdown-item
                >
                <el-dropdown-item divided @click.native="deleteUser(user.uid)"
                  >注销账号</el-dropdown-item
                >
              </el-dropdown-menu>
            </template>
          </el-dropdown></el-menu-item
        >
      </el-menu>
    </el-header>
    <el-container style="width: 100%; position: relative; top: -70px">
      <el-main>
        <!-- 主题 -->
        <div class="info1">Welcome to your square inch space!</div>
        <div class="info2">Created on Jun 1, 2021！</div>
        <img
          class="xianxian"
          src="http://120.79.198.192:9003/school/2021-11-04/%E5%A8%B4%E5%A8%B43.jpg"
        />
      </el-main>
      <el-aside width="30%">
        <!-- 侧边 -->
        <!-- 搜索 -->
        <el-autocomplete
          class="search5"
          v-model="search"
          :fetch-suggestions="querySearch"
          placeholder="输入搜索/enter进入搜索详情页..."
          @select="handleSelect"
          @keyup.enter.native="find"
        />
        <!-- 网站公告 -->
        <el-card class="notice">
          <template #header>
            <div class="card-header">
              <span>公告</span>
            </div>
          </template>
          <h3>{{ notice.title }}</h3>
          <p style="margin: 10px 0; text-indent: 1em">{{ notice.context }}</p>
          <span
            style="float: right; font-size: 12px; color: rgb(126, 122, 122)"
            >{{ notice.time }}</span
          >
        </el-card>
        <!-- 留言展示 -->
        <div
          class="leaveMessage"
          @mouseover="isShowInput = true"
          @mouseleave="isShowInput = false"
        >
          <!-- 所有的留言 -->
          <el-scrollbar style="height: 100%"
            ><div class="item5" v-for="item in userList" :key="item.nid">
              <el-avatar
                :size="46"
                :src="item.avatar"
                style="float: left"
              ></el-avatar>
              <div class="name">
                {{ item.nickName
                }}<span style="font-size: 12px; margin-left: 12px">{{
                  item.time
                }}</span>
              </div>
              <span class="context5">{{ item.context }}</span>
            </div>
          </el-scrollbar>
          <!-- 留言框 -->
          <el-row class="inputBox5" v-if="isShowInput && user.uid > 0">
            <el-avatar :size="40" :src="user.avatar" class="avatar5"></el-avatar
            ><el-input
              class="c_input5"
              v-model="inputComment"
              type="text"
              placeholder="欢迎留言(20 w)"
            ></el-input>
            <el-button
              type="primary"
              round
              @click="leaveMessage"
              v-show="inputComment != ''"
              >留言</el-button
            >
          </el-row>
        </div>
      </el-aside>
    </el-container>
    <!-- 首页底部 -->
    <el-footer class="footer2" height="80px" style="line-height: 80px">
      Copyright © Powered by Lzk | email：1801290586@qq.com
    </el-footer>
    <!-- 弹窗部分 -->
    <!-- 用户头像 -->
    <el-dialog
      :visible.sync="dialogVisible1"
      title="用户头像"
      width="30%"
      @close="cancelUpload"
    >
      <div class="block">
        <el-avatar
          :size="100"
          :src="file.url ? file.url : user.avatar"
        ></el-avatar>
      </div>
      <el-upload
        action="http://localhost:8088/minio/put"
        name="file"
        :on-remove="cancelUpload"
        :before-upload="beforeAvatarUpload"
        :on-success="upload"
      >
        <el-button size="small" type="primary">Click to upload</el-button>
        <template #tip>
          <div class="el-upload__tip">
            jpg/png files with a size less than 5M
          </div>
        </template>
      </el-upload>
      <template #footer>
        <el-button type="primary" @click="changeAvatar">更换</el-button>
      </template>
    </el-dialog>
    <!-- 修改信息 -->
    <el-dialog :visible.sync="dialogVisible2" title="修改信息" width="30%">
      <el-form
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="昵称" prop="nickName">
          <el-input
            v-model="ruleForm.nickName"
            placeholder="nickName"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="ruleForm.password"
            type="password"
            placeholder="password"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="updateUser">更换</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        avatar: "",
        uid: 0,
        status: 0,
      },
      search: "",
      result: [],
      //是否显示评论框
      isShowInput: false,
      //留言
      userList: [],
      //留言内容
      inputComment: "",
      //公告
      notice: {},
      isShow: false,
      // 弹窗的显示
      dialogVisible1: false,
      dialogVisible2: false,
      ruleForm: {
        password: "",
        nickName: "",
      },
      rules: {
        nickName: [
          {
            required: true,
            message: "请输入账号！",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "请输入密码！",
            trigger: "blur",
          },
          {
            min: 3,
            max: 18,
            message: "长度为3-18！",
            trigger: "blur",
          },
        ],
      },
      //上传的头像
      file: {
        name: "",
        url: "",
      },
    };
  },
  methods: {
    //前往登录
    login() {
      this.$router.push("/");
    },
    goToReview() {
      let src = this.$router.resolve({
        path: "/review",
      });
      // 打开一个新的页面
      window.open(src.href, "_blank");
    },
    //退出登录
    logout() {
      //清除所有数据
      window.sessionStorage.clear();
      this.$notify({
        title: "退出登录~",
        // 支持html文本
        dangerouslyUseHTMLString: true,
        message: "<text style='color:#058282'>欢迎下次登录~</text>",
        // 自动关闭，0 为点击关闭，>0是到时间关闭
        duration: 3 * 1000,
        // 提示的分类图标
        type: "success",
      });
      location.reload();
    },
    //前往搜索页面
    find() {
      let src = this.$router.resolve({
        path: "/search?keyword=" + this.search,
      });
      // 打开一个新的页面
      window.open(src.href, "_blank");
    },
    querySearch(queryString, cb) {
      if (queryString != "") {
        var res = this.result;
        var results = queryString
          ? res.filter(this.createFilter(queryString))
          : res;
        cb(results);
      } else cb([]);
    },
    //这是当输入数据时触发的，筛选出和输入数据匹配的建议输入。
    //我这里用的是调用match方法，是模糊匹配；官方调用的是indexOf，是精确匹配，看自身情况选择
    createFilter(queryString) {
      return (item) => {
        return (
          item.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    handleSelect(item) {
      let src = this.$router.resolve({
        path: "/blog?aid=" + item.aid,
      });
      // 打开一个新的页面
      window.open(src.href, "_blank");
    },
    showBut() {
      this.isShow = true;
    },
    hideBut() {
      this.isShow = false;
    },
    //上传头像
    upload(res, file) {
      //链接用于更换头像链接
      this.file.url = res;
      //名字用于不想换了删除存储在minio的图片
      var arr = this.file.url.split("/");
      this.file.name = arr[arr.length - 2] + "/" + arr[arr.length - 1];
    },
    //上传头像
    changeAvatar() {
      let params = new URLSearchParams();
      params.append("uid", this.user.uid);
      params.append("avatarUrl", this.file.url);
      this.$axios
        .post("/user/changeAvatar", params)
        .then((res) => {
          if (res.data.code == 200) {
            this.$message.success("头像更换成功,需重新登录方可见！");
            this.file.name = "";
            this.dialogVisible1 = false;
          } else this.$message.error("未知原因导致上传失败，请检测网络情况!");
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //取消头像上传
    cancelUpload() {
      if (this.file.name != "") {
        let params = new URLSearchParams();
        params.append("name", this.file.name);
        this.$axios
          .post("/minio/delete", params)
          .then((res) => {
            this.file.name = "";
            this.file.url = "";
            this.$message.error("未上传新头像!");
          })
          .catch((err) => {
            console.error(err);
          });
      }
    },
    //上传前检测文件大小
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt5M = file.size / 1024 / 1024 < 5;

      if (!isJPG) {
        this.$message.error("Avatar picture must be JPG format!");
      }
      if (!isLt5M) {
        this.$message.error("Avatar picture size can not exceed 5MB!");
      }
      return isJPG && isLt5M;
    },
    //修改信息
    updateUser() {
      var user = window.sessionStorage.getItem("user");
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          let params = new URLSearchParams();
          params.append("uid", this.user.uid);
          params.append("password", this.ruleForm.password);
          params.append("nickName", this.ruleForm.nickName);
          this.$axios
            .post("/user/updateUser", params)
            .then((res) => {
              if (res.data.code == 200) {
                //隐藏弹窗
                this.dialogVisible2 = false;
                this.$notify({
                  title: "Success",
                  message: "修改成功",
                  duration: 3 * 1000,
                  type: "success",
                });
                location.reload();
              }
            })
            .catch((err) => {
              console.error(err);
            });
        } else {
          return false;
        }
      });
    },
    //注销用户
    deleteUser(uid) {
      let params = new URLSearchParams();
      params.append("uid", uid);
      this.$axios
        .post("/user/deleteUser", params)
        .then((res) => {
          if (res.data.code == 200) {
            this.$notify({
              title: "注销成功",
              message: "欢迎下次使用！",
              duration: 3 * 1000,
              // 提示的分类图标
              type: "success",
            });
            //清除所有数据
            window.sessionStroage.remove("user");
            location.reload();
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //私信
    sendMessage() {
      let src = this.$router.resolve({
        path: "/wechat",
      });
      // 打开一个新的页面
      window.open(src.href, "_blank");
    },
    //获得留言
    getLeaveMessage() {
      this.$axios
        .post("/admin/getLeaveMessage")
        .then((res) => {
          console.log(res);
          this.userList = res.data.data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //留言
    leaveMessage() {
      var params = new URLSearchParams();
      params.append("uid", this.user.uid);
      params.append("status", 2);
      params.append("title", "");
      params.append("context", this.inputComment);
      this.$axios
        .post("/admin/addNotice", params)
        .then((res) => {
          if ((res.data.code = 200)) {
            this.$notify({
              title: "Success",
              message: "留言成功！",
              duration: 3 * 1000,
              type: "success",
            });
            //将内容置为空
            this.inputComment = "";
            this.getLeaveMessage();
          }
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
  created() {
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    if (user != null) {
      this.user.avatar = user.avatar;
      this.user.uid = user.uid;
      this.user.status = user.status;
    }
    //获得所有博文数据
    this.$axios
      .post("/article/queryAllArticle")
      .then((res) => {
        this.result = res.data.data;
      })
      .catch((err) => {
        console.error(err);
      });
    //获得最新公告
    this.$axios
      .post("/admin/getNewNotice")
      .then((res) => {
        this.notice = res.data.data;
      })
      .catch((err) => {
        console.error(err);
      });
    //获得留言
    this.getLeaveMessage();
  },
};
</script>

<style>
.header2,
.footer2 {
  background-color: #212529;
  color: white;
  text-align: center;
  line-height: 60px;
}
.xianxian {
  margin-top: 15px;
  width: 94%;
  border-radius: 6px;
}
.info1 {
  font-size: 34px;
  color: black;
}
.info2 {
  margin-top: 20px;
  font-style: oblique;
  font-size: 14px;
  color: rgb(126, 122, 122);
}
.notice {
  margin-right: 8%;
  margin-top: 15px;
  border-radius: 6px;
}
.search5 {
  margin: 15px 8% 0 0;
  width: 92%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
/*留言区 */

.item5 {
  position: relative;
  width: 94%;
  height: 50px;
  max-height: 300px;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  margin: 3% auto;
}
.item5 .name {
  float: left;
  margin-left: 10px;
}
.item5 .context5 {
  position: absolute;
  top: 26px;
  left: 56px;
  text-align: left;
  width: 80%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.leaveMessage {
  position: absolute;
  margin-top: 3%;
  width: 28%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  border-radius: 20px;
  height: 54vh;
}
.inputBox5 {
  position: relative;
  width: 70%;
  left: 15%;
  bottom: 12%;
}
.avatar5 {
  position: absolute;
  top: 2%;
}
.c_input5 {
  width: 70%;
  padding-left: 45px;
}
</style>
