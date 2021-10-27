<!--suppress ALL -->
<template lang="pug">

.weekiOptionalDropDown.form-floating( :class="{ 'mb-3': normalMb }" )

  select.form-select.custom_weeki_input(
    :aria-label="name + ' DropDown'"
    @change="$emit('update:value', $event.target.value)"
    required
    :name="name"
  )

    option( v-if="ownSelected" selected ) Select The {{ label }}

    slot

  label {{ label }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Name
  name: "WeekiOptionalDropDown",

  // Element Props
  props: ["label", "value", "name", "mb", "sl"],

  // Element Computed Variables
  computed: {

    // Element Margin Bottom
    normalMb()
    {
      return this.mb == null || this.mb === "true"
    },

    // Element Own Selected
    ownSelected()
    {
      return this.sl == null || this.sl === "true"
    },
  }
})
export default class WeekiOptionalDropDown extends Vue {}
</script>