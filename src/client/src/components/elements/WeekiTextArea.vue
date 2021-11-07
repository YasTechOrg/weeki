<template lang="pug">

.weekiTextArea.form-floating

  textarea.custom_weeki_input.form-control(
    :value="value"
    @input="$emit('update:value', $event.target.value)"
    @focus="$emit('inFocus', $event.target)"
    :class="{ 'resize': resize }"
    :style="{ 'height' : getHeight }"
    :name="name"
    :placeholder="label"
    :required="getRequired"
  )

  label {{ label }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["label", "value", "name", "req", "resize", "height"],

  // Export Input Value
  emits: ['update:value', 'inFocus'],

  // Element Computed Variables
  computed: {

    // Get Required
    getRequired()
    {
      return this.req == null || this.req === "true"
    },

    // Get Required
    getResize()
    {
      return this.resize == null || this.resize === "none"
    },

    // Get Height
    getHeight()
    {
      if (this.height != null)
      {
        return this.height
      }
      else
      {
        return false
      }
    }
  }
})

export default class WeekiTextArea extends Vue {}
</script>

<style scoped lang="sass">
.weekiTextArea > textarea.resize
  resize: none!important
</style>