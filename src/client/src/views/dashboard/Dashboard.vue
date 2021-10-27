<!--suppress ALL -->
<template lang="pug">

#dashboard


</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Page Components
  components: {

  },

  // Page Variables
  mounted()
  {
    // Get Token
    const token: string = this.$route.query.at

    // Check If Url Have Token
    if (token != null && token !== "")
    {
      this.$store.commit("setAuth", token)
      location.href = "/dashboard"
    }
  }
})
export default class Dashboard extends Vue {}
</script>

<style scoped src="../../assets/sass/page/dashboard.sass" lang="sass"></style>