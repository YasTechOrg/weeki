<template lang="pug">

#loader-wrapper

.layout

  header

  main

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
