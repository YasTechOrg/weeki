<!--suppress ALL -->
<template lang="pug">

.weekiNormalDropDown.form-floating( :class="{ 'mb-3': normalMb }" )

  select.form-select.custom_weeki_input(
    :aria-label="name + ' DropDown'"
    @change="$emit('update:value', $event.target.value)"
    required
    :name="name"
  )

    option( selected ) Select The {{ label }}

    option( v-for="item in list" :key="item" :value="item" ) {{ item }}

  label {{ label }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Name
  name: "WeekiNormalDropDown",

  // Element Props
  props: ["label", "value", "name", "list", "mb"],

  // Export Dropdown Value
  emits: ['update:value'],

  // Elements Computed Variables
  computed: {

    // Element Margin Bottom
    normalMb()
    {
      return this.mb == null || this.mb === "true"
    },
  }
})

export default class WeekiNormalDropDown extends Vue {}
</script>

<style scoped lang="sass">
.weekiNormalDropDown > select
  padding-right: 30px!important
</style>