<template lang="pug">

#employee

  WeekiSearchInput( placeholder="Search Employee Name" v-model:value="employeeSearch" )

  .mt-40

    .title.fw-bolder.mb-12 New Requests To Be Added To The Company Account

    .nf.mb-0( v-if="new_employee.length === 0" ) No New Employee Found!

    div( v-else )

      .employee.d-flex.justify-content-between.aligalign-items-center( v-for="user in filteredNewEmployees" :key="user" )

        .d-flex.justify-content-start.align-items-center

          WeekiProfile.employee( :info="user" )

          .d-flex.flex-column.align-items-start.justify-content-center.pl-16

            p.mb-0.fw-bolder {{ user["firstname"] }} {{ user["lastname"] }}

            p.mb-0 {{ timeCalc(user["addTime"]) }}

        .na_employee.d-none

          .d-flex.align-items-center.justify-content-end.h-100

            .reject.cursor-pointer.d-flex.justify-content-center.align-items-center( @click="employeeActions(user['email'], 'reject')" )

              | Reject

            .accept.cursor-pointer.d-flex.justify-content-center.align-items-center.ml-16( @click="employeeActions(user['email'], 'accept')" )

              | Accept

  .mt-40

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import WeekiSearchInput from "@/components/elements/WeekiSearchInput.vue"
import WeekiProfile from "@/components/elements/WeekiProfile.vue"
import moment from "moment"
import axios from "axios"
import { getToken } from "@/csrfManager"
import { mapGetters } from "vuex"

@Options({

  // Page Components
  components: {
    WeekiSearchInput,
    WeekiProfile
  },

  // Page Variables
  data()
  {
    return {
      employeeSearch: "",
      new_employee: [],
      employee: [],

      activeAccess: 0,
      access: {
        planning: true,
        messages: true,
        bookmarks: true,
        add_products: true,
        my_products: true,
        send_orders: true,
        orders_received: true,
        claim: true,
        weather: true,
        traffic: true,
        newspaper: true,
        my_contacts: true,
        e_mail: true,
        blog: true,
        date_of_births: true,
        game: true
      },
      profileModalUser: {},
      profileModalCompany: {},
      profileModalStars: null
    }
  },

  // On Page Load
  async mounted()
  {
    this.getEmployees()
  },

  // Page Methods
  methods: {

    // Calculate Time
    timeCalc(time)
    {
      return moment().from(new Date(time), true)
    },

    // Employee Actions
    employeeActions(id, action)
    {
      //
    },

    // Get Employees
    getEmployees()
    {
      this.employee = []
      this.new_employee = []

      axios
          .get(`/api/rest/employee/${ this.userInfo["email"] }`, {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": this.getAuth
            }
          })

          .then(value =>
          {
            const data: any[] = value.data

            for (let i = 0; i < data.length; i++)
            {
              if(data[i]["accepted"])
              {
                this.employee.push(data[i])
              }
              else
              {
                this.new_employee.push(data[i])
              }
            }
          })

          .catch(reason => console.log(reason))
    },

  },

  // Page Computed Variables
  computed: {

    // Get Filtered New Employees
    filteredNewEmployees()
    {
      return this.new_employee.filter((el) =>
      {
        return (el["firstname"] + el["lastname"]).toLowerCase().indexOf(this.employeeSearch.toLowerCase()) !== -1
      })
    },

    // Get Filtered New Employees
    filteredEmployees()
    {
      return this.employee.filter((el) =>
      {
        return (el["firstname"] + el["lastname"]).toLowerCase().indexOf(this.employeeSearch.toLowerCase()) !== -1
      })
    },

    // Get User Data
    userInfo()
    {
      return this.$store.state.userData
    },

    // Get Store Getters
    ...mapGetters([
      "checkAuth",
      "getAuth"
    ]),
  }
})

export default class Employee extends Vue {}
</script>

<style scoped src="../../assets/sass/page/employee.sass" lang="sass"></style>