<template lang="pug">

.appLayer
  Layout
    router-view

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import Layout from "@/components/Layout.vue"

@Options({
  components: {
    Layout
  }
})
export default class App extends Vue {}
</script>

<style src="./assets/sass/public/style.sass" lang="sass"></style>
<style src="./assets/sass/public/elements.sass" lang="sass"></style>