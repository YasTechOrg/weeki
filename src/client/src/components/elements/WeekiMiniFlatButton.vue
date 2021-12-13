<template lang="pug">

button.weekiMiniFlatButton.btn( :type="type" ) {{ text }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["text", "type"],
})

export default class WeekiMiniFlatButton extends Vue {}
</script>

<style scoped lang="sass">
.weekiMiniFlatButton
  background-color: #68B64D!important
  border-radius: 4px!important
  padding: 8px 20px!important
  color: white!important
  font-size: 12px!important
  margin: 0!important
</style>