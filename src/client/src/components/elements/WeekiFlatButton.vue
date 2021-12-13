<template lang="pug">

button.weekiFlatButton.btn( :type="type" ) {{ text }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["text", "type"],
})

export default class WeekiFlatButton extends Vue {}
</script>

<style scoped lang="sass">
.weekiFlatButton
  background-color: #68B64D!important
  border-radius: 4px!important
  padding: 10px 20px!important
  color: white!important
  font-size: 16px!important
  margin: 0!important
</style>