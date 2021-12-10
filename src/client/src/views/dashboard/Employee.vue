<template lang="pug">

#employee

  WeekiSearchInput( placeholder="Search Employee Name" v-model:value="employeeSearch" )

  .mt-40

    p.title.fw-bolder.mb-12 New Requests To Be Added To The Company Account

    p.nf.mb-0( v-if="new_employee.length === 0" ) No New Employee Found!

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

    p.title.fw-bolder.mb-12 Company Employees

    p.nf.mb-0( v-if="employee.length === 0" ) No Employee Found!

    div( v-else )

      .employee.d-flex.justify-content-between.aligalign-items-center( v-for="(user, index) in filteredEmployees" :key="user" )

        .d-flex.justify-content-start.align-items-center

          WeekiProfile.employee( :info="user" )

          .d-flex.flex-column.align-items-start.justify-content-center.pl-16

            p.mb-0.fw-bolder {{ user["firstname"] }} {{ user["lastname"] }}

            p.mb-0 {{ timeCalc(user["addTime"]) }}

        .employee.d-flex.align-items-center.justify-content-end

          .btn-group

            div( data-bs-toggle="dropdown" data-bs-auto-close="true" aria-expanded="false" )

              img.cursor-pointer.unselectable( src="../../assets/img/icons/icon_doted_gray.svg" alt="menu" )

            ul.dropdown-menu.dropdown-menu-start

              li
                a.dropdown-item.d-flex.align-items-center.cursor-pointer(
                  @click="completeEmployee(user['email'])"
                  data-bs-target="#WeekiNormalModal_user_profile"
                  data-bs-toggle="modal"
                )
                  span.material-icons.md-20.me-2 person
                  | View Profile

              li
                router-link.dropdown-item.d-flex.align-items-center( :to="`/dashboard/messages?iu=${ user['email'] }`" )
                  span.material-icons.md-20.me-2 chat
                  | Send Message

              li
                a.dropdown-item.d-flex.align-items-center.cursor-pointer( @click="removeEmployee(user['email'])" )
                  span.material-icons.md-20.me-2 block
                  | Remove Account

              li
                a.dropdown-item.d-flex.align-items-center.cursor-pointer(
                  @click="setAccess(index)"
                  data-bs-target="#WeekiNormalModal_employee_access_edit"
                  data-bs-toggle="modal"
                )
                  span.material-icons.md-20.me-2 check_circle
                  | Access

WeekiNormalModal(
  name="employee_access_edit"
  title="Access"
  max-width="792px"
  max-height="73%"
  scrollable="true"
  mfs="true"
  height="unset"
)

  .row.ms-0.me-0.mb-16

    .col-md-6.p-0.row.m-0

      .col-sm-6.p-0

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.planning" v-model:value="access.planning" )
          p.mb-0 Planning

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.my_products" v-model:value="access.my_products" )
          p.mb-0 My Products

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.weather" v-model:value="access.weather" )
          p.mb-0 Weather

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.e_mail" v-model:value="access.e_mail" )
          p.mb-0 E-Mail

      .col-sm-6.p-0

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.messages" v-model:value="access.messages" )
          p.mb-0 Messages

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.send_orders" v-model:value="access.send_orders" )
          p.mb-0 Send Orders

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.traffic" v-model:value="access.traffic" )
          p.mb-0 Traffic

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.blog" v-model:value="access.blog" )
          p.mb-0 Blog

    .col-md-6.p-0.row.m-0

      .col-sm-6.p-0

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.bookmarks" v-model:value="access.bookmarks" )
          p.mb-0 Bookmarks

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.orders_received" v-model:value="access.orders_received" )
          p.mb-0 Orders Received

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.newspaper" v-model:value="access.newspaper" )
          p.mb-0 Newspaper

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.date_of_births" v-model:value="access.date_of_births" )
          p.mb-0 Date Of Births

      .col-sm-6.p-0

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.add_products" v-model:value="access.add_products" )
          p.mb-0 Add Products

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.claim" v-model:value="access.claim" )
          p.mb-0 Claim

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.my_contacts" v-model:value="access.my_contacts" )
          p.mb-0 My Contacts

        .d-flex.align-items-center.justify-content-start.pt-12.pb-12.pl-20
          WeekiCheckBox( v-model:checked="access.game" v-model:value="access.game" )
          p.mb-0 Game

  WeekiButton.float-end( text="Save Changes" data-bs-dismiss="modal" @click="updateAccess" )

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import moment from "moment"
import axios from "axios"
import { getToken } from "@/csrfManager"
import { mapGetters } from "vuex"
import Swal from "sweetalert2"
import { showToast, Types } from "@/toastManager"
import WeekiSearchInput from "@/components/elements/WeekiSearchInput.vue"
import WeekiProfile from "@/components/elements/WeekiProfile.vue"
import WeekiNormalModal from "@/components/elements/WeekiNormalModal.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"
import WeekiCheckBox from "@/components/elements/WeekiCheckBox.vue"

