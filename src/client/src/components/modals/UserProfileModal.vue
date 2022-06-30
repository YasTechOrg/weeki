<script setup lang="ts">

import {defineAsyncComponent, defineProps} from "vue"

const Modal = defineAsyncComponent(() => import("../components/Modal.vue"))

defineProps<{
  maxWidth: string,
  maxHeight: string,
  scrollable: boolean,
  height: string,
  mfs: boolean
}>()
</script>

<template>
  <Modal name="user_profile" title="Profile"
         :max-width="maxWidth"
         :max-height="maxHeight"
         :scrollable="scrollable"
         :mfs="mfs"
         :height="height">
    mogfnolihdofhroi
  </Modal>
</template>

<style scoped lang="sass">

</style>
