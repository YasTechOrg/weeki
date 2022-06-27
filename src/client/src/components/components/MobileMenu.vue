<script lang="ts" setup>
import {computed, defineAsyncComponent, defineProps, ref} from "vue"
import router from "@/router"
import store from "@/store"

const WeekiIconButton = defineAsyncComponent(() => import("@/components/elements/WeekiIconButton.vue"))
const MobileMenuItem = defineAsyncComponent(() => import("@/components/components/MobileMenuItem.vue"))

const layout = ref(router.currentRoute.value.meta["layout"])
const toggleMobileSidebar = () => document.querySelector("#mobile_menu")!.classList.toggle("d-none")
const checkAuth = ref(store.getters.checkAuth)
const userInfo = computed(() => store.state.userData)

const props = defineProps<{
  dm: [],
  title: []
}>()

const dashboard_menu = (index) =>
{
  const master: any[] = props.dm[index]

  let final: any[] = []

  if (index === 0)
  {
    final.push(master[0])

    if (userInfo.value['role'] === 'COMPANY') final.push(master[1])

    for (let i = 2; i < master.length; i++)
      if (!userInfo.value['access'].includes(master[i].id)) final.push(master[i])
  }
  else
    for (let i = 0; i < master.length; i++)
      if (!userInfo.value['access'].includes(master[i].id)) final.push(master[i])

  return final
}
</script>

<template>
  <div id="mobile_menu" class="d-none w3-animate-left h-100 w-100"
       v-if="layout === 'surface' || layout === 'error' || layout === 'single' || layout === 'dashboard'">

    <img @click="toggleMobileSidebar"
         src="../../assets/img/icons/icon_exit.svg"
         alt="exit" class="position-absolute cursor-pointer exit w3-animate-zoom">

    <div class="logo d-flex align-items-center justify-content-center">
      <img  src="../../assets/img/images/brand/logo.png" @click="$router.push('/')" alt="weeki" class="w3-animate-zoom pt-1 pb-1 cursor-pointer">
    </div>

    <WeekiIconButton
        class="mt-24 mr-16 ml-16 w3-animate-zoom"
        v-if="$route.path !== '/'"
        text="Advanced Search"
        icon="icons/icon_search_green.svg"/>

    <div class="surface mt-24">
      <MobileMenuItem page="home" link="/" icon="home"/>
      <MobileMenuItem page="about" link="/about" icon="info"/>
      <MobileMenuItem page="contact" link="/contact" icon="phone"/>
      <MobileMenuItem page="faq" link="/faq" icon="question"/>
    </div>

    <div class="dashboard" v-if="checkAuth">
      <div class="part mt-12" v-for="(item, index) in title" :key="item">
        <div class="part_title d-flex align-items-center justify-content-start">

        </div>
        <div class="menu_items" v-if="typeof userInfo['access'] !== 'undefined' && userInfo['access'] != null">
          <MobileMenuItem
              v-for="link in dashboard_menu(index)"
              :key="link" :page="link.name" :link="link.path"
              :icon="link.icon" item="dash"/>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="sass">

</style>