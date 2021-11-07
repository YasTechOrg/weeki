<template lang="pug">

#loader-wrapper.d-flex.align-items-center.justify-content-center

  img( src="../assets/animations/main_loader.svg" alt="Weeki" )

.layout

  header.w-100.bg-white( v-if="layout !== 'account'" )

    .inner.gm

      .desktop.d-flex.justify-content-between.align-items-center

        .left.d-flex.align-items-center

          img.cursor-pointer.logo( src="../assets/img/images/brand/logo.png" @click="goTo('/')" alt="Weeki" )

          .d-flex.flex-nowrap

            router-link.text-decoration-none(
              v-for="item in home_menu"
              :key="item"
              :to="item.path"
              active-class="ac"
            ) {{ item.name }}

        .right.d-flex.align-items-center.justify-content-end

          .search

          .nt

          .language

          .account.btn-group( v-if="checkAuth" )

            WeekiProfile.cursor-pointer(
              :info="userInfo"
              data-bs-toggle="dropdown"
              data-bs-auto-close="true"
              aria-expanded="false"
            )

            ul.dropdown-menu.dropdown-menu-end( aria-labelledby="accountDropdown" )

              li
                router-link.dropdown-item.d-flex.align-items-center( to="/dashboard" active-class="active" )
                  span.material-icons.md-20.me-2 dashboard
                  | Dashboard

              li
                router-link.dropdown-item.d-flex.align-items-center( to="/dashboard/profile" active-class="active" )
                  span.material-icons.md-20.me-2 person
                  | Profile

              li
                router-link.dropdown-item.d-flex.align-items-center( to="/dashboard/password" active-class="active" )
                  span.material-icons.md-20.me-2 lock
                  | Password

              li
                a.dropdown-item.d-flex.align-items-center.cursor-pointer( @click="logoutUser" )
                  span.material-icons.md-20.me-2 logout
                  | Logout

          WeekiButton( text="Login / Register" @click="goTo('/account/login')" v-else )

      .mobile.d-none.row

        .col-4.d-flex.align-items-center.justify-content-start
          img.cursor-pointer( src="../assets/img/icons/icon_menu.svg" alt="menu" )

        .col-4.d-flex.justify-content-center
          img.m-auto.cursor-pointer.logo( src="../assets/img/images/brand/logo.png" @click="goTo('/')" alt="Weeki" )

        .col-4.d-flex.align-items-center.justify-content-end

          .account.btn-group( v-if="checkAuth" )

            WeekiProfile.cursor-pointer(
              :info="userInfo"
              data-bs-toggle="dropdown"
              data-bs-auto-close="true"
              aria-expanded="false"
            )

            ul.dropdown-menu.dropdown-menu-end( aria-labelledby="accountDropdown" )

              li
                router-link.dropdown-item.d-flex.align-items-center( to="/dashboard" active-class="active" )
                  span.material-icons.md-20.me-2 dashboard
                  | Dashboard

              li
                router-link.dropdown-item.d-flex.align-items-center( to="/dashboard/profile" active-class="active" )
                  span.material-icons.md-20.me-2 person
                  | Profile

              li
                router-link.dropdown-item.d-flex.align-items-center( to="/dashboard/password" active-class="active" )
                  span.material-icons.md-20.me-2 lock
                  | Password

              li
                a.dropdown-item.d-flex.align-items-center.cursor-pointer( @click="logoutUser" )
                  span.material-icons.md-20.me-2 logout
                  | Logout

          WeekiIconBtn( icon="icons/icon_login_white.svg" @click="goTo('/account/login')" v-else )

  section( v-if="layout === 'surface' || layout === 'error' || layout === 'single'" data-surface )

    slot

  section( v-if="layout === 'account'"  data-account )

    div( :class="{ 'log' : $route.path === '/account/login' || $route.path === '/account/forgot', 'reg' : $route.path === '/account/register' }" )

      slot

  section( v-if="layout === 'dashboard'"  data-dashboard )

    .inner.gm.flex-nowrap.row

      .sidebar.col-md.p-0.w3-hide-medium.w3-hide-small( :class="dashboardMenu" )

        button.cao_btn.position-relative.d-flex.justify-content-center.align-items-center.cursor-pointer( @click="dashboardMenuToggle" )

          img( src="../assets/img/icons/icon_menu_gray.svg" alt="o_a_c" )

        .mb-24

          .part.mt-24( v-for="(item, index) in dashboard_menu_titles" :key="item" )

            .part_title.d-flex.align-items-center.justify-content-start

              img.mr-16( :src="require(`@/assets/img/icons/${ item.icon }.svg`)" :alt="item.name" )

              p.mb-0( v-if="dashboardMenu === 'open'" ) {{ item.name }}

            .menu_items.pl-16

              router-link.d-flex.align-items-center.justify-content-start.text-decoration-none.mt-24(
                v-for="link in dashboard_menu(index)"
                :key="link"
                active-class="menu_item_active"
                @mouseenter="toggleActiveClass($event.target, link.path)"
                @mouseleave="toggleActiveClass($event.target, link.path)"
                :to="link.path"
              )

                .mi_label.mr-16

                .mi_icon.d-flex.align-items-center.justify-content-start

                  p.mb-0.material-icons.md-20 {{ link.icon }}

                .mi_title.d-flex.align-items-center.justify-content-start( v-if="dashboardMenu === 'open'" )

                  p.mb-0 {{ link.name }}

              a.d-flex.align-items-center.justify-content-start.text-decoration-none.mt-24.cursor-pointer(
                v-if="index === 3"
                @mouseenter="toggleActiveClass($event.target, null)"
                @mouseleave="toggleActiveClass($event.target, null)"
                @click="logoutUser"
              )

                .mi_label.mr-16

                .mi_icon.d-flex.align-items-center.justify-content-start

                  p.mb-0.material-icons.md-20 logout

                .mi_title.d-flex.align-items-center.justify-content-start( v-if="dashboardMenu === 'open'" )

                  p.mb-0 Logout

      .content.col-md

        slot

  footer(  v-if="layout !== 'account'" )

    .inner.gm

      .content.row.m-0

        .col-md-3.p-0.d-flex.flex-column.justify-content-start

          .logo
            img.cursor-pointer( src="../assets/img/images/brand/logo.png" @click="goTo('/')" alt="Weeki" )

          .language

          .contact.mt-24.d-flex.align-items-center
            img.mr-16( src="../assets/img/icons/icon_phone.svg" alt="phone" )
            p.m-0 +49 1514 689615

          .contact.mt-24.d-flex.align-items-center
            img.mr-16( src="../assets/img/icons/icon_mail.svg" alt="mail" )
            p.m-0 info@weeki.de

        .col-md-9.p-0.row.m-0

          .col-sm-6.p-0.row.m-0

            .col-6.p-0.d-flex.flex-column.justify-content-start

              p.mb-16.fw-bolder Company

              router-link.mb-0.mt-16.text-decoration-none( to="/" ) Home

              router-link.mb-0.mt-16.text-decoration-none( to="/" ) About Us

              router-link.mb-0.mt-16.text-decoration-none( to="/" ) Contact Us

              router-link.mb-0.mt-16.text-decoration-none( to="/" ) FAQ

            .col-6.p-0.d-flex.flex-column.justify-content-start

              p.mb-16.fw-bolder Main Tools

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/planning" ) Planning

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/messages" ) Messages

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/email" ) E-Mail

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/contacts" ) My Contacts

          .col-sm-6.p-0.row.m-0

            .col-6.p-0.d-flex.flex-column.justify-content-start

              p.mb-16.fw-bolder Main Process

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/products/add" ) Add Products

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/orders/send" ) Send Orders

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/claim" ) Claim

            .col-6.p-0.d-flex.flex-column.justify-content-start

              p.mb-16.fw-bolder Playful And Functional

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/blog" ) Blog

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/newspapers" ) News Papers

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/weather" ) Weather

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/traffic" ) Traffic

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/dob" ) Date Of Births

              router-link.mb-0.mt-16.text-decoration-none( to="/dashboard/game" ) Game

      .copyright

        .line.w-100

        p.mt-24.mb-0.text-center @ {{ new Date().getFullYear() }} Weeki All Right Reserved. | #[a( :href="authorUrl" ) {{ authorName }}] made this site with 💙
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { mapGetters } from 'vuex'
import WeekiButton from "@/components/elements/WeekiButton.vue"
import WeekiProfile from "@/components/elements/WeekiProfile.vue"
import WeekiIconBtn from "@/components/elements/WeekiIconBtn.vue"
import axios from "axios"
import { getToken } from '@/csrfManager'
import { showToast, Types } from "@/toastManager"

