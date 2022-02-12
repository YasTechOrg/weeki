<template lang="pug">

vue3-progress

.appLayer
  Layout
    router-view

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({
  components: {
    Layout: () => import(/* webpackChunkName: "layout" */"@/components/Layout.vue")
  },
  mounted() {
    this.$progress.finish()
  },
  created() {
    this.$progress.start()

    this.$router.beforeEach((to, from, next) =>
    {
      this.$progress.start()
      next()
    })

    this.$router.afterEach((to, from) =>
    {
      this.$progress.finish()
    })
  },
})
export default class App extends Vue {}
</script>

<style src="./assets/sass/public/style.sass" lang="sass"></style>
<style src="./assets/sass/public/elements.sass" lang="sass"></style>