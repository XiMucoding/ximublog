import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../views/user/login";
import Register from "../views/user/register";
import Shouye from "../views/pages/shouye";
import Edit from "../views/pages/edit";
import My from "../views/user/user";
import Blog from "../views/pages/blog";
import Search from "../views/pages/search";
import Admin from "../views/admin/admin";
import mUser from "../views/admin/m_user";
import mBlog from "../views/admin/m_blog";
import mAnn from "../views/admin/announcement";
import Message from "../views/admin/message";
import Wechat from "../views/pages/wechat";
import Music from "../views/pages/music";
import Review from "../views/pages/review";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
  },
  {
    path: "/shouye",
    name: "Shouye",
    component: Shouye,
  },
  {
    path: "/edit",
    name: "Edit",
    component: Edit,
  },
  {
    path: "/my",
    name: "My",
    component: My,
  },
  {
    path: "/blog",
    name: "Blog",
    component: Blog,
  },
  {
    path: "/search",
    name: "Search",
    component: Search,
  },
  {
    path: "/wechat",
    name: "Wechat",
    component: Wechat,
  },
  {
    path: "/music",
    name: "Music",
    component: Music,
  },
  {
    path: "/review",
    name: "Review",
    component: Review,
  },
  {
    path: "/admin",
    name: "Admin",
    component: Admin,
    //嵌套子路由
    children: [
      {
        path: "/admin/mUser",
        name: "mUser",
        component: mUser,
      },
      {
        path: "/admin/mBlog",
        name: "mBlog",
        component: mBlog,
      },
      {
        path: "/admin/mAnn",
        name: "mAnn",
        component: mAnn,
      },
      {
        path: "/admin/message",
        name: "Message",
        component: Message,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
