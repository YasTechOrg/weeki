<template lang="pug">

.weekiBorderedIconButton.cursor-pointer.d-flex.align-items-center.justify-content-center

  p.mb-0.d-flex.align-items-center

    span.mb-0.material-icons.md-18 {{ icon }}

    | {{ text }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["text", "icon"],
})

export default class WeekiBorderedIconButton extends Vue {}
</script>

<style scoped lang="sass">
.weekiBorderedIconButton
  border: 2px solid #68B64D!important
  border-radius: 4px!important
  padding: 8px 20px!important
  font-size: 12px!important
  color: #68B64D
  transition: 0.2s

  > img
    margin-right: 8px!important

.weekiBorderedIconButton:hover
  background-color: #68B64D!important
  color: white!important
</style>