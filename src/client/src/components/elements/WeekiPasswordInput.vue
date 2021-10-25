<template lang="pug">

.weekiPasswordInput.form-floating

  input.custom_weeki_input.form-control(
    type="password"
    :value="value"
    @input="checkValidPassword"
    :name="name"
    :placeholder="label"
    :required="getRequired"
  )

  label {{ label }}

  span.custom_input_sh_position.material-icons( @click="changeVisibility" ) visibility

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Name
  name: "WeekiButton",

  // Element Props
  props: ["label", "value", "name", "req"],

  // Export Input Value
  emits: ['update:value'],

  // Elements Methods
  methods: {

    // Change Password Visibility
    changeVisibility(event)
    {
      const el = event.target.parentElement.children[0]
      if (el.type === "text")
      {
        el.type = "password"
        event.target.innerHTML = "visibility"
      }
      else if (el.type === "password")
      {
        el.type = "text"
        event.target.innerHTML = "visibility_off"
      }
    },

    // Check Valid Password
    checkValidPassword(event)
    {
      this.$emit('update:value', event.target.value)
      const el = event.target
      const pass = el.value.length
      if (pass >= 3)
      {
        if (!el.classList.contains("is-valid"))
        {
          el.classList.add("is-valid")
        }
        if (el.classList.contains("is-invalid"))
        {
          el.classList.remove("is-invalid")
        }
      }
      else
      {
        if (el.classList.contains("is-valid"))
        {
          el.classList.remove("is-valid")
        }
        if (!el.classList.contains("is-invalid"))
        {
          el.classList.add("is-invalid")
        }
      }
    }
  },

  // Element Computed Variables
  computed: {

    // Get Required
    getRequired()
    {
      return this.req == null || this.req === "true"
    }
  }
})
export default class WeekiPasswordInput extends Vue {}
</script>

<style scoped lang="sass">
.weekiPasswordInput

  .is-invalid, .is-valid
    background-position: right calc(2.6em + .1875rem) center!important

  input::-ms-reveal, input::-ms-clear
    display: none

</style>