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
                  data-bs-target="#WeekiNormalModal_employee_profile"
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
  name="employee_profile"
  title="Profile"
  max-width="588px"
  max-height="73%"
  scrollable="true"
  mfs="true"
  height="unset"
)

  .nf.d-flex.justify-content-center.align-items-center.pt-12.pb-12( v-if="Object.keys(profileModalUser).length === 0" )

    img( src="../../assets/animations/main_loader.svg" alt="Loading..." )

  .content( v-else )

    .d-flex.justify-content-between.align-items-center

      .d-flex.justify-content-start.align-items-center

        WeekiProfile.profile( :info="profileModalUser" )

        .d-flex.flex-column.justify-content-center.align-items-start.pl-16

          p.fw-bolder {{ profileModalUser['firstname'] }} {{ profileModalUser['lastname'] }}

          .d-flex( v-if="Object.keys(profileModalUser['rate']).length === 0" )

            img( src="../../assets/img/icons/icon_empty_star.svg" v-for="i in 5" :key="i" :class="{ 'mr-8' : i !== getStars }"  alt="s" )

          .d-flex( v-else-if="getStars === 5" )

            img( src="../../assets/img/icons/icon_star.svg" v-for="i in 5" :key="i" :class="{ 'mr-8' : i !== getStars }"  alt="s" )

          .d-flex( v-else )

            img( src="../../assets/img/icons/icon_star.svg" v-for="i in getStars" :key="i" :class="{ 'mr-8' : i !== 5 }"  alt="s" )
            img( src="../../assets/img/icons/icon_empty_star.svg" v-for="i in 5 - getStars" :key="i" :class="{ 'mr-8' : i !== 5 }"  alt="s" )

      .d-flex.justify-content-end.align-items-center

        .d-flex.flex-column.justify-content-center.align-items-end

          img.cursor-pointer(
            src="../../assets/img/icons/icon_addUser_gray.svg"
            v-if="!userInfo['contacts'].includes(profileModalUser['email'])"
            @click="addToContacts"
            alt="Add To Contacts"
          )

          img.cursor-pointer(
            src="../../assets/img/icons/icon_removeUser_gray.svg"
            @click="removeFromContacts"
            alt="Remove From Contacts"
            v-else
          )

          p.mb-0.mt-12.cursor-pointer(
            data-bs-target="#WeekiNormalModal_employee_rate"
            data-bs-toggle="modal"
          )

    .pl-40.mt-20

      .d-flex.justify-content-start.align-items-center.pt-16.pb-16
        img( src="../../assets/img/icons/icon_phone.svg" alt="phone number" )
        p.mb-0.ml-16 {{ profileModalUser["phoneNumber"] }}

      .d-flex.justify-content-start.align-items-center.pt-16.pb-16
        img( src="../../assets/img/icons/icon_mail.svg" alt="email" )
        p.mb-0.ml-16 {{ profileModalUser["email"] }}

      .d-flex.justify-content-start.align-items-center.pt-16.pb-16
        img( src="../../assets/img/icons/icon_company.svg" alt="company" )
        p.mb-0.ml-16 {{ profileModalCompany["companyName"] }}

      .d-flex.justify-content-start.align-items-center.pt-16.pb-16
        img( src="../../assets/img/icons/icon_location.svg" alt="location" )
        p.mb-0.ml-16 {{ profileModalUser["address"] }}

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

WeekiNormalModal(
  name="employee_rate"
  title="Rate"
  max-width="380px"
  max-height="73%"
  scrollable="true"
  mfs="true"
  height="unset"
)

  p.mb-24 what's your rate?

  .he.d-flex.justify-content-start.align-items-center

    WeekiProfile.employee( :info="profileModalUser" )

    p.ml-16.mb-0.fw-bolder {{ profileModalUser['firstname'] }} {{ profileModalUser['lastname'] }}

  .st.d-flex.justify-content-center.justify-content-center.mb-24

    img.cursor-pointer.unselectable(
      src="../../assets/img/icons/icon_star_big.svg"
      v-for="(i, index) in getSelfStars"
      @click="profileModalStars = index + 1"
      :key="i" alt="s"
    )

    img.cursor-pointer.unselectable(
      src="../../assets/img/icons/icon_empty_star_big.svg"
      v-for="(i, index) in 5 - getSelfStars"
      @click="profileModalStars = getSelfStars + (index + 1)"
      :key="i" alt="s"
    )

  WeekiButton.float-end( text="Confirm" @click="submitUserStars" )

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
      this.profileModalUser = {}
      this.profileModalCompany = {}
      this.profileModalStars = null

      axios
          .get(`/api/rest/employee/get/${ id }`, {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": this.getAuth
            }
          })

          .then(value =>
          {
            axios
                .get("/api/rest/account/sd/" + value.data["company"], {
                  headers: {
                    "_csrf" : getToken() as any,
                  }
                })
                .then(value2 =>
                {
                  this.profileModalCompany = value2.data
                  this.profileModalUser = value.data
                })
                .catch(reason2 => console.log(reason2))
          })

          .catch(reason => console.log(reason))
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

    // Add To Contacts
    addToContacts()
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
      const id = this.profileModalUser["email"]
      const bodyFormData = new FormData()
      bodyFormData.append("id", id)

      axios.post(
          "/api/rest/account/contact/add",
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
            location.href = "/dashboard/employee?res=ac_done"
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

    // Remove From Contacts
    removeFromContacts()
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
      const id = this.profileModalUser["email"]
      const bodyFormData = new FormData()
      bodyFormData.append("id", id)

      axios.post(
          "/api/rest/account/contact/remove",
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
            location.href = "/dashboard/employee?res=rc_done"
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

    // Update User Stars
    submitUserStars()
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
      const id = this.profileModalUser["email"]
      const bodyFormData = new FormData()
      bodyFormData.append("id", id)
      bodyFormData.append("rate", this.profileModalStars)

      axios.post(
          "/api/rest/account/stars/update",
          bodyFormData,
          {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": this.getAuth
            }
          }
      )
          .then(() => location.href = "/dashboard/employee?res=us_done")
          .catch(() =>
          {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Something went wrong!',
            })
          })
    }
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

    // Get Stars
    getStars()
    {
      if (this.profileModalUser["rate"] !== null && typeof this.profileModalUser["rate"] !== "undefined")
      {
        const size: any[] = Object.values(this.profileModalUser["rate"])
        let num = 0
        for (let i = 0; i < size.length; i++)
        {
          num = num + size[i]
        }
        return Math.round(num / size.length)
      }
      else
      {
        return 0
      }
    },

    // Get Stars
    getSelfStars()
    {
      if (this.profileModalUser["rate"] !== null && typeof this.profileModalUser["rate"] !== "undefined")
      {
        if (this.profileModalStars == null)
        {
          const users = Object.keys(this.profileModalUser["rate"])

          if (users.includes(this.userInfo["email"]))
          {
            return this.profileModalUser["rate"][this.userInfo["email"]]
          }
          else
          {
            return 0
          }
        }
        else
        {
          return this.profileModalStars
        }
      }
      else
      {
        return 0
      }
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