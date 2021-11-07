<template lang="pug">

.weekiEmailInput.form-floating

  input.custom_weeki_input.form-control(
    type="email"
    :value="value"
    @input="checkValidEmail"
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
  props: ["label", "value", "name", "req"],

  // Export Input Value
  emits: ['update:value'],

  // Elements Methods
  methods: {

    // Check Email Is Valid
    checkValidEmail(event)
    {
      this.$emit('update:value', event.target.value)
      const valid = !/^\w+([\\.-]?\w+)*@\w+([\\.-]?\w+)*(\.\w{2,3})+$/.test(event.target.value);
      if (valid)
      {
        if (event.target.classList.contains("is-valid")){
          event.target.classList.remove("is-valid")
          event.target.classList.add("is-invalid")
        }
        else
        {
          event.target.classList.add("is-invalid")
        }
      }
      else
      {
        if (event.target.classList.contains("is-invalid"))
        {
          event.target.classList.remove("is-invalid")
          event.target.classList.add("is-valid")
        }
        else
        {
          event.target.classList.add("is-valid")
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
export default class WeekiEmailInput extends Vue {}
</script>