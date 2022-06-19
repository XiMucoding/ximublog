<template>
  <div class="bg4">
    <el-container class="buju4">
      <el-header style="margin-top: 10px">
        <input
          v-model="keyword"
          placeholder="请输入关键词..."
          class="search4"
          @keyup.enter="find"
        />
      </el-header>
      <el-container>
        <el-main>
          <div style="width: 92%; height: 100%; margin: 0 auto">
            <el-scrollbar style="height: 64%">
              <!-- 结果展示 -->
              <span v-show="result.length == 0">暂无匹配的搜索结果</span>
              <div class="item4" v-for="item in result" :key="item.aid">
                <el-link :href="'/blog?aid=' + item.aid"
                  ><h2 style="margin-top: 8px">{{ item.title }}</h2></el-link
                >
                <p style="padding: 8px">{{ item.summary }}</p>
                <!-- 搜索结果底部右边 -->
                <div class="right4">
                  <span>{{ item.nickName }}</span>
                  <span style="margin-left: 20px; margin-right: 10px">{{
                    item.time
                  }}</span>
                </div>
              </div>
            </el-scrollbar>
          </div>
        </el-main>
        <el-aside width="40%">
          <!-- 热门推荐 -->
          <el-card class="hots">
            <template #header>
              <div class="card-header">
                <h3>今日热点</h3>
              </div>
            </template>
            <div
              v-for="(item, index) in hostList"
              :key="item.hid"
              class="hot_item"
            >
              <el-link
                :href="item.link"
                :type="index < 3 ? 'danger' : 'info'"
                >{{ item.title }}</el-link
              >
            </div>
          </el-card>
        </el-aside></el-container
      >
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      keyword: "",
      result: [],
      hostList: [],
    };
  },
  methods: {
    //搜索内容
    find() {
      let params = new URLSearchParams();
      params.append("keyWord", this.keyword);
      this.$axios
        .post("/article/search", params)
        .then((res) => {
          this.result = res.data.data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
  created() {
    //获得热点
    this.$axios
      .post("/admin/getAllHots")
      .then((res) => {
        this.hostList = res.data.data;
      })
      .catch((err) => {
        console.error(err);
      });
    this.keyword = this.$route.query.keyword;
    let params = new URLSearchParams();
    params.append("keyWord", this.keyword);
    //获得所有博文数据
    this.$axios
      .post("/article/search", params)
      .then((res) => {
        this.result = res.data.data;
      })
      .catch((err) => {
        console.error(err);
      });
  },
};
</script>

<style>
.bg4 {
  background: url("http://120.79.198.192:9003/school/2021-11-03/xiaobai.jpg")
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
.buju4 {
  width: 92%;
  height: 94%;
  margin: 20px auto;
  border-radius: 20px;
}
.search4 {
  width: 88%;
  height: 90%;
  border-color: seashell;
  border-radius: 20px;
  font-size: 24px;
  text-indent: 1em;
}
.item4 {
  position: relative;
  height: 120px;
  text-align: left;
  border-bottom: 1px rgb(190, 188, 186) solid;
}
.right4 {
  position: absolute;
  font-size: 14px;
  color: rgb(123, 125, 126);
  margin-left: 20px;
  right: 2px;
  bottom: 3px;
}

/* 热点 */
.hots {
  margin: 0 auto !important;
  width: 86% !important;
  height: 100% !important;
  border-radius: 20px !important;
  background-color: rgba(245, 237, 237, 0.3) !important;
}
.hot_item {
  font-size: 24px;
  text-align: left;
  margin: 10px 0;
}
.danger {
  color: rgba(255, 43, 43, 0.973);
}
</style>