/* eslint-disable  @typescript-eslint/no-explicit-any */
@Options({

  // Page Components
  components: {
    WeekiSearchInput,
    WeekiProfile,
    WeekiNormalModal,
    WeekiButton,
    WeekiCheckBox
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

    switch (this.$route.query.res)
    {
      case "ac_done":
        showToast("System : Contact added successfully!", Types.SUCCESS)
        break

      case "rc_done":
        showToast("System : Contact removed successfully!", Types.SUCCESS)
        break

      case "us_done":
        showToast("System : Your score was successfully registered!", Types.SUCCESS)
        break
    }
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
      const bodyFormData = new FormData()
      bodyFormData.append("id", id)
      bodyFormData.append("action", action)

      Swal.fire({
        title: 'Are you sure?',
        text: action === "accept"? "Do you want to accept this user ?": "Do you want to reject this user ?",
        icon: 'warning',
        showCancelButton: true,
        reverseButtons: true,
        confirmButtonColor: '#68B64D',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes'
      })
          .then((result) =>
          {
            if (result.isConfirmed)
            {
              Swal.fire({
                padding: "60px",
                width: 153,
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen() {
                  Swal.showLoading()
                }
              })

              axios.post(
                  "/api/rest/employee/actions/",
                  bodyFormData,
                  {
                    headers: {
                      "_csrf" : getToken() as any,
                      "Authorization": this.getAuth
                    }
                  }
              )
                  .then(() =>
                  {
                    location.reload()
                    Swal.close()
                  })
                  .catch(() =>
                  {
                    Swal.fire({
                      icon: 'error',
                      title: 'Oops...',
                      text: 'Something went wrong!',
                    })
                  })
            }
          })
    },

    // Update Access
    updateAccess()
    {
      Swal.fire({
        padding: "60px",
        width: 153,
        allowOutsideClick: false,
        allowEscapeKey: false,
        didOpen()
        {
          Swal.showLoading()
        }
      })

      const bodyFormData = new FormData()
      bodyFormData.append("id", this.employee[this.activeAccess].email)

      const params: any[] = Object.keys(this.access)

      const newAccess: any[] = []

      for (let i = 0; i < params.length; i++)
      {
        if (!this.access[params[i]])
        {
          newAccess.push(params[i])
        }
      }

      bodyFormData.append("access", JSON.stringify(newAccess))

      axios.post(
          "/api/rest/employee/access",
          bodyFormData,
          {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": this.getAuth
            }
          }
      )
          .then(() =>
          {
            this.getEmployees()
            Swal.close()
          })
          .catch(() =>
          {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Something went wrong!',
            })
          })
    },

    // Get Employees
    getEmployees()
    {
      this.employee = []
      this.new_employee = []

      axios
          .get(`/api/rest/employee/all/${ this.userInfo["email"] }`, {
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

    // Get Complete Employee
    completeEmployee(id)
    {
      this.$store.commit("setProfileModal", id)
    },

    // Remove Employee
    removeEmployee(id)
    {
      const bodyFormData = new FormData()
      bodyFormData.append("id", id)

      Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        reverseButtons: true,
        confirmButtonColor: '#68B64D',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes'
      })
          .then((result) =>
          {
            if (result.isConfirmed)
            {
              Swal.fire({
                padding: "60px",
                width: 153,
                allowOutsideClick: false,
                allowEscapeKey: false,
                didOpen()
                {
                  Swal.showLoading()
                }
              })
              axios.post(
                  "/api/rest/employee/remove",
                  bodyFormData,
                  {
                    headers: {
                      "_csrf" : getToken() as any,
                      "Authorization": this.getAuth
                    }
                  }
              )
                  .then(() =>
                  {
                    this.getEmployees()
                    Swal.close()
                  })
                  .catch(() =>
                  {
                    Swal.fire({
                      icon: 'error',
                      title: 'Oops...',
                      text: 'Something went wrong!',
                    })
                  })
            }
          })
    },

    // Set Access
    setAccess(index)
    {
      this.activeAccess = index
      const user = this.employee[index]
      for (let i = 0; i < user["access"].length; i++)
      {
        this.access[user["access"][i]] = false
      }
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
      "getAuth"
    ]),
  }
})

export default class Employee extends Vue {}
</script>

<style scoped src="../../assets/sass/page/employee.sass" lang="sass"></style>