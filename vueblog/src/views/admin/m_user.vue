<template>
  <div>
    <!-- 搜索框 -->
    <div>
      <input
        v-model="keyword"
        placeholder="请输入用户名..."
        class="search2"
        @keyup.enter="find"
        @blur="pageNum = 0"
      />
    </div>
    <!-- 用户展示 -->
    <el-card
      style="width: 300px;margin:8px 8px;float:left"
      v-for="item in UserList"
      :key="item.uid"
    >
      <template #header>
        <span style="fl">{{ item.nickName }}</span>
        <el-button
          type="danger"
          round
          size="mini"
          style="float: right;"
          @click="deleteUser(item.uid)"
          >删除</el-button
        >
      </template>
      <div>
        <el-avatar :size="80" :src="item.avatar" fit="fill"> </el-avatar><br />
        <div class="info">
          <div>userName: {{ item.userName }}</div>
          <div>password: {{ item.password }}</div>
          <div v-if="item.status == 1">状态: 禁言</div>
          <div v-else-if="item.status == 2">状态: 正常</div>
          <div v-else-if="item.status == 3">状态: 正常、管理员</div>
          <div>
            <el-radio-group
              v-model="item.status"
              @change="changeStatus($event, (uid = item.uid))"
            >
              <el-radio :label="1">禁言</el-radio>
              <el-radio :label="2">正常</el-radio>
              <el-radio :label="3">管理员</el-radio>
            </el-radio-group>
          </div>
        </div>
      </div>
    </el-card>
    <el-pagination
      style=" position: fixed;
      right: 20px;
      bottom: 20px;"
      background
      hide-on-single-page="true"
      layout="prev, pager, next"
      :total="pageSum * 10"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        avatar: "",
        uid: 0,
        nickName: ""
      },
      keyword: "",
      pageNum: 0,
      pageSum: 0,
      UserList: []
    };
  },
  methods: {
    //获取所有用户
    queryUser() {
      let params = new URLSearchParams();
      params.append("index", this.pageNum);
      params.append("num", 8);
      this.$axios
        .post("/user/queryUser", params)
        .then(res => {
          this.UserList = res.data.data.users;
          this.pageSum = res.data.data.sumOfUser;
        })
        .catch(err => {
          console.error(err);
        });
    },
    //查询用户
    find() {
      let params = new URLSearchParams();
      params.append("keyword", this.keyword);
      params.append("index", this.pageNum);
      params.append("num", 8);
      this.$axios
        .post("/user/findUserByKW", params)
        .then(res => {
          console.log(res);
          if (res.data.code == 200) {
            this.UserList = res.data.data.users;
            this.pageSum = res.data.data.sumOfUser;
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    //更改用户的状态
    changeStatus($event, uid) {
      let params = new URLSearchParams();
      params.append("uid", uid);
      params.append("status", $event);
      this.$axios
        .post("/user/changeStatus", params)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.error(err);
        });
    },
    //删除用户
    deleteUser(uid) {
      let params = new URLSearchParams();
      params.append("uid", uid);
      this.$axios
        .post("/user/deleteUser", params)
        .then(res => {
          if (res.data.code == 200) {
            this.$notify({
              title: "Success",
              message: "删除成功！",
              duration: 3 * 1000,
              // 提示的分类图标
              type: "success"
            });
            this.queryUser();
          }
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    if (user != null) {
      this.user.avatar = user.avatar;
      this.user.uid = user.uid;
      this.user.nickName = user.nickName;
    }
    //获得UserList
    this.queryUser();
  }
};
</script>

<style>
.search2 {
  width: 55%;
  height: 40px;
  border-radius: 20px;
  font-size: 24px;
  text-indent: 1em;
}
.info {
  margin: 8px 0;
}
.info div {
  margin: 12px 0 0 3px;
  text-align: left;
  color: rgb(98, 201, 241);
}
</style>
