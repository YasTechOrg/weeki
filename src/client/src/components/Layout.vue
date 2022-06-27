<script lang="ts" setup>
/* eslint @typescript-eslint/no-var-requires: "off" */
/* eslint-disable  @typescript-eslint/no-explicit-any */
/* eslint-disable  @typescript-eslint/no-this-alias */

// ToDo : Login Process

// Imports
const WeekiButton = defineAsyncComponent(() => import("@/components/elements/WeekiButton.vue"))
const WeekiProfile = defineAsyncComponent(() => import("@/components/elements/WeekiProfile.vue"))
const WeekiIconBtn = defineAsyncComponent(() => import("@/components/elements/WeekiIconBtn.vue"))
const WeekiNormalModal = defineAsyncComponent(() => import("@/components/elements/WeekiNormalModal.vue"))
const WeekiTextInput = defineAsyncComponent(() => import("@/components/elements/WeekiTextInput.vue"))
const WeekiTextArea = defineAsyncComponent(() => import("@/components/elements/WeekiTextArea.vue"))
const WeekiIconButton = defineAsyncComponent(() => import("@/components/elements/WeekiIconButton.vue"))
const MobileMenuItem = defineAsyncComponent(() => import("@/components/components/MobileMenuItem.vue"))
const MobileMenu = defineAsyncComponent(() => import("@/components/components/MobileMenu.vue"))
import {useDropzone} from "vue3-dropzone"
import Swal from "sweetalert2"
import store from "@/store"
import {computed, defineAsyncComponent, onMounted, ref, watch} from "vue"
import router from "@/router"

// variables
const files = ref([])
const onlineRemovedItemsProfileEdit = ref([])
const authorName = ref(require("../../package.json").author.name)
const authorUrl = ref(require("../../package.json").author.url)
const home_menu = ref([
  { name : "Home", path: "/" },
  { name : "About", path: "/about" },
  { name : "Contact", path: "/contact" },
  { name : "Faq", path: "/faq" }
])

const layout = computed(() => router.currentRoute.value.meta["layout"] as string)
const checkAuth = computed(() => store.getters.checkAuth)
const unSeenNotification = computed(() => store.state.unSeenNotification)
const dashboard_menu_titles = ref([
  { name: "Main Tools", icon: "icon_setting_gray" },
  { name: "Main Process", icon: "icon_refresh_gray" },
  { name: "Playful And Functional", icon: "icon_dashboard_gray" },
  { name: "Account", icon: "icon_account_gray" },
])
const dm = ref([
  [
    { name: "Dashboard", icon: "dashboard", path: "/dashboard", id: "dashboard" },
    { name: "Employee", icon: "people", path: "/dashboard/employee", id: "employee" },
    { name: "Planning", icon: "calendar_today", path: "/dashboard/planning", id: "planning" },
    { name: "Messages", icon: "message", path: "/dashboard/messages", id: "messages" },
    { name: "E-Mail", icon: "email", path: "/dashboard/email", id: "e_mail" },
    { name: "My Contacts", icon: "account_circle", path: "/dashboard/contacts", id: "my_contacts" },
  ],
  [
    { name: "Add Products", icon: "create_new_folder", path: "/dashboard/products/add", id: "add_products" },
    { name: "My Products", icon: "inbox", path: "/dashboard/products/self", id: "my_products" },
    { name: "Bookmarks", icon: "bookmark", path: "/dashboard/bookmarks", id: "bookmarks" },
    { name: "Send Orders", icon: "featured_play_list", path: "/dashboard/orders/send", id: "orders_send" },
    { name: "Received Orders", icon: "move_to_inbox", path: "/dashboard/orders/received", id: "orders_received" },
    { name: "Claim", icon: "restore_page", path: "/dashboard/claim", id: "claim" },
  ],
  [
    { name: "Blog", icon: "info", path: "/dashboard/blog", id: "blog" },
    { name: "Newspaper", icon: "description", path: "/dashboard/newspapers", id: "newspaper" },
    { name: "Weather", icon: "light_mode", path: "/dashboard/weather", id: "weather" },
    { name: "Traffic", icon: "traffic", path: "/dashboard/traffic", id: "traffic" },
    { name: "Date Of Births", icon: "cake", path: "/dashboard/dob", id: "date_of_births" },
    { name: "Game", icon: "games", path: "/dashboard/game", id: "game" },
  ],
  [
    { name: "Profile", icon: "person", path: "/dashboard/account/profile", id: "profile" },
    { name: "Password", icon: "lock", path: "/dashboard/account/password", id: "password" },
  ]
])
const userInfo = computed(() => store.state.userData)
const dashboardMenu = computed(() => store.state.d_menu)

