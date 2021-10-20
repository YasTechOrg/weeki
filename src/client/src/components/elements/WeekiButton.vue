<template lang="pug">

button.weekiButton.btn( :type="type" ) {{ text }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Name
  name: "WeekiButton",

  // Element Props
  props: ["text", "type"],
})
export default class WeekiButton extends Vue {}
</script>

<style scoped lang="sass">
.weekiButton
  background-color: #68B64D!important
  box-shadow: 0 3px 10px #00000029!important
  border-radius: 4px!important
  padding: 10px 20px!important
  color: white!important
  font-size: 16px!important
  font-weight: bold!important
  margin: 0!important
</style>