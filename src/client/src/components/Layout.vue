<!--suppress ALL -->
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
            WeekiProfile( v-if="userInfo['name'] != null" :name="userInfo['name']" )
            WeekiProfile( :name="userInfo['firstname']" v-else )

          WeekiButton( text="Login / Register" @click="goTo('/account/login')" v-else )

      .mobile.d-none.row

        .p_left

        .p_center

        .p_right

  section( v-if="layout === 'surface' || layout === 'error' || layout === 'single'" data-surface )

    slot

  section( v-if="layout === 'account'"  data-account )

    slot

  section( v-if="layout === 'dashboard'"  data-dashboard )

    slot

  footer(  v-if="layout !== 'account'" )

    .content.row.m-0

      .col-md-3.p-0.d-flex.flex-column.justify-content-start

        .logo
          img.logo_design.cursor-pointer( src="../assets/img/images/brand/logo.png" @click="goTo('/')" alt="Weeki" )

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
import WeekiButton from "@/components/elements/WeekiButton.vue"
import WeekiProfile from "@/components/elements/WeekiProfile.vue"
import { mapGetters } from 'vuex'

/* eslint @typescript-eslint/no-var-requires: "off" */
@Options({

  // App Components
  components: {
    WeekiButton,
    WeekiProfile
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
    },

    // Go To Route
    goTo(route: string)
    {
      this.$router.push(route)
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
      "checkAuth"
    ]),

  }
})
export default class Layout extends Vue {}
</script>

<style src="../assets/sass/layout/header.sass" lang="sass"></style>
<style src="../assets/sass/layout/footer.sass" lang="sass"></style>
<style src="../assets/sass/layout/surface.sass" lang="sass"></style>