// Watchers
watch(files, async () =>
{
  console.log(files.value)
  const rendered: string[] = []

  for (let i = 0; i < files.value.length; i++)
  {
    const reader = new FileReader()

    reader.addEventListener("load", function ()
    {
      rendered.push(reader.result as string)
    }, false)

    reader.readAsDataURL(files.value[i])
  }
  console.log(rendered)
})

const toggleMobileSidebar = () => document.querySelector("#mobile_menu")!.classList.toggle("d-none")
const logoutUser = () => router.push("/account/login?res=logout")

const dashboardMenuToggle = () =>
{
  if(dashboardMenu.value !== "open") store.commit("changeDashboardMenuState", "open")
  else store.commit("changeDashboardMenuState", "close")
}

const dashboard_menu = (index) =>
{
  const master: any[] = dm.value[index]

  let final: any[] = []

  if (index === 0)
  {
    final.push(master[0])

    if (userInfo.value['role'] === 'COMPANY') final.push(master[1])

    for (let i = 2; i < master.length; i++)
      if (!userInfo.value['access'].includes(master[i].id)) final.push(master[i])
  }
  else
    for (let i = 0; i < master.length; i++)
      if (!userInfo.value['access'].includes(master[i].id)) final.push(master[i])

  return final
}

</script>

<template>

