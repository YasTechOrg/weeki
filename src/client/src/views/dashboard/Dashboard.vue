<template lang="pug">

#dashboard.row.m-0

  .col-md-6.pr-12.d-flex.flex-column.justify-content-start.align-items-center.p-0

    TasksWidget

    NotificationsWidget.mt-16

  .col-md-6.pr-12.d-flex.flex-column.justify-content-start.align-items-center.p-0

    ChatWidget

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import TasksWidget from "@/components/widgets/TasksWidget.vue"
import NotificationsWidget from "@/components/widgets/NotificationsWidget.vue"
import ChatWidget from "@/components/widgets/ChatWidget.vue"

@Options({

  // Page Components
  components: {
    TasksWidget,
    NotificationsWidget,
    ChatWidget
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