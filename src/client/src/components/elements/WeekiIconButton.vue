<template lang="pug">

.weekiIconButton.cursor-pointer.d-flex.align-items-center.justify-content-center

  img( :src="require('@/assets/img/' + icon)" alt="img" )

  | {{ text }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["icon", "type", "text"],
})
export default class WeekiIconButton extends Vue {}
</script>

<style scoped lang="sass">
.weekiIconButton
  background-color: #D1E9C9!important
  box-shadow: 0 3px 10px #0000001A!important
  border-radius: 4px!important
  padding: 16px 0!important
  color: #68B64D!important
  font-size: 14px!important

.weekiIconButton:hover
  box-shadow: none!important

.weekiIconButton > img
  margin-right: 8px!important
</style>