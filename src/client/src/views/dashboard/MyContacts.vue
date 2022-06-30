<template>

<div id="myContacts">
  <WeekiSearchInput placeholder="Search" v-model:value="contactSearch"/>

  <div class="load d-flex align-items-center justify-content-center" v-if="dataLoaded === false">
    <img src="../../assets/animations/main_loader.svg" alt="Loading...">
  </div>

  <p class="nf mb-0" v-else-if="contacts.length === 0">No Contact Found!</p>

  <div class="list pt-12" v-else>
    <div class="pt-12 pb-12" v-for="item in filteredContacts" :key="item">
      <div class="d-flex align-items-center justify-content-center mb-12">
        {{ item[0] }}
      </div>

      <div class="d-flex contact justify-content-between align-items-center"
           v-for="(user) in item[1]"
           :key="user">
        <div class="d-flex justify-content-start align-items-center">
          <WeekiProfile :info="user"/>
          <div class="d-flex flex-column align-items-start justify-content-center pl-16">
            <p class="mb-0 fw-bolder">{{ getFullName(user) }}</p>
            <p class="mb-0">{{ user["company"] }}</p>
          </div>
        </div>

        <div class="d-none align-items-center justify-content-end">
          <img src="../../assets/img/icons/icon_delete_gray.svg"
               @click="removeFromContacts(user['email'])"
               alt="remove" class="cursor-pointer unselectable">

          <img src="../../assets/img/icons/icon_message_gray.svg"
               @click="$router.push(`/dashboard/messages?iu=${ user['email'] }`)"
               alt="message" class="cursor-pointer unselectable">

          <img src="../../assets/img/icons/icon_person_gray.svg"
               @click="completeEmployee(user['email'])"
               data-bs-target="#WeekiNormalModal_user_profile"
               data-bs-toggle="modal"
               alt="profile" class="cursor-pointer unselectable">
        </div>
      </div>
    </div>
  </div>

  <UserProfileModal
      max-width="588px"
      max-height="73%"
      :scrollable="true"
      :mfs="true"
      height="unset"/>
</div>
</template>

<script lang="ts" setup>
import axios from "axios"
import { getToken } from "@/csrfManager"
import {computed, defineAsyncComponent, onMounted, Ref, ref} from "vue"
import store from "@/store"

const WeekiSearchInput = defineAsyncComponent(() => import("@/components/elements/WeekiSearchInput.vue"))
const WeekiProfile = defineAsyncComponent(() => import("@/components/elements/WeekiProfile.vue"))
const UserProfileModal = defineAsyncComponent(() => import("@/components/modals/UserProfileModal.vue"))

const dataLoaded = ref(false)
let contacts: Ref<any[]> = ref([])
const contactSearch = ref("")

const getFullName = (user) =>
{
  if (user["role"] === "COMPANY") return user["name"]
  else return `${ user["firstname"] } ${ user["lastname"] }`
}

onMounted(() =>
{
  const http_conf = { "_csrf": getToken(), "Authorization": store.getters.getAuth }
  axios
      .get("/api/rest/account/contact/get", { headers: http_conf })

      .then(value => contacts.value = (value.data as any[]).sort(
              (a, b) => (getFullName(a) > getFullName(b)) ? 1 : ((getFullName(b) > getFullName(a)) ? -1 : 0)
          )
      )

      .catch(reason => console.log(reason))

      .finally(() => dataLoaded.value = true)
})

const removeFromContacts = (id: string) =>
{
  //
}

const completeEmployee = (id) =>
{
  //
}

const filteredContacts = computed(() =>
{
  const f_contacts: any[] = contacts.value.filter((el) =>
  {
    return (el["firstname"] + el["lastname"]).toLowerCase().indexOf(contactSearch.value.toLowerCase()) !== -1
  })

  let correct = ""
  let newArr: any[] = []

  for (let i = 0; i < f_contacts.length; i++)
  {
    const contact = f_contacts[i]

    if (getFullName(contact)[0] !== correct)
    {
      correct = getFullName(contact)[0]

      let sameUsers: any[] = []

      for (let j = 0; j < f_contacts.length; j++)
      {
        const contact2 = f_contacts[j]

        if (getFullName(contact2)[0] === correct) sameUsers.push(contact2)
      }

      newArr.push([correct, sameUsers])
    }
  }

  return newArr
})
</script>

<style scoped src="../../assets/sass/page/my_contacts.sass" lang="sass"></style>
