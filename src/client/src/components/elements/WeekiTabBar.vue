<template lang="pug">

.weekiTabBar

  ul.nav.nav-pills.row.mb-24( role="tablist" )

    li.nav-item.col( role="presentation" v-for="(btn, index) in bottom" :key="btn" )

      button.nav-link(
        :class="{ 'active' : index === 0 }"
        :id="'tab_pills-' + btn + '-tab'"
        data-bs-toggle="pill"
        :data-bs-target="'#tab_pills-' + getCorrectName(btn)"
        type="button"
        role="tab"
        :aria-controls="'tab_pills-' + getCorrectName(btn)"
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

  // Element Methods
  methods: {

    // Get Correct Name
    getCorrectName(name): string
    {
      return name.toLowerCase().replace(/\s/g, '_')
    },
  }
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