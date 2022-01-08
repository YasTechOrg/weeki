<template lang="pug">

.weekiTabBar

  ul.nav.nav-pills.mb-5.row( role="tablist" )

    li.nav-item.col-4( role="presentation" v-for="(btn, index) in bottom" :key="btn" )

      button.nav-link(
        :class="{ 'active' : index === 0 }"
        :id="'tab_pills-' + btn + '-tab'"
        data-bs-toggle="pill"
        :data-bs-target="'#tab_pills-' + btn.toLowerCase()"
        type="button"
        role="tab"
        :aria-controls="'tab_pills-' + btn.toLowerCase()"
        aria-selected="true"
      ) {{ btn }}

  .tab-content

    slot

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["bottom"],
})

export default class WeekiTabBar extends Vue {}
</script>

<style scoped lang="sass">
.weekiTabBar

  > ul > .nav-item

    > .nav-link
      background-color: #E3E3E3!important
      width: 100% !important
      padding: 8px 8px
      color: #6e6e6e

    > .active
      background-color: #68B64D!important
      color: white!important
</style>