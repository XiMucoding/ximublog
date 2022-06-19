<template>
  <div style="width:70%;margin:2% auto;">
    <!-- 空时展示 -->
    <el-empty
      v-if="AllNotice.length == 0"
      :image-size="400"
      description="暂无留言信息~"
    ></el-empty>
    <el-collapse v-model="activeName_right">
      <div
        style="position: relative;"
        v-for="item in AllNotice"
        :key="item.nid"
      >
        <el-button
          type="danger"
          size="mini"
          round
          style="position: absolute;right: 46px;top: 8px;"
          @click="deleteOneNotice(item.nid)"
          >删除</el-button
        >
        <el-collapse-item :title="item.nickName" :name="index">
          <div>
            {{ item.context }}
          </div>
        </el-collapse-item>
      </div>
    </el-collapse>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //公告和通知
      AllNotice: [],
      activeName_right: "0"
    };
  },
  methods: {
    //获得公告和通知
    getAllNotice() {
      this.$axios
        .post("/admin/getLeaveMessage")
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
    this.getAllNotice();
  }
};
</script>

<style></style>
