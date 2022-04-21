<template lang="pug">

router-link.mobileMenuItem.d-flex.mt-16( active-class="active" :to="link" )

  .icon.d-flex.justify-content-center.align-items-center.w3-animate-left
    img( :src="require('@/assets/img/icons/icon_' + icon + ($route.path !== link? '_gray.svg' : '_green.svg'))" :alt="page" )

  .text.flex-grow-1.pl-16.d-flex.justify-content-start.align-items-center.w3-animate-opacity
    p.mb-0 {{ page }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["page", "link", "icon"],
})
export default class MobileMenuItem extends Vue {}
</script>