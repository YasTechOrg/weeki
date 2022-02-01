<template lang="pug">

.weekiDateInput.form-floating.position-relative

  input.form-control.custom_weeki_input(
    :value="value"
    @input="$emit('update:value', $event.target.value)"
    type="date"
    :name="name"
    :placeholder="placeholder"
    :required="getRequired"
  )
  label {{ placeholder }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["placeholder", "value", "name", "req"],

  // Export Input Value
  emits: ['update:value'],

  // Element Computed Variables
  computed: {

    // Get Required
    getRequired()
    {
      return this.req == null || this.req === "true"
    },
  }
})

export default class WeekiDateInput extends Vue {}
</script>

<style scoped lang="sass">
.weekiDateInput

  > input
    border-right: none!important
    border-bottom-right-radius: 0!important
    border-top-right-radius: 0!important
    border-color: #E4E4E4!important


  input::-webkit-calendar-picker-indicator
    opacity: 1 !important
    background-image: url("../../assets/img/icons/icon_calendar.svg")

</style>