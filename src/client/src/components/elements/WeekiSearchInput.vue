<template lang="pug">

.weekiSearchInput.input-group

  .input-group-prepend.h-auto

    span.input-group-text.h-100

      img( src="../../assets/img/icons/icon_search_gray.svg" alt="s" )

  input.form-control(
    :value="value"
    @input="$emit('update:value', $event.target.value)"
    type="text"
    :name="name"
    :placeholder="placeholder"
    :required="getRequired"
  )

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

export default class WeekiSearchInput extends Vue {}
</script>

<style scoped lang="sass">
.weekiSearchInput

  > input
    border-left: none!important
    border-bottom-left-radius: 0!important
    border-top-left-radius: 0!important
    padding-left: 0!important
    border-color: #E4E4E4!important

  > div > span
    background-color: white
    border-color: #E4E4E4!important
</style>