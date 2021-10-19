import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/Home.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { layout: "surface" },
  },
  {
    path: '/about',
    name: 'About Us',
    component: Home,
    meta: { layout: "surface" },
  },
  {
    path: '/contact',
    name: 'Contact Us',
    component: Home,
    meta: { layout: "surface" },
  },
  {
    path: '/faq',
    name: 'FAQ',
    component: Home,
    meta: { layout: "surface" },
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
