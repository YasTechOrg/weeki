<template lang="pug">

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

          WeekiButton( text="Login / Register" @click="goTo('/account/login?back=' + $route.path)" v-else )

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

          WeekiIconBtn( icon="icons/icon_login_white.svg" @click="goTo('/account/login?back=' + $route.path)" v-else )

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

            .menu_items.pl-16( v-if="typeof this.userInfo['access'] !== 'undefined' && this.userInfo['access'] != null" )

              router-link.d-flex.align-items-center.justify-content-start.text-decoration-none.mt-24(
                v-for="link in dashboard_menu(index)"
                :key="link"
                active-class="menu_item_active"
                :to="link.path"
              )

                .mi_label.mr-16

                .mi_icon.d-flex.align-items-center.justify-content-start

                  p.mb-0.material-icons.md-20 {{ link.icon }}

                .mi_title.d-flex.align-items-center.justify-content-start( v-if="dashboardMenu === 'open'" )

                  p.mb-0 {{ link.name }}

              a.d-flex.align-items-center.justify-content-start.text-decoration-none.mt-24.cursor-pointer(
                v-if="index === 3"
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

              router-link.mb-0.mt-16.text-decoration-none( to="/about" ) About Us

              router-link.mb-0.mt-16.text-decoration-none( to="/contact" ) Contact Us

              router-link.mb-0.mt-16.text-decoration-none( to="/faq" ) FAQ

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

WeekiNormalModal(
  v-if="checkPage(['employee', 'my_contacts'])"
  name="user_profile"
  title="Profile"
  max-width="588px"
  max-height="73%"
  scrollable="true"
  mfs="true"
  height="unset"
)

  .nf.d-flex.justify-content-center.align-items-center.pt-12.pb-12( v-if="Object.keys(getProfileModalData).length === 0" )

    img( src="../assets/animations/main_loader.svg" alt="Loading..." )

  .content( v-else )

    .d-flex.justify-content-between.align-items-center

      .d-flex.justify-content-start.align-items-center

        WeekiProfile.profile( :info="getProfileModalData" )

        .d-flex.flex-column.justify-content-center.align-items-start.pl-16

          p.fw-bolder {{ getProfileModalData['firstname'] }} {{ getProfileModalData['lastname'] }}

          .d-flex( v-if="Object.keys(getProfileModalData['rate']).length === 0" )

            img( src="../assets/img/icons/icon_empty_star.svg" v-for="i in 5" :key="i" :class="{ 'mr-8' : i !== getStars }"  alt="s" )

          .d-flex( v-else-if="getStars === 5" )

            img( src="../assets/img/icons/icon_star.svg" v-for="i in 5" :key="i" :class="{ 'mr-8' : i !== getStars }"  alt="s" )

          .d-flex( v-else )

            img( src="../assets/img/icons/icon_star.svg" v-for="i in getStars" :key="i" :class="{ 'mr-8' : i !== 5 }"  alt="s" )
            img( src="../assets/img/icons/icon_empty_star.svg" v-for="i in 5 - getStars" :key="i" :class="{ 'mr-8' : i !== 5 }"  alt="s" )

      .d-flex.justify-content-end.align-items-center

        .d-flex.flex-column.justify-content-center.align-items-end

          img.cursor-pointer(
            src="../assets/img/icons/icon_addUser_gray.svg"
            v-if="!userInfo['contacts'].includes(getProfileModalData['email'])"
            @click="addToContacts(getProfileModalData['email'])"
            alt="Add To Contacts"
          )

          img.cursor-pointer(
            src="../assets/img/icons/icon_removeUser_gray.svg"
            @click="removeFromContacts(getProfileModalData['email'])"
            alt="Remove From Contacts"
            v-else
          )

          p.mb-0.mt-12.cursor-pointer(
            data-bs-target="#WeekiNormalModal_user_profile_rate"
            data-bs-toggle="modal"
          )

    .pl-40.mt-20

      .d-flex.justify-content-start.align-items-center.pt-16.pb-16
        img( src="../assets/img/icons/icon_phone.svg" alt="phone number" )
        p.mb-0.ml-16 {{ getProfileModalData["phoneNumber"] }}

      .d-flex.justify-content-start.align-items-center.pt-16.pb-16
        img( src="../assets/img/icons/icon_mail.svg" alt="email" )
        p.mb-0.ml-16 {{ getProfileModalData["email"] }}

      .d-flex.justify-content-start.align-items-center.pt-16.pb-16
        img( src="../assets/img/icons/icon_company.svg" alt="company" )
        p.mb-0.ml-16 {{ getProfileModalCompany["companyName"] }}

      .d-flex.justify-content-start.align-items-center.pt-16.pb-16
        img( src="../assets/img/icons/icon_location.svg" alt="location" )
        p.mb-0.ml-16 {{ getProfileModalData["address"] }}

WeekiNormalModal(
  v-if="checkPage(['employee', 'my_contacts'])"
  name="user_profile_rate"
  title="Rate"
  max-width="380px"
  max-height="73%"
  scrollable="true"
  mfs="true"
  height="unset"
)

  p.mb-24 what's your rate?

  .he.d-flex.justify-content-start.align-items-center.mb-12

    WeekiProfile.employee( :info="getProfileModalData" )

    p.ml-16.mb-0.fw-bolder {{ getProfileModalData['firstname'] }} {{ getProfileModalData['lastname'] }}

  .st.d-flex.justify-content-around.justify-content-center.mb-24

    img.cursor-pointer.unselectable(
      src="../assets/img/icons/icon_star_big.svg"
      v-for="(i, index) in getSelfStars"
      @click="setProfileModalStars(index + 1)"
      :key="i" alt="s"
    )

    img.cursor-pointer.unselectable(
      src="../assets/img/icons/icon_empty_star_big.svg"
      v-for="(i, index) in (5 - getSelfStars)"
      @click="setProfileModalStars(getSelfStars + (index + 1))"
      :key="i" alt="s"
    )

  WeekiButton.float-end( text="Confirm" @click="submitUserStars" )

WeekiNormalModal(
  v-if="checkPage(['my_products'])"
  name="product_edit"
  title="Edit Product"
  max-width="588px"
  max-height="73%"
  scrollable="true"
  mfs="true"
  height="unset"
)

  p.mb-24.fw-bold.text-black Carrots, Anabella, Tehran, 300-400, A

  .upload_part.d-flex.justify-content-start.align-items-center.mb-20

    .p_upload_card.position-relative( v-for="i in 5" :key="i" )

      img( src="../assets/img/images/no_img.png" alt="product img" )

      img.position-absolute( src="../assets/img/icons/icon_delete_gray.svg" alt="remove" )

    .p_upload_card.add_p

      .p_card.d-flex.justify-content-center.align-items-center

        img( src="../assets/img/icons/icon_plus_small_green.svg" alt="add" )

  .p_detail_part.row.m-0

    .col-sm-6

      WeekiTextInput.mb-3( name="packing" label="Packing" auto-complete="false" )

    .col-sm-6

      WeekiTextInput.mb-3( name="location" label="Location" auto-complete="false" )

    .col-sm-6

      WeekiTextInput.mb-3( name="amount" label="Amount(kg)" auto-complete="false" type="number" )

    .col-sm-6

      WeekiTextInput.mb-3( name="ppk" label="Price Per Kg(€)" auto-complete="false" type="number" )

  WeekiTextArea.mb-24( label="Description" resize="false" height="200px" )

  WeekiButton.float-end( text="Edit Product" )

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { mapGetters } from 'vuex'
import axios from "axios"
import { getToken } from '@/csrfManager'
import { showToast, Types } from "@/toastManager"
import SockJS from "sockjs-client"
import Stomp from "webstomp-client"
import WeekiButton from "@/components/elements/WeekiButton.vue"
import WeekiProfile from "@/components/elements/WeekiProfile.vue"
import WeekiIconBtn from "@/components/elements/WeekiIconBtn.vue"
import WeekiNormalModal from "@/components/elements/WeekiNormalModal.vue"
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue"
import WeekiTextArea from "@/components/elements/WeekiTextArea.vue"

/* eslint @typescript-eslint/no-var-requires: "off" */
/* eslint-disable  @typescript-eslint/no-explicit-any */

@Options({

  // App Components
  components: {
    WeekiButton,
    WeekiProfile,
    WeekiIconBtn,
    WeekiNormalModal,
    WeekiTextInput,
    WeekiTextArea
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

      unSeenNotification: false,

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

          // Disable All Schedule
          this.$store.commit("disableNotificationsSchedule")

          // Load Page
          await this.load()
        }
      }
    },
  },

  // App Methods
  methods: {

    // On App Load
    async load()
    {
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

        axios
            .get("/api/rest/account/user/get", {
              headers: {
                "_csrf" : getToken() as any,
                "Authorization": this.getAuth
              }
            })
            .then(async value =>
            {
              await this.$store.commit("setUserData", value.data)
              await this.$store.commit("clearProfileModal")
              await this.$store.commit("getTasks")
            })
            .catch((err) =>
            {
              console.log(err)
              this.$store.commit("removeAuth")

              showToast("System : Your login has expired", Types.WARNING)

              if (this.layout === 'dashboard')
              {
                this.goTo("/account/login?res=expi&back=" + this.$route.path)
              }
            })
            .finally(async () =>
            {
              if (this.layout === 'dashboard')
              {
                if (this.userInfo["access"].includes(this.$route.meta["id"]))
                {
                  location.href = "/dashboard?res=da"
                }
                else if (this.$route.meta["id"] === "employee" && this.userInfo["role"] === "NORMAL_USER")
                {
                  location.href = "/dashboard?res=da_nu"
                }

                window.scrollTo(0, 0)

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

                // Get Notifications
                await this.getNotifications()
                const seenNotifications: boolean[] = []

                for (const item in this.notifications)
                {
                  seenNotifications.push(item["seen"])
                }

                if (seenNotifications.includes(false))
                {
                  this.unSeenNotification = true
                }

                // Define this
                // eslint-disable-next-line @typescript-eslint/no-this-alias
                const t = this

                // Connect To Socket Server
                this.socket = new SockJS(location.origin + "/wst", [], {
                  sessionId: function ()
                  {
                    return t.userInfo["socketId"]
                  }
                })

                // Define Stomp Client
                this.stompClient = Stomp.over(this.socket)

                // Disable Stomp Logging
                this.stompClient.debug = () => {
                  //
                }

                // Connect Stomp
                this.stompClient.connect({}, () =>
                {

                  // Subscribe Stomp
                  this.stompClient.subscribe('/user/notifications/get', function (response)
                  {
                    // Show Notification
                    showToast(JSON.parse(response.body).content, Types.INFO)

                    t.getNotifications()
                    t.unSeenNotification = true
                  }, {
                    "_csrf" : getToken() as any,
                    "Authorization": this.getAuth
                  })
                })
              }
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

    // Get All Notifications
    async getNotifications()
    {

      // Await Request
      this.$store.commit("setNotifications", await new Promise(resolve =>
      {

        // Send Request
        axios

            // Send
            .get("/api/rest/notifications/get", {
              headers: {
                "_csrf" : getToken() as any,
                "Authorization": this.getAuth
              }
            })

            // On Success
            .then(value =>
            {
              resolve(value.data)
            })

            // On Error
            .catch(reason => console.log(reason))
      }))
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
    },

    // Remove From Contacts
    removeFromContacts(id: string)
    {
      this.$store.commit("removeFromContacts", { id : id, url : this.$route.path, promise : false })
    },

    // Add To Contacts
    addToContacts(id: string)
    {
      this.$store.commit("addToContacts", { id : id, url : this.$route.path })
    },

    // Check Page
    checkPage(ids: string[]): boolean
    {
      return ids.includes(this.$route.meta['id'])
    },

    submitUserStars()
    {
      this.$store.commit("submitUserStars", { id : this.getProfileModalData["email"], url : this.$route.path, stars : this.$store.state.profileModalStars })
    },

    setProfileModalStars(value)
    {
      this.$store.commit("setProfileModalStars", value)
    },
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
    },

    getProfileModalData()
    {
      return this.$store.state.profileModal
    },

    getProfileModalCompany()
    {
      return this.$store.state.profileModalCo
    },

    getProfileModalStars()
    {
      return this.$store.state.profileModalStars
    },

    notifications()
    {
      return this.$store.state.notifications
    },

    // Get User Data
    userInfo()
    {
      return this.$store.state.userData
    },

    // Get Stars
    getStars()
    {
      if (this.getProfileModalData["rate"] !== null && typeof this.getProfileModalData["rate"] !== "undefined")
      {
        const size: any[] = Object.values(this.getProfileModalData["rate"])
        let num = 0
        for (let i = 0; i < size.length; i++)
        {
          num = num + size[i]
        }
        return Math.round(num / size.length)
      }
      else
      {
        return 0
      }
    },

    // Get Stars
    getSelfStars(): number
    {
      if (this.getProfileModalData["rate"] !== null && typeof this.getProfileModalData["rate"] !== "undefined")
      {
        if (this.getProfileModalStars == null)
        {
          const users = Object.keys(this.getProfileModalData["rate"])

          if (users.includes(this.userInfo["email"]))
          {
            return this.getProfileModalData["rate"][this.userInfo["email"]]
          }
          else
          {
            return 0
          }
        }
        else
        {
          return this.getProfileModalStars
        }
      }
      else
      {
        return 0
      }
    },
  }
})

export default class Layout extends Vue {}
</script>

<style src="../assets/sass/layout/header.sass" lang="sass"></style>
<style src="../assets/sass/layout/footer.sass" lang="sass"></style>
<style src="../assets/sass/layout/surface.sass" lang="sass"></style>
<style src="../assets/sass/layout/account.sass" lang="sass"></style>
<style src="../assets/sass/layout/dashboard.sass" lang="sass"></style>