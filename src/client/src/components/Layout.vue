<!--suppress ALL -->
<template lang="pug">

#loader-wrapper.d-flex.align-items-center.justify-content-center

  img( src="../assets/animations/main_loader.svg" alt="Weeki" )

.layout

  header.w-100.bg-white( v-if="layout !== 'account'" )

    .inner

      .desktop.d-flex.justify-content-between.align-items-center

        .left

          img( src="../assets/img/images/brand/logo.png" )

        .right

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

  footer


</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

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
      await new Promise(resolve => setTimeout(resolve, 1000))
    },
  },

  // App Computed Variables
  computed: {

    // Get Page Layout
    layout(): string
    {
      return this.$route.meta["layout"]
    },
  }
})
export default class Layout extends Vue {}
</script>

<style src="../assets/sass/layout/header.sass" lang="sass"/>

<style src="../assets/sass/layout/surface.sass" lang="sass"/>