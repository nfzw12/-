
import { createRouter, createWebHashHistory } from "vue-router"
import Layout from '@/layout/Layout'

const routes = [
  {
    path: '/',
    redirect: '/index',
  },
  {
    // 登录界面
    path: "/login",
    name: "login",
    component: () =>
      import(/*webpackChunkName:'Login'*/ "@/views/Login.vue")
  }, {
    path: '/index',
    component: Layout,
    meta: { requireAdmin: true},
    redirect: '/sysuser',
    name: 'index',
    children: [
      {
        path: "/sysuser",
        name: "员工管理",
        icon: 'User',
        meta: { keepAlive: true },
        component: () => import(/*webpackChunkName: 'SysUser'*/'@/views/sys/User.vue')
      }, {
        path: "/detail",
        name: "员工详情",
        meta: { keepAlive: false },
        component: () => import(/*webpackChunkName: 'UserDetail'*/'@/views/sys/UserDetail.vue')
      }
    ]
  },
  //  管理员路由 => start
  {
    // 企业管理
    path: "/sysent",
    redirect: '/sysent',
    meta: { requireAdmin: true},
    component: Layout,
    children: [
      {
        name: "部门管理",
        path: "/sysent",
        icon: 'OfficeBuilding',
        meta: { keepAlive: false },
        component: () => import(/*webpackChunkName: 'SysEnt'*/'@/views/sys/Ent.vue')
      }
    ]
  }, {
    // 岗位管理
    path: "/syspost",
    redirect: '/syspost',
    component: Layout,
    meta: { requireAdmin: true},
    children: [
      {
        name: "招聘管理",
        path: "/syspost",
        icon: 'Tickets',
        meta: { keepAlive: false },
        component: () => import(/*webpackChunkName: 'SysPost'*/'@/views/sys/Post.vue')
      }
    ]
  }, {
    // 简历来源管理
    path: "/resume",
    component: Layout,
    meta: { requireAdmin: true},
    redirect: '/resume',
    children: [
      {
        name: "考勤管理",
        path: "/resume",
        icon: 'Location',
        meta: { keepAlive: false },
        component: () => import(/*webpackChunkName: 'SysResume'*/'@/views/sys/Resume.vue')
      }
    ]
  }
  // // 管理员路由 => end
  // // 普通用户路由 => start
  , 
  {
    // 企业
    path: "/ent",
    redirect: '/ent',
    component: Layout,
    meta: { requireAdmin: false},
    children: [
      {
        name: "员工信息管理",
        path: "/ent",
        icon: 'OfficeBuilding',
        meta: { keepAlive: false },
        component: () => import(/*webpackChunkName: 'Ent'*/'@/views/Ent.vue')
      }
    ]
  }
  // 普通用户路由 => end
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
