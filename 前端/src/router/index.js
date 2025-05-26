import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/Login.vue'
import Layout from '@/views/layout/Layout.vue' // 引入布局组件
import HomeView from '@/views/Home.vue' // 引入首页组件

const routes = [
  {
    path: '/',
    component: Layout, // 使用 Layout 作为主布局
    meta: { requiresAuth: true },
    children: [
      {
        path: '', // 根路径默认显示首页
        name: 'Home',
        component: HomeView
      },
      // 其他需要显示在布局中的路由可以添加在这里.
      {
        path: '/customer/Customer',
        name: 'Customer',
        component: () => import('@/views/customer/Customer.vue') // 示例路径，根据实际项目调整
      },
      {
        path: '/customer/checkIn',
        name: 'CheckIn',
        component: () => import('@/views/customer/CheckIn.vue') // 示例路径，根据实际项目调整
      },
        {
        path: '/customer/checkOut',
        name: 'CheckOut',
        component: () => import('@/views/customer/CheckOut.vue') // 示例路径，根据实际项目调整
      },
      {
        path: '/nurse/nurseLevel',
        name: 'NurseLevel',
        component: () => import('@/views/nurse/NurseLevel.vue') // 示例路径，根据实际项目调整
      },
      {
        path: '/nurse/nurseRecords',
        name: 'nurseRecords',
        component: () => import('@/views/nurse/NurseRecords.vue') // 示例路径，根据实际项目调整
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/:catchAll(.*)',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const requiresAuth = to.meta.requiresAuth;
  const hasToken = sessionStorage.getItem('token');
  
  if (requiresAuth && !hasToken) {
    next('/login'); // 未登录跳转登录页
  } else {
    next(); // 允许访问，包括已登录用户访问首页
  }
});

export default router