<template>
  <div>
    <!-- 搜索框 -->
    <div>
      <input
        v-model="keyword"
        placeholder="请输入关键词..."
        class="search1"
        @keyup.enter="find"
      />
    </div>
    <!-- 博文展示 -->
    <div style="width: 70%;height: 100%;margin:20px auto">
      <!-- 结果展示 -->
      <span v-show="result.length == 0">暂无匹配的搜索结果</span>
      <div class="item1" v-for="item in result" :key="item.aid">
        <el-link :href="'/blog?aid=' + item.aid"
          ><h2 style="margin-top:8px ;">{{ item.title }}</h2></el-link
        >
        <!-- 搜索结果底部右边 -->
        <div class="right1">
          <span>{{ item.nickName }}</span>
          <span style="margin-left:20px;margin-right:10px">{{
            item.time
          }}</span>
          <el-button type="danger" size="mini" @click="deleteArticle(item.aid)"
            >删除</el-button
          >
        </div>
      </div>
    </div>
    <!-- 返回顶部 -->
    <el-backtop :bottom="100">
      <div
        style="
        height: 100%;
        width: 100%;
        background-color: #f2f5f6;
        box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
        text-align: center;
        line-height: 40px;
        color: #1989fa;
      "
      >
        UP
      </div>
    </el-backtop>
  </div>
</template>

<script>
export default {
  data() {
    return {
      keyword: "",
      result: []
    };
  },
  methods: {
    //搜索内容
    find() {
      let params = new URLSearchParams();
      params.append("keyWord", this.keyword);
      this.$axios
        .post("/article/search", params)
        .then(res => {
          this.result = res.data.data;
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
    let params = new URLSearchParams();
    params.append("keyWord", "");
    //获得所有博文数据
    this.$axios
      .post("/article/search", params)
      .then(res => {
        this.result = res.data.data;
      })
      .catch(err => {
        console.error(err);
      });
  }
};
</script>

<style>
.search1 {
  width: 55%;
  height: 40px;
  border-radius: 20px;
  font-size: 24px;
  text-indent: 1em;
}
.item1 {
  position: relative;
  text-align: left;
  border-bottom: 1px rgb(190, 188, 186) solid;
}
.right1 {
  position: absolute;
  font-size: 14px;
  color: rgb(123, 125, 126);
  margin-left: 20px;
  right: 2px;
  bottom: 3px;
}

/* 热点 */
.hots {
  margin: 0 auto;
  width: 86%;
  height: 74%;
  border-radius: 20px;
  background-color: rgba(245, 237, 237, 0.3);
}
</style>
