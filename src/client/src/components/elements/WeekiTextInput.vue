<template lang="pug">

.weekiTextInput.form-floating

  input.custom_weeki_input.form-control(
    :type="type"
    :value="value"
    @input="$emit('update:value', $event.target.value)"
    @focus="$emit('inFocus', $event.target)"
    :name="name"
    :placeholder="label"
    :autocomplete="getAutoComplete"
    :list="'autoCompleteList' + name"
    :required="getRequired"
    :min="getMin"
    :max="getMax"
  )

  datalist( v-if="list != null" :id="'autoCompleteList' + name" )

    option( v-for="item in list" :key="item" :value="item" )

  label {{ label }}

  img( v-if="icon != null" :src="require('@/assets/img/' + icon)" alt="name" class="custom_input_sh_position" )

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["label", "value", "name", "icon", "list", "type", "mb", "req", "autoComplete", "min", "max"],

  // Export Input Value
  emits: ['update:value', 'inFocus'],

  // Element Computed Variables
  computed: {

    // Modal Margin Bottom
    normalMb()
    {
      return this.mb == null || this.mb === "true";
    },


    // Get Required
    getRequired()
    {
      return this.req == null || this.req === "true"
    },

    // Get Auto Complete
    getAutoComplete()
    {
      if (this.autoComplete == null)
      {
        return "on"
      }
      else
      {
        return "off"
      }
    },

    getMin()
    {
      if (this.min !== null)
      {
        return this.min
      }
      else return false
    },

    getMax()
    {
      if (this.max !== null)
      {
        return this.max
      }
      else return false
    }
  }
})

export default class WeekiTextInput extends Vue {}
</script>