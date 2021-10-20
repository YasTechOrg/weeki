import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from "@/views/Home.vue"
import Faq from "@/views/Faq.vue"
import Contact from "@/views/Contact.vue"
import About from "@/views/About.vue"

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
    component: About,
    meta: { layout: "surface" },
  },
  {
    path: '/contact',
    name: 'Contact Us',
    component: Contact,
    meta: { layout: "surface" },
  },
  {
    path: '/faq',
    name: 'FAQ',
    component: Faq,
    meta: { layout: "surface" },
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