<div class="layout d-flex flex-column">

  <!-- Header -->
  <header class="w-100 bg-white" v-if="layout !== 'account'">
    <div class="inner gm">

      <!-- Desktop -->
      <div class="desktop d-flex justify-content-between align-items-center">
        <div class="left d-flex align-items-center">
          <img src="../assets/img/images/brand/logo.png" @click="$router.push('/')" alt="Weeki" class="cursor-pointer logo">
          <div class="d-flex flex-nowrap">
            <router-link
                 class="text-decoration-none"
                 v-for="item in home_menu"
                 :key="item"
                 :to="item.path"
                 active-class="ac">
              {{ item.name }}
            </router-link>
          </div>
        </div>
        <div class="right d-flex align-items-center justify-content-end">
          <!-- ToDO -->
          <div class="search"></div>
          <div class="nt"></div>
          <div class="language"></div>
          <div class="account btn-group" v-if="checkAuth">
            <WeekiProfile
                class="cursor-pointer"
                :info="userInfo"
                data-bs-toggle="dropdown"
                data-bs-auto-close="true"
                aria-expanded="false"/>

            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="accountDropdown" >
              <li><router-link class="dropdown-item d-flex align-items-center" to="/dashboard" active-class="active">
                <span class="material-icons md-20 me-2">dashboard</span>
                Dashboard
              </router-link></li>
              <li><router-link class="dropdown-item d-flex align-items-center" to="/dashboard/profile" active-class="active">
                <span class="material-icons md-20 me-2">person</span>
                Profile
              </router-link></li>
              <li><router-link class="dropdown-item d-flex align-items-center" to="/dashboard/password" active-class="active">
                <span class="material-icons md-20 me-2">lock</span>
                Password
              </router-link></li>
              <li><a class="dropdown-item d-flex align-items-center" @click="logoutUser">
                <span class="material-icons md-20 me-2">logout</span>
                Logout
              </a></li>
            </ul>
          </div>
          <WeekiButton text="Login / Register" @click="$router.push('/account/login?back=' + $route.path)" v-else/>
        </div>
      </div>

      <!-- Mobile -->
      <div class="mobile d-none row">
        <div class="col-4 d-flex align-items-center justify-content-start">
          <img src="../assets/img/icons/icon_menu.svg" alt="menu" @click="toggleMobileSidebar" class="cursor-pointer">
        </div>
        <div class="col-4 d-flex justify-content-center">
          <img src="../assets/img/images/brand/logo.png"
               @click="$router.push('/')" alt="Weeki"
               class="m-auto cursor-pointer logo">
        </div>
        <div class="col-4 d-flex align-items-center justify-content-end">
          <div class="account btn-group" v-if="checkAuth">
            <WeekiProfile
                class="cursor-pointer"
                :info="userInfo"
                data-bs-toggle="dropdown"
                data-bs-auto-close="true"
                aria-expanded="false"/>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="accountDropdown">
              <li>
                <router-link
                    class="dropdown-item d-flex align-items-center"
                    to="/dashboard" active-class="active">
                  <span class="material-icons md-20 me-2">dashboard</span>
                  Dashboard
                </router-link>
              </li>
              <li>
                <router-link
                    class="dropdown-item d-flex align-items-center"
                    to="/dashboard/profile" active-class="active">
                  <span class="material-icons md-20 me-2">person</span>
                  Profile
                </router-link>
              </li>
              <li>
                <router-link
                    class="dropdown-item d-flex align-items-center"
                    to="/dashboard/password" active-class="active">
                  <span class="material-icons md-20 me-2">lock</span>
                  Password
                </router-link>
              </li>
              <li>
                <a
                    class="dropdown-item d-flex align-items-center cursor-pointer"
                    @click="logoutUser">
                  <span class="material-icons md-20 me-2">logout</span>
                  Logout
                </a>
              </li>
            </ul>
          </div>
          <WeekiIconBtn
              icon="icons/icon_login_white.svg"
              @click="$router.push('/account/login?back=' + $route.path)"
              v-else/>
        </div>
      </div>
    </div>
  </header>

  <MobileMenu :dm="dm" :title="dashboard_menu_titles"/>

  <section class="flex-grow-1" v-if="layout === 'surface' || layout === 'error' || layout === 'single'" data-surface>
    <slot/>
  </section>

  <section class="flex-grow-1" v-if="layout === 'account'" data-account>
    <div :class="{ 'log' : $route.path === '/account/login' || $route.path === '/account/forgot', 'reg' : $route.path === '/account/register' }">
      <slot></slot>
    </div>
  </section>

  <section class="flex-grow-1" v-if="!(['account', 'dashboard', 'single', 'surface'].includes(layout))"></section>

  <section class="flex-grow-1" v-if="layout === 'dashboard'" data-dashboard>
    <div class="inner gm flex-nowrap row">
      <div class="sidebar col-md p-0 w3-hide-medium w3-hide-small" :class="dashboardMenu">
        <button class="cao_btn position-relative d-flex justify-content-center align-items-center cursor-pointer" @click="dashboardMenuToggle">
          <img src="../assets/img/icons/icon_menu_gray.svg" alt="o_a_c">
        </button>

        <div class="mb-24">
          <div class="part mt-24" v-for="(item, index) in dashboard_menu_titles" :key="item">
            <div class="part_title d-flex align-items-center justify-content-start">
              <img :src="require(`@/assets/img/icons/${ item.icon }.svg`)"
                   :alt="item.name" class="mr-16">
              <p class="mb-0" v-if="dashboardMenu === 'open'">
                {{ item.name }}
              </p>
            </div>

            <div class="menu_items pl-16" v-if="typeof userInfo['access'] !== 'undefined' && userInfo['access'] != null">
              <router-link
                  class="d-flex align-items-center justify-content-start text-decoration-none mt-24"
                  v-for="link in dashboard_menu(index)"
                  :key="link"
                  active-class="menu_item_active"
                  :to="link.path">
                <div class="mi_label mr-16"></div>

                <div class="mi_icon d-flex align-items-center justify-content-start">
                  <p class="mb-0 material-icons md-20">
                    {{ link.icon }}
                  </p>
                </div>

                <div class="mi_title d-flex align-items-center justify-content-start" v-if="dashboardMenu === 'open'">
                  <p class="mb-0">
                    {{ link.name }}
                  </p>
                </div>
              </router-link>
              <a class="d-flex align-items-center justify-content-start text-decoration-none mt-24 cursor-pointer"
                 v-if="index === 3"
                 @click="logoutUser">
                <div class="mi_label mr-16"></div>

                <div class="mi_icon d-flex align-items-center justify-content-start">
                  <p class="mb-0 material-icons md-20">logout</p>
                </div>

                <div class="mi_title d-flex align-items-center justify-content-start" v-if="dashboardMenu === 'open'">
                  <p class="mb-0">Logout</p>
                </div>
              </a>
            </div>
          </div>
        </div>
      </div>
      <div class="content col-md">
        <slot/>
      </div>
    </div>
  </section>
</div>
</template>

<style src="../assets/sass/layout/header.sass" lang="sass" scoped></style>
<style src="../assets/sass/layout/footer.sass" lang="sass" scoped></style>
<style src="../assets/sass/layout/surface.sass" lang="sass"></style>
<style src="../assets/sass/layout/account.sass" lang="sass"></style>
<style src="../assets/sass/layout/dashboard.sass" lang="sass"></style>