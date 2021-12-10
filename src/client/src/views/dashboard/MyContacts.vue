<template lang="pug">

#myContacts

  WeekiSearchInput( placeholder="Search" v-model:value="contactSearch" )

  .load.d-flex.align-items-center.justify-content-center( v-if="contacts == null" )

    img( src="../../assets/animations/main_loader.svg" alt="Loading..." )

  p.nf.mb-0( v-else-if="contacts.length === 0" ) No Contact Found!

  .list.pt-12( v-else )

    .pt-12.pb-12( v-for="item in filteredContacts" :key="item" )

      .d-flex.align-items-center.justify-content-center.mb-12

        | {{ item[0] }}

      .d-flex.contact.justify-content-between.aligalign-items-center(
        v-for="(user) in item[1]"
        :key="user"
      )

        .d-flex.justify-content-start.align-items-center

          WeekiProfile.employee( :info="user" )

          .d-flex.flex-column.align-items-start.justify-content-center.pl-16

            p.mb-0.fw-bolder {{ getFullName(user) }}

            p.mb-0 {{ user["company"] }}

        .d-none.align-items-center.justify-content-end

          img.cursor-pointer.unselectable(
            src="../../assets/img/icons/icon_delete_gray.svg"
            @click="removeFromContacts(user['email'])"
            alt="remove"
          )

          img.cursor-pointer.unselectable(
            src="../../assets/img/icons/icon_message_gray.svg"
            @click="$router.push(`/dashboard/messages?iu=${ user['email'] }`)"
            alt="message"
          )

          img.cursor-pointer.unselectable(
            src="../../assets/img/icons/icon_person_gray.svg"
            @click="completeEmployee(user['email'])"
            data-bs-target="#WeekiNormalModal_user_profile"
            data-bs-toggle="modal"
            alt="profile"
          )

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import axios from "axios"
import WeekiSearchInput from "@/components/elements/WeekiSearchInput.vue"
import WeekiProfile from "@/components/elements/WeekiProfile.vue"
import { getToken } from "@/csrfManager"
import { mapGetters } from "vuex"

/* eslint-disable  @typescript-eslint/no-explicit-any */
@Options({

  // Page Variables
  data()
  {
    return {
      contacts: null,
      contactSearch: ""
    }
  },

  // Page Components
  components: {
    WeekiSearchInput,
    WeekiProfile
  },

  // On Load Page
  mounted()
  {
    axios
        .get("/api/rest/account/contact/get", {
          headers: {
            "_csrf" : getToken() as any,
            "Authorization": this.getAuth
          }
        })

        .then(value => this.contacts = (value.data as any[])
            .sort((a, b) => (this.getFullName(a) > this.getFullName(b)) ? 1 : ((this.getFullName(b) > this.getFullName(a)) ? -1 : 0))
        )

        .catch(reason => console.log(reason))
  },

  // Page Methods
  methods: {

    // Get Full Name
    getFullName(user): string
    {
      if (user["role"] === "COMPANY")
      {
        return user["name"]
      }
      else
      {
        return `${ user["firstname"] } ${ user["lastname"] }`
      }
    },

    // Remove From Contacts
    removeFromContacts(id: string)
    {
      this.$store.commit("removeFromContacts", { id : id, url : "contacts", promise : false })
    },

    // Get Complete Employee
    completeEmployee(id)
    {
      this.$store.commit("setProfileModal", id)
    },
  },

  // Page Computed Variables
  computed: {

    // Get Filtered New Employees
    filteredContacts()
    {
      const contacts: any[] = this.contacts.filter((el) =>
      {
        return (el["firstname"] + el["lastname"]).toLowerCase().indexOf(this.contactSearch.toLowerCase()) !== -1
      })

      let correct = ""
      let newArr: any[] = []

      for (let i = 0; i < contacts.length; i++)
      {
        const contact = contacts[i]

        if (this.getFullName(contact)[0] !== correct)
        {
          correct = this.getFullName(contact)[0]

          let sameUsers: any[] = []

          for (let j = 0; j < contacts.length; j++)
          {
            const contact2 = contacts[j]

            if (this.getFullName(contact2)[0] === correct)
            {
              sameUsers.push(contact2)
            }
          }

          newArr.push([correct, sameUsers])
        }
      }

      return newArr
    },

    // Get Store Getters
    ...mapGetters([
      "getAuth"
    ]),
  }
})

export default class MyContacts extends Vue {}
</script>

<style scoped src="../../assets/sass/page/my_contacts.sass" lang="sass"></style>
