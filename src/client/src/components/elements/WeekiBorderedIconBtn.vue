<template lang="pug">

.weekiBorderedIconBtn.cursor-pointer.d-flex.align-items-center.justify-content-center

  p.mb-0.material-icons.md-18 {{ icon }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["icon"],
})

export default class WeekiBorderedIconBtn extends Vue {}
</script>

<style scoped lang="sass">
.weekiBorderedIconBtn
  border: 2px solid #68B64D!important
  border-radius: 4px!important
  padding: 8px 20px
  font-size: 12px!important
  color: #68B64D
  transition: 0.2s

.weekiBorderedIconBtn:hover
  background-color: #68B64D!important
  color: white!important

@media screen and (max-width: 990px)

  .weekiBorderedIconBtn
    padding: 8px!important

</style>