<template lang="pug">

.chatWidget

  WeekiSearchInput( placeholder="Search" )

  p Contact List

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import WeekiSearchInput from "@/components/elements/WeekiSearchInput.vue"

@Options({

  // Widget Components
  components: {
    WeekiSearchInput
  },
})

export default class ChatWidget extends Vue {}
</script>

<style src="../../assets/sass/widget/chatWidget.sass" scoped lang="sass"></style>