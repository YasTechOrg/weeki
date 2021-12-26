<!--suppress ALL -->
<template lang="pug">

#sendOrders

  form
    .row.me-0
      p.mb-4.fw-bold.col-12 Enter The Company Name And Product Specifications To Send The Order

      .col-sm-6.col-md-4
        WeekiNormalDropDown( name="type" :list="['dobd', 'domdmo']" label="Type Of Product" )

      .col-sm-6.col-md-4
        WeekiNormalDropDown( name="family" :list="['dobd', 'domdmo']" label="Family Of Product" )

      .col-sm-6.col-md-4
        WeekiOptionalDropDown( name="country" label="Country" v-model:value="countrySelectValue" )
          option( v-for="country in countries" :key="country['nativeName']" :value="((country.name != null) ? country.name : country['nativeName'])" ) {{ country["nativeName"] }}

      .col-sm-3.col-md-4
        WeekiTextInput.mb-3( name="code" label="Code" auto-complete="false" req="true" )

      .col-sm-3.col-md-4
        WeekiTextInput.mb-3( name="grade" label="Grade" auto-complete="false" req="true" )

      .col-sm-6.col-md-4
        WeekiTextInput.mb-3( name="packing" label="Packing" auto-complete="false" req="true" )

      .col-sm-6.col-md-4
        WeekiTextInput.mb-3( name="amount" label="Amount(kg)" auto-complete="false" type="number" req="true" )

      .col-sm-6.col-md-4
        WeekiTextInput.mb-3( name="ppk" label="Price Per Kg(€)" auto-complete="false" type="number" req="true" )

      .col-sm-6.col-md-4
        WeekiOptionalDropDown( name="company" label="Company" )
          option( v-for="company in allCompanies" :key="company.name" :value="company.email" ) {{ company.name }}

      .col-12
        WeekiButton.float-end( text="Send Order" @click="submitForm" )



</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import WeekiNormalDropDown from "@/components/elements/WeekiNormalDropDown.vue"
import WeekiOptionalDropDown from "@/components/elements/WeekiOptionalDropDown.vue"
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"

@Options({

  // Page Components
  components: {
    WeekiNormalDropDown,
    WeekiOptionalDropDown,
    WeekiTextInput,
    WeekiButton
  },
  data(){
    return{
      allCompanies: [],
    }
  }
})

export default class SendOrders extends Vue {}
</script>

