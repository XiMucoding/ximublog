<template>
  <el-container class="bg1">
    <el-aside width="8%">
      <div style="margin-top:100px">
        <router-link to="/admin/mUser" style="text-decoration: none;"
          ><div class="menu">用户</div></router-link
        >
        <router-link to="/admin/mBlog" style="text-decoration: none;"
          ><div class="menu">博客</div></router-link
        >
        <router-link to="/admin/mAnn" style="text-decoration: none;"
          ><div class="menu">公告</div></router-link
        >
        <router-link to="/admin/message" style="text-decoration: none;"
          ><div class="menu">留言</div></router-link
        >
      </div>
    </el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
export default {};
</script>

<style>
.bg1 {
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

.menu {
  padding: 15px 0 0 4px;
  color: black;
  font-size: 20px;
  height: 60px;
  width: 60px;
  margin: 10px auto;
  border-radius: 50%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
</style>
