import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
const Home = () => import(/* webpackChunkName: "surface" */ "@/views/surface/Home.vue")
const Faq = () => import(/* webpackChunkName: "surface" */ "@/views/surface/Faq.vue")
const Contact = () => import(/* webpackChunkName: "surface" */ "@/views/surface/Contact.vue")
const About = () => import(/* webpackChunkName: "surface" */ "@/views/surface/About.vue")
const Forgot = () => import(/* webpackChunkName: "account" */ "@/views/account/Forgot.vue")
const Register = () => import(/* webpackChunkName: "account" */ "@/views/account/Register.vue")
const Login = () => import(/* webpackChunkName: "account" */ "@/views/account/Login.vue")
const Dashboard = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Dashboard.vue")
const Employee = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Employee.vue")
const Messages = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Messages.vue")
const Planning = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Planning.vue")
const Email = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Email.vue")
const MyContacts = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/MyContacts.vue")
const AddProducts = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/AddProducts.vue")
const MyProducts = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/MyProducts.vue")
const Bookmarks = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Bookmarks.vue")
const SendOrders = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/SendOrders.vue")
const ReceivedOrders = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/ReceivedOrders.vue")
const Claim = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Claim.vue")
const Blog = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Blog.vue")
const Newspaper = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Newspaper.vue")
const Weather = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Weather.vue")
const Traffic = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Traffic.vue")
const DateOfBirths = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/DateOfBirths.vue")
const Game = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Game.vue")
const Profile = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Profile.vue")
const Password = () => import(/* webpackChunkName: "dashboard" */ "@/views/dashboard/Password.vue")

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
  routes,
})

export default router
