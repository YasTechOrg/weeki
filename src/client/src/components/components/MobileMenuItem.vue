<template lang="pug">

router-link.mobileMenuItem.d-flex.mt-16( active-class="active" :to="link" )

  .icon.d-flex.justify-content-center.align-items-center.w3-animate-left
    img( :src="require('@/assets/img/icons/icon_' + icon + ($route.path !== link? '_gray.svg' : '_green.svg'))" :alt="page" v-if="item !== 'dash'" )
    span.material-icons( v-else ) {{ icon }}

  .text.flex-grow-1.pl-16.d-flex.justify-content-start.align-items-center.w3-animate-opacity
    p.mb-0 {{ page }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["page", "link", "icon", "item"],
})
export default class MobileMenuItem extends Vue {}
</script>

<style scoped lang="sass">
.mobileMenuItem
  color: #6E6E6E!important
  text-decoration: none!important

  > .icon
    width: 65px!important
    height: 48px!important
    border-radius: 0 4px 4px 0!important

    > img, > span
      width: 24px!important

  > .text

    > p
      font-size: 16px!important

.mobileMenuItem.active
  color: #68B64D!important

  > .icon
    background-color: #D1E9C9!important

    > span
      color: #75BC5C
</style>