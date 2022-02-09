<template lang="pug">

button.weekiChipButton.btn( :type="type" :class="{ 'enabled' : enabled }" ) {{ text }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["text", "type", "enabled"],
})

export default class WeekiChipButton extends Vue {}
</script>

<style scoped lang="sass">
.weekiChipButton
  border-radius: 4px!important
  padding: 4px 12px!important
  color: #6E6E6E
  font-size: 14px!important
  background-color: #E4E4E4

.weekiChipButton.enabled, .weekiChipButton:hover
  background-color: #68B64D!important
  color: white!important
</style>