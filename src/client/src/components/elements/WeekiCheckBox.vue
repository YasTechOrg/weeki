<template lang="pug">

.weekiCheckBox.form-check

  input.form-check-input.cursor-pointer(
    @change="$emit('update:value', $event.target.checked)"
    type="checkbox"
    :value="value"
    :name="name"
    :checked="checked"
  )

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["value", "name", "checked"],

  // Export Input Value
  emits: ['update:value'],
})

export default class WeekiCheckBox extends Vue {}
</script>

<style scoped lang="sass">
.weekiCheckBox

  > input
    border-radius: 360%!important
    border-width: 2px!important

  > input:checked
    background-color: #63ba56!important
    border-color: #63ba56!important

  > input:focus
    border-color: #63ba56!important
</style>