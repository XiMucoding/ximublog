<template>
  <div class="bg6">
    <el-container class="wechat">
      <el-aside width="35%">
        <el-tabs
          v-model="activeName"
          style="padding-left:20px;border-right: 1px solid #fff;height: 100%"
        >
          <el-tab-pane label="私信" name="first">
            <!-- 即将要私聊的用户 -->
            <div
              v-if="otherUser.uid != myself.uid"
              class="item6"
              @click="getMessage(otherUser.uid, otherUser.nickName)"
            >
              <el-avatar
                :size="46"
                :src="otherUser.avatar"
                style="float: left;"
              ></el-avatar>
              <div class="name">{{ otherUser.nickName }}</div>
            </div>
            <!-- 聊过天的用户 -->
            <div
              class="item6"
              v-for="item in userList"
              :key="item.mid"
              @click="getMessage(item.uid, item.nickName)"
            >
              <el-avatar
                :size="46"
                :src="item.avatar"
                style="float: left;"
              ></el-avatar>
              <div class="name">{{ item.nickName }}</div>
              <span class="context6">{{ item.context }}</span>
            </div>
          </el-tab-pane>
          <el-tab-pane label="通知" name="second">
            <div v-for="item in noticeList" :key="item.nid">
              <div class="myBr"></div>
              <div class="leftBox">
                <span style="font-size: 4px;">{{ item.time }}</span
                >{{ item.context }}
              </div>
              <div class="myBr"></div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-aside>
      <el-main>
        <el-container class="wechat_right">
          <el-header class="header6">
            {{ replayName }}
          </el-header>
          <el-main class="showChat">
            <div v-for="item in messageList" :key="item.mid">
              <!-- 对方发的 -->
              <div v-if="item.uid != myself.uid" class="leftBox">
                <span style="font-size: 4px;">{{ item.time }}</span
                >{{ item.context }}
              </div>
              <!-- 自己发的 -->
              <div v-if="item.uid == myself.uid" class="rightBox">
                <span style="font-size: 4px;">{{ item.time }}</span
                >{{ item.context }}
              </div>
              <div class="myBr"></div>
            </div>
          </el-main>
          <el-main class="inputValue6">
            <textarea v-model="inputValue" id="chat" cols="30" rows="5">
            </textarea>
            <!-- 发送按钮 -->
            <el-button
              type="success"
              size="mini"
              round
              id="send"
              @click="senMessage"
              v-if="(inputValue != '') & (replayUid != 0)"
              >发送</el-button
            >
          </el-main>
        </el-container>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //开始显示的页
      activeName: "first",
      myself: {
        uid: 0,
        avatar: "",
        userName: ""
      },
      //通知
      noticeList: [],
      //将要发送的内容
      inputValue: "",
      //要回复的对象的昵称
      replayName: "",
      //要回复的人的id
      replayUid: 0,
      //已经私信或是想私信的user
      userList: [],
      //消息队列
      messageList: [],
      //要私信的人
      otherUser: {}
    };
  },
  methods: {
    //获得私信过的用户信息
    getUserInfo() {
      var params = new URLSearchParams();
      params.append("uid", this.myself.uid);
      this.$axios
        .post("/admin/getMessageByUid", params)
        .then(res => {
          this.userList = res.data.data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    //获得消息队列
    getMessage(uid, replayName) {
      this.replayUid = uid;
      this.replayName = replayName;
      var params = new URLSearchParams();
      params.append("uid", this.myself.uid);
      params.append("replayUid", this.replayUid);
      this.$axios
        .post("/admin/getMessageByrUid", params)
        .then(res => {
          this.messageList = res.data.data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    //发送信息
    senMessage() {
      var params = new URLSearchParams();
      params.append("uid", this.myself.uid);
      params.append("replayUid", this.replayUid);
      params.append("context", this.inputValue);
      this.$axios
        .post("/admin/addMessage", params)
        .then(res => {
          if (res.data.code == 200) {
            this.getMessage(this.replayUid, this.replayName);
            this.inputValue = "";
          }
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    //获得登录人的信息
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    if (user) {
      this.myself.uid = user.uid;
      this.myself.avatar = user.avatar;
      this.myself.userName = user.nickName;
      //获得私信过的用户信息
      this.getUserInfo();
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

    //获得通知
    this.$axios
      .post("/admin/getNotice1")
      .then(res => {
        this.noticeList = res.data.data;
      })
      .catch(err => {
        console.error(err);
      });

    //在主页私信别人进入该页面 获得参数补充基本信息
    let uid = this.$route.query.uid;
    if (uid != null && uid != user.uid) {
      var params = new URLSearchParams();
      params.append("uid", uid);
      this.$axios
        .post("/user/queryTheUser", params)
        .then(res => {
          this.otherUser = res.data.data;
          //判断要私信的人是否已经聊过
          let flag = false;
          for (const item in this.userList) {
            if (this.otherUser.uid == this.userList[item].uid) {
              this.otherUser = user;
              flag = true;
              break;
            }
          }
          if (flag) {
            this.replayName = this.otherUser.nickName;
            this.replayUid = this.otherUser.uid;
          }
        })
        .catch(err => {
          console.error(err);
        });
    } else this.otherUser = user;
  }
};
</script>

<style>
.bg6 {
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
.wechat {
  width: 60%;
  height: 80%;
  margin: 6% auto;
  border-radius: 20px;
  background-color: rgba(245, 237, 237, 0.3);
}
/*聊天框左侧 */
.item6 {
  position: relative;
  width: 94%;
  height: 50px;
  margin-bottom: 3%;
  border-bottom: 1px solid #fff;
}
.item6 .name {
  float: left;
  margin-left: 10px;
}
.item6 .context6 {
  position: absolute;
  bottom: 2px;
  left: 56px;
  text-align: left;
  width: 200px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
/*聊天框右侧 */
.wechat_right {
  position: relative;
  width: 100%;
  height: 100%;
}
.header6 {
  text-align: left;
  height: 5%;
}
.showChat {
  width: 100%;
  height: 65%;
}
.inputValue6 {
  position: relative;
  margin: 0;
  padding: 0;
  width: 100%;
  height: 30%;
}
.inputValue6 #chat {
  padding: 10px;
  font-size: 18px;
  width: 100%;
  height: 97%;
  border-radius: 20px;
  resize: none;
  background-color: rgba(245, 237, 237, 0.3);
}
#send {
  position: absolute;
  bottom: 6%;
  right: 2%;
}
/*展示区 */
.leftBox {
  float: left;
  max-width: 60%;
  padding: 8px;
  position: relative;
  font-size: 18px;
  border-radius: 12px;
  background-color: rgba(40, 208, 250, 0.76);
}
.rightBox {
  float: right;
  max-width: 60%;
  padding: 8px;
  font-size: 18px;
  border-radius: 12px;
  position: relative;
  background-color: rgba(101, 240, 21, 0.945);
}
.myBr {
  float: left;
  width: 100%;
  height: 20px;
}
.leftBox > span {
  left: 3px;
  width: 120px;
  position: absolute;
  top: -16px;
}
.rightBox > span {
  width: 120px;
  position: absolute;
  right: 3px;
  top: -16px;
}
</style>