/* eslint @typescript-eslint/no-var-requires: "off" */
@Options({

  // App Components
  components: {
    WeekiButton,
    WeekiProfile,
    WeekiIconBtn
  },

  // App Variables
  data()
  {
    return {
      authorName: require("../../package.json").author.name,
      authorUrl: require("../../package.json").author.url,
      home_menu: [
        { name : "Home", path: "/" },
        { name : "About", path: "/about" },
        { name : "Contact", path: "/contact" },
        { name : "Faq", path: "/faq" }
      ],

      userInfo: { firstname: "", lastname: "", name: "", access: [] },

      dashboard_menu_titles: [
        { name: "Main Tools", icon: "icon_setting_gray" },
        { name: "Main Process", icon: "icon_refresh_gray" },
        { name: "Playful And Functional", icon: "icon_dashboard_gray" },
        { name: "Account", icon: "icon_account_gray" },
      ],

      dm: [
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
      ]
    }
  },

  // App Watchers
  watch: {

    // Set Layout Title
    $route: {
      immediate: true,
      async handler(to)
      {
        // Set Title
        document.title = to.meta.title || `Weeki`

        // Is Router Loaded
        if (typeof this.$route.name !== 'undefined')
        {

          // Set Title
          document.title = to.meta.title || `${ this.$route.name } | Weeki`

          // Show Loading
          document.getElementById("loader-wrapper")!.classList.remove("h")

          // Load Page
          await this.load()

          // Hide Loading
          document.getElementById("loader-wrapper")!.classList.add("h")
        }
      }
    },
  },

  // App Methods
  methods: {

    // On App Load
    async load()
    {
      await new Promise(resolve => setTimeout(resolve, 500))

      if (this.checkAuth)
      {
        const d: any = this.$store.state.at_time
        const date1: any = new Date(d)
        const date2: any = new Date()
        const diffTime = Math.abs(date2 - date1)
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24))

        if (diffDays >= 5)
        {
          this.$store.commit("removeAuth")

          if (this.layout === 'dashboard')
          {
            this.goTo("/account/login?res=expi")
          }
        }

        if (this.layout === "account")
        {
          this.goTo("/dashboard")
        }

        this.userInfo = await new Promise(resolve =>
        {
          axios
              .get("/api/rest/account/user/get", {
                headers: {
                  "_csrf" : getToken() as any,
                  "Authorization": this.getAuth
                }
              })
              .then(value =>
              {
                resolve(value.data)

                this.$store.commit("setUserData", value.data)

                this.$store.commit("getTasks")

                if (this.layout === 'dashboard')
                {
                  if(this.userInfo["access"].includes(this.$route.meta["id"]))
                  {
                    location.href = "/dashboard?res=da"
                  }
                  else if (this.$route.meta["id"] === "employee" && this.userInfo["role"] === "NORMAL_USER")
                  {
                    location.href = "/dashboard?res=da_nu"
                  }

                  window.scrollTo(0,0)

                  switch (this.$route.query.res)
                  {
                    case "add_task_comp":
                      showToast("System : Task added successfully!", Types.SUCCESS)
                      break

                    case "add_task_err":
                      showToast("System : An error occurred while add new task!", Types.ERROR)
                      break

                    case "da":
                      showToast("System : Your access to this page has been restricted by your boss!", Types.ERROR)
                      break

                    case "da_nu":
                      showToast("System : Normal Users don't have access to the employee page", Types.ERROR)
                      break

                    case "update_task_comp":
                      showToast("System : Task edited successfully!", Types.SUCCESS)
                      break

                    case "update_task_err":
                      showToast("System : An error occurred while edit a task!", Types.ERROR)
                      break
                  }
                }
              })
              .catch((err) =>
              {
                console.log(err)
                this.$store.commit("removeAuth")

                showToast("System : Your login has expired", Types.WARNING)

                if (this.layout === 'dashboard')
                {
                  this.goTo("/account/login?res=expi")
                }
              })
        })
      }
      else
      {
        if (this.layout === "dashboard")
        {
          this.goTo("/account/login")
        }
      }
    },

    // Go To Route
    goTo(route: string)
    {
      this.$router.push(route)
    },

    // Logout
    logoutUser()
    {
      this.$router.push("/account/login?res=logout")
    },

    // Toggle Dashboard Menu Active Class
    toggleActiveClass(t: any, path: string)
    {
      if (typeof path !== "undefined")
      {
        if(path !== this.$route.path)
        {
          t.classList.toggle("menu_item_active")
        }
      }
    },

    dashboard_menu(index): any[]
    {
      const master = this.dm[index]

      let final: any[] = []

      if (index === 0)
      {
        final.push(master[0])

        if (this.userInfo['role'] === 'COMPANY')
        {
          final.push(master[1])
        }

        for (let i = 2; i < master.length; i++)
        {
          if (!this.userInfo['access'].includes(master[i].id))
          {
            final.push(master[i])
          }
        }
      }
      else
      {
        for (let i = 0; i < master.length; i++)
        {
          if (!this.userInfo['access'].includes(master[i].id))
          {
            final.push(master[i])
          }
        }
      }

      return final
    },

    dashboardMenuToggle()
    {
      if(this.dashboardMenu !== "open")
      {
        this.$store.commit("changeDashboardMenuState", "open")
      }
      else
      {
        this.$store.commit("changeDashboardMenuState", "close")
      }
    }
  },

  // App Computed Variables
  computed: {

    // Get Page Layout
    layout(): string
    {
      return this.$route.meta["layout"]
    },

    ...mapGetters([
        "checkAuth",
        "getAuth"
    ]),

    dashboardMenu()
    {
      return this.$store.state.d_menu
    }
  }
})
export default class Layout extends Vue {}
</script>

<style src="../assets/sass/layout/header.sass" lang="sass"></style>
<style src="../assets/sass/layout/footer.sass" lang="sass"></style>
<style src="../assets/sass/layout/surface.sass" lang="sass"></style>
<style src="../assets/sass/layout/account.sass" lang="sass"></style>
<style src="../assets/sass/layout/dashboard.sass" lang="sass"></style>