import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from "@/views/surface/Home.vue"
import Faq from "@/views/surface/Faq.vue"
import Contact from "@/views/surface/Contact.vue"
import About from "@/views/surface/About.vue"
import Forgot from "@/views/account/Forgot.vue"
import Register from "@/views/account/Register.vue"
import Login from "@/views/account/Login.vue"
import Dashboard from "@/views/dashboard/Dashboard.vue"
import Employee from "@/views/dashboard/Employee.vue"
import Messages from "@/views/dashboard/Messages.vue"
import Planning from "@/views/dashboard/Planning.vue"
import Email from "@/views/dashboard/Email.vue"
import MyContacts from "@/views/dashboard/MyContacts.vue"
import AddProducts from "@/views/dashboard/AddProducts.vue"
import MyProducts from "@/views/dashboard/MyProducts.vue"
import Bookmarks from "@/views/dashboard/Bookmarks.vue"
import SendOrders from "@/views/dashboard/SendOrders.vue"
import ReceivedOrders from "@/views/dashboard/ReceivedOrders.vue"
import Claim from "@/views/dashboard/Claim.vue"
import Blog from "@/views/dashboard/Blog.vue"
import Newspaper from "@/views/dashboard/Newspaper.vue"
import Weather from "@/views/dashboard/Weather.vue"
import Traffic from "@/views/dashboard/Traffic.vue"
import DateOfBirths from "@/views/dashboard/DateOfBirths.vue"
import Game from "@/views/dashboard/Game.vue"
import Profile from "@/views/dashboard/Profile.vue"
import Password from "@/views/dashboard/Password.vue"

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
  },
  {
    path: '/account/login',
    name: 'Login',
    component: Login,
    meta: { layout: "account" },
  },
  {
    path: '/account/register',
    name: 'Register',
    component: Register,
    meta: { layout: "account" },
  },
  {
    path: '/account/forgot',
    name: 'Forgot',
    component: Forgot,
    meta: { layout: "account" },
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
    meta: { layout: "dashboard", id: "dashboard" }
  },
  {
    path: '/dashboard/employee',
    name: 'Employee',
    component: Employee,
    meta: { layout: "dashboard", id: "employee" }
  },
  {
    path: '/dashboard/messages',
    name: 'Messages',
    component: Messages,
    meta: { layout: "dashboard", id: "messages" }
  },
  {
    path: '/dashboard/planning',
    name: 'Planning',
    component: Planning,
    meta: { layout: "dashboard", id: "planning" }
  },
  {
    path: '/dashboard/email',
    name: 'E-mail',
    component: Email,
    meta: { layout: "dashboard", id: "e_mail" }
  },
  {
    path: '/dashboard/contacts',
    name: 'My Contacts',
    component: MyContacts,
    meta: { layout: "dashboard", id: "my_contacts" }
  },
  {
    path: '/dashboard/products/add',
    name: 'Add Products',
    component: AddProducts,
    meta: { layout: "dashboard", id: "add_products" }
  },
  {
    path: '/dashboard/products/self',
    name: 'My Products',
    component: MyProducts,
    meta: { layout: "dashboard", id: "my_products" }
  },
  {
    path: '/dashboard/bookmarks',
    name: 'Bookmarks',
    component: Bookmarks,
    meta: { layout: "dashboard", id: "bookmarks" }
  },
  {
    path: '/dashboard/orders/send',
    name: 'Send Orders',
    component: SendOrders,
    meta: { layout: "dashboard", id: "orders_send" }
  },
  {
    path: '/dashboard/orders/received',
    name: 'Received Orders',
    component: ReceivedOrders,
    meta: { layout: "dashboard", id: "orders_received" }
  },
  {
    path: '/dashboard/claim',
    name: 'Claim',
    component: Claim,
    meta: { layout: "dashboard", id: "claim" }
  },
  {
    path: '/dashboard/blog',
    name: 'Blog',
    component: Blog,
    meta: { layout: "dashboard", id: "blog" }
  },
  {
    path: '/dashboard/newspapers',
    name: 'Newspapers',
    component: Newspaper,
    meta: { layout: "dashboard", id: "newspaper" }
  },
  {
    path: '/dashboard/weather',
    name: 'Weather',
    component: Weather,
    meta: { layout: "dashboard", id: "weather" }
  },
  {
    path: '/dashboard/traffic',
    name: 'Traffic',
    component: Traffic,
    meta: { layout: "dashboard", id: "traffic" }
  },
  {
    path: '/dashboard/dob',
    name: 'Date Of Births',
    component: DateOfBirths,
    meta: { layout: "dashboard", id: "date_of_births" }
  },
  {
    path: '/dashboard/game',
    name: 'Game',
    component: Game,
    meta: { layout: "dashboard", id: "game" }
  },
  {
    path: '/dashboard/account/profile',
    name: 'Profile',
    component: Profile,
    meta: { layout: "dashboard", id: "profile" }
  },
  {
    path: '/dashboard/account/password',
    name: 'Password',
    component: Password,
    meta: { layout: "dashboard", id: "password" }
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
