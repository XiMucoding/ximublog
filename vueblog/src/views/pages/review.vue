<template>
  <div class="bg">
    <el-container style="width: 100%; height: 100%">
      <!-- 歌曲/音乐 -->
      <el-aside class="userbox">
        <div style="margin-top: 20px">
          <el-divider> 歌曲/音乐 </el-divider>
          <!-- 展示 -->
          <div style="width: 100%; height: 580px; margin: 0 auto">
            <el-scrollbar style="height: 100%">
              <div
                class="fanfollow"
                v-for="(item, index) in list"
                :key="item.mid"
                @click="selectFile(index)"
              >
                <el-tag
                  :type="item.status == 1 ? 'success' : ''"
                  class="fanfollowstatus"
                  >{{ item.status == 1 ? "音频" : "视频" }}</el-tag
                >
                {{ item.name }}
              </div>
            </el-scrollbar>
            <el-button type="primary" @click="dialogVisible = true"
              >上传你的文件</el-button
            >
            <!-- 上传文件弹窗 -->
            <el-dialog
              :visible.sync="dialogVisible"
              title="上传音频/视频"
              width="30%"
            >
              <el-form
                ref="ruleForm"
                :model="ruleForm"
                :rules="rules"
                label-width="120px"
              >
                <el-form-item label="文件名" prop="name">
                  <el-input
                    v-model="ruleForm.name"
                    placeholder="文件名"
                  ></el-input>
                </el-form-item>
                <el-form-item label="文件类型" prop="status">
                  <el-radio-group v-model="ruleForm.status">
                    <el-radio label="1" size="large">音频</el-radio>
                    <el-radio label="2" size="large">视频</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="上传文件" prop="file">
                  <input
                    type="file"
                    ref="inputFile"
                    accept="video/*,audio/*"
                    @change="getFile"
                  />
                </el-form-item>
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button type="primary" @click="updateFile">上传</el-button>
                </span>
              </template>
            </el-dialog>
          </div>
        </div>
      </el-aside>
      <el-main class="fanfollowbox">
        <el-empty
          description="未选择视频"
          :image-size="400"
          v-if="curr.status != 2"
        />
        <video
          controls
          width="96%"
          :src="curr.url"
          v-if="curr.status == 2"
        ></video>
        <audio
          controls
          :src="curr.url"
          v-if="curr.status == 1"
          id="music"
        ></audio>
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      //是否显示弹窗
      dialogVisible: false,
      //表单
      ruleForm: {
        name: "",
        status: "",
        file: "",
      },
      //文件上传后返回的链接
      url: "",
      curr: {
        mid: 0,
        name: "",
        status: 0,
        url: "",
      },
      list: [],
      rules: {
        name: [
          {
            required: true,
            message: "请输入文件名！",
            trigger: "blur",
          },
        ],
        status: [
          {
            required: true,
            message: "请选择文件类型！",
            trigger: "change",
          },
        ],
        file: [
          {
            required: true,
            message: "请选择文件！",
            trigger: "change",
          },
        ],
      },
    };
  },
  methods: {
    //获取所有对象
    getMinioList() {
      let params = new URLSearchParams();
      this.$axios
        .post("/file/getAllList", params)
        .then((res) => {
          this.list = res.data.data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //选择播放的文件
    selectFile(index) {
      this.curr = this.list[index];
    },
    //上传文件后将ruleForm.file改成"ok"
    getFile() {
      this.ruleForm.file = "ok";
    },
    //上传完自己的文件后存进数据库
    updateFile() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          //因为上传需要时间，所以先返回提示，收回弹窗
          this.dialogVisible = false;
          this.$notify({
            title: "Warning",
            message: "正在上传文件，请等待！",
            duration: 3 * 1000,
            type: "warning",
          });
          //正式处理文件上传等
          var files = this.$refs.inputFile.files;
          var fd = new FormData();
          fd.append("file", files[0]);
          this.$axios
            .post("/minio/put", fd, { "Content-type": "multipart/form-data" })
            .then(
              (res) => {
                if (res.status == 200) {
                  // 上传成功后的处理
                  //上传到表单
                  let params = new URLSearchParams();
                  params.append("name", this.ruleForm.name);
                  params.append("status", this.ruleForm.status);
                  params.append("url", res.data);
                  this.$axios
                    .post("/file/updateFile", params)
                    .then((res) => {
                      //上传完文件就上传到数据库
                      if (res.data.code == 200) {
                        this.$notify({
                          title: "Success",
                          message: "上传成功",
                          duration: 3 * 1000,
                          type: "success",
                        });
                      } else {
                        this.$notify({
                          title: "Error",
                          message: "上传文件成功，但存储进数据库失败",
                          duration: 3 * 1000,
                          type: "error",
                        });
                      }
                    })
                    .catch((err) => {
                      console.error(err);
                    });
                  //清空上传过的信息
                  this.ruleForm.name = "";
                  this.ruleForm.status = "";
                  this.ruleForm.file = "";
                } else {
                  //上传图片失败
                  this.$notify({
                    title: "Error",
                    message: "上传文件失败",
                    duration: 3 * 1000,
                    type: "error",
                  });
                }
              },
              (err) => {
                // 出现错误时的处理
                console.log(err);
              }
            );
        } else {
          return false;
        }
      });
    },
  },
  created() {
    this.getMinioList();
  },
};
</script>

<style>
.bg {
  background: url("http://120.79.198.192:9003/school/2021-10-02/xieshan.jpg")
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
.fanfollowstatus {
  border-radius: 20px 0 0 20px !important;
  height: 42px !important;
  line-height: 42px !important;
  position: absolute !important;
  top: -1px !important;
  left: -1px !important;
}
.fanfollow {
  width: 280px;
  height: 40px;
  line-height: 40px;
  margin: 3% auto;
  position: relative;
  border-radius: 20px;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
}
.fanfollow:hover {
  background-color: #f89898;
}
.fanfollowbox {
  padding: 20px 20px;
  width: 80%;
  height: 94%;
  margin: 20px 20px;
  border-radius: 20px;
  background-color: white;
}
.userbox {
  padding: 0;
  width: 80%;
  height: 94%;
  margin: 20px 20px;
  border-radius: 20px;
  background-color: white;
}
#music {
  position: fixed;
  top: 30px;
  right: 30px;
}
</style>
