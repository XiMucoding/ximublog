import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

// 全局使用element ui
import Element from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
Vue.use(Element);

// 全局使用axios
import axios from "axios";
Vue.prototype.$axios = axios;
//axios请求携带cookie
axios.defaults.withCredentials = true;
//请求头前缀
axios.defaults.baseURL = "http://localhost:8088";

//全局样式引入
import "./assets/css/global.css";

// mavon-editor
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
Vue.use(mavonEditor);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
