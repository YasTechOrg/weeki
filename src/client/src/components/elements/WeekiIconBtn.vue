<template lang="pug">

button.weekiIconBtn.btn( :type="type" )

  //img( :src="require('@/assets/img/' + icon)" alt="img" )

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["icon", "type"],
})
export default class WeekiIconBtn extends Vue {}
</script>

<style scoped lang="sass">
.weekiIconBtn
  background-color: #68B64D!important
  box-shadow: 0 3px 10px #00000029!important
  border-radius: 4px!important
  padding: 8px!important
  margin: 0!important
</style>