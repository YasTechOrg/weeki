<!--suppress ALL -->
<template lang="pug">

#register

  img.logo( src="../../assets/img/images/brand/logo.png" alt="Weeki" )

  .d-flex.position-relative

    img.me-auto.h-100.position-absolute( src="../../assets/img/icons/icon_arrow_left.svg" @click="goTo('/')" alt="back" )

    p.fw-bolder.m-auto.mb-0.w-100.col-sm-4 Register

  .reg_tab.mt-40

    ul.nav.nav-pills.mb-5.row( role="tablist" )

      li.nav-item.col-4( role="presentation" )

        button.nav-link.active(
          id="register_pills-employee-tab"
          data-bs-toggle="pill"
          data-bs-target="#register_pills-employee"
          type="button"
          role="tab"
          aria-controls="register_pills-employee"
          aria-selected="true"
        ) Employee

      li.nav-item.col-4( role="presentation" )

        button.nav-link(
          id="register_pills-company-tab"
          data-bs-toggle="pill"
          data-bs-target="#register_pills-company"
          type="button"
          role="tab"
          aria-controls="register_pills-company"
          aria-selected="false"
        ) Company

      li.nav-item.col-4( role="presentation" )

        button.nav-link(
          id="register_pills-normal-tab"
          data-bs-toggle="pill"
          data-bs-target="#register_pills-normal"
          type="button"
          role="tab"
          aria-controls="register_pills-normal"
          aria-selected="false"
        ) Normal User

    .tab-content

      .tab-pane.fade.show.active(
        id="register_pills-employee"
        role="tabpanel"
        aria-labelledby="register_pills-employee-tab"
      )

        form( method="post" action="/api/controller/account/register" )

          input( name="role" type="text" hidden value="EMPLOYEE" )
          input( :value="require('@/csrfManager').getToken()" name="_csrf" hidden )

          .row

            .col-sm-6

              WeekiTextInput( type="text" label="First Name" name="firstname" )

            .col-sm-6

              WeekiTextInput( type="text" label="Last Name" name="lastname" )

          .row

            .col-sm-6

              WeekiEmailInput( label="Email Address" name="email" )

            .col-sm-6

              WeekiTextInput( type="tel" label="Phone Number" name="phoneNumber" )

          .row

            .col-sm-4

              WeekiOptionalDropDown( name="company" label="Company" )

                option( v-for="company in allCompanies" :key="company.name" :value="company.email" ) {{ company.name }}

            .col-sm-4

              WeekiOptionalDropDown( name="country" label="Country" v-model:value="countrySelectValueEmployee" )

                option( v-for="country in countries" :key="country['nativeName']" :value="((country.name != null) ? country.name : country['nativeName'])" ) {{ country["nativeName"] }}

            .col-sm-4

              WeekiNormalDropDown( name="city" :list="returnableCityEmployee" label="City" )

          .row

            .col-12

              WeekiPasswordInput( label="Password" name="password" )

          WeekiButton( type="submit" text="Create Account" )

      .tab-pane.fade(
        id="register_pills-company"
        role="tabpanel"
        aria-labelledby="register_pills-company-tab"
      )

        form( method="post" action="/api/controller/account/register" )

          input( name="role" type="text" hidden value="COMPANY" )
          input( :value="require('@/csrfManager').getToken()" name="_csrf" hidden )

          .row

            .col-sm-4

              WeekiTextInput( label="Company Name" name="name" type="text" )

            .col-sm-4

              WeekiOptionalDropDown( name="country" label="Country" v-model:value="countrySelectValueCompany" )

                option( v-for="country in countries" :key="country['nativeName']" :value="((country.name != null) ? country.name : country['nativeName'])" ) {{ country["nativeName"] }}

            .col-sm-4

              WeekiNormalDropDown( name="city" :list="returnableCityCompany" label="City" )

          .row

            .col-sm-6

              WeekiEmailInput( label="Email Address" name="email" )

            .col-sm-6

              WeekiTextInput( label="Phone Number" name="phoneNumber" type="tel" )

          .row

            .col-12

              WeekiTextInput( label="Address" name="address" type="text" )

          .row

            .col-12

              WeekiPasswordInput( label="Password" name="password" )

          WeekiButton( type="submit" text="Create Account" )

      .tab-pane.fade(
        id="register_pills-normal"
        role="tabpanel"
        aria-labelledby="register_pills-normal-tab"
      )

        form( method="post" action="/api/controller/account/register" )

          input( name="role" type="text" hidden value="NORMAL_USER" )
          input( :value="require('@/csrfManager').getToken()" name="_csrf" hidden )

          .row

            .col-sm-6

              WeekiTextInput( type="text" label="First Name" name="firstname" )

            .col-sm-6

              WeekiTextInput( type="text" label="Last Name" name="lastname" )

          .row

            .col-sm-6

              WeekiEmailInput( label="Email Address" name="email" )

            .col-sm-6

              WeekiTextInput( type="tel" label="Phone Number" name="phoneNumber" )

          .row

            .col-sm-6

              WeekiOptionalDropDown( name="country" label="Country" v-model:value="countrySelectValueNormal" )

                option( v-for="country in countries" :key="country['nativeName']" :value="((country.name != null) ? country.name : country['nativeName'])" ) {{ country["nativeName"] }}

            .col-sm-6

              WeekiNormalDropDown( name="city" :list="returnableCityNormal" label="City" )

          .row

            .col-12

              WeekiTextInput( label="Address" name="address" type="text" )

          .row

            .col-12

              WeekiPasswordInput( label="Password" name="password" )

          WeekiButton( type="submit" text="Create Account" )

  p.m-0.mt-3.reg_btn have an account? #[router-link( to="/account/login" )  login here]

</template>

<script lang="ts">
import WeekiButton from "@/components/elements/WeekiButton.vue"
const countries = require('@/assets/json/countries.json')
const cities = require('@/assets/json/cities.json')
import { Options, Vue } from 'vue-class-component'
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue"
import WeekiEmailInput from "@/components/elements/WeekiEmailInput.vue"
import WeekiOptionalDropDown from "@/components/elements/WeekiOptionalDropDown.vue"
import WeekiNormalDropDown from "@/components/elements/WeekiNormalDropDown.vue"
import WeekiPasswordInput from "@/components/elements/WeekiPasswordInput.vue"
import { showToast, Types } from '@/toastManager'
import axios from "axios"
import { getToken } from "@/csrfManager"

/* eslint @typescript-eslint/no-var-requires: "off" */
@Options({

  // Components
  components: {
    WeekiButton,
    WeekiTextInput,
    WeekiEmailInput,
    WeekiOptionalDropDown,
    WeekiNormalDropDown,
    WeekiPasswordInput
  },

  // Page Variables
  data()
  {
    return {
      allCompanies: [],
      countries: countries,
      cities: cities.data,
      countrySelectValueEmployee: "",
      countrySelectValueCompany: "",
      countrySelectValueNormal: "",
      returnableCityEmployee: [],
      returnableCityCompany: [],
      returnableCityNormal: [],
      res: this.$route.query.res
    }
  },

  // On Page Load
  mounted()
  {
    // Get All Companies
    axios
        .get("/api/rest/account/company/all", { headers: { "_csrf" : getToken() as any, } })
        .then(value => this.allCompanies = value.data)

    // User Is Exist
    if (this.res === "exi")
    {
      showToast("System : This user is available!", Types.ERROR)
    }
  },

  // Page Methods
  methods: {

    // Go To Route
    goTo(route: string)
    {
      this.$router.push(route)
    }
  },

  // Page Variable Listeners
  watch: {

    countrySelectValueEmployee(val)
    {
      this.returnableCityEmployee = []
      this.cities.find(city =>
      {
        if (val === city.country)
        {
          if (!this.returnableCityEmployee.includes(city.city))
          {
            this.returnableCityEmployee.push(city.city)
          }
        }
        else
        {
          if (!this.returnableCityEmployee.includes(val))
          {
            this.returnableCityEmployee.push(val)
          }
        }
      })
    },

    countrySelectValueCompany(val)
    {
      this.returnableCityCompany = []
      this.cities.find(city =>
      {
        if (val === city.country)
        {
          if (!this.returnableCityCompany.includes(city.city))
          {
            this.returnableCityCompany.push(city.city)
          }
        }
        else
        {
          if (!this.returnableCityCompany.includes(val))
          {
            this.returnableCityCompany.push(val)
          }
        }
      })
    },

    countrySelectValueNormal(val)
    {
      this.returnableCityNormal = []
      this.cities.find(city =>
      {
        if (val === city.country)
        {
          if (!this.returnableCityNormal.includes(city.city))
          {
            this.returnableCityNormal.push(city.city)
          }
        }
        else
        {
          if (!this.returnableCityNormal.includes(val))
          {
            this.returnableCityNormal.push(val)
          }
        }
      })
    }
  }
})

export default class Register extends Vue {}
</script>

<style scoped src="../../assets/sass/page/register.sass" lang="sass"></style>