<template lang="pug">

.weekiTabBarTab.tab-pane.fade.show(
  :id="'tab_pills-' + btn.toLowerCase()"
  :class="{ 'active' : active === true }"
  role="tabpanel"
  :aria-labelledby="'tab_pills-' + btn.toLowerCase() + '-tab'"
)
  slot

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Props
  props: ["btn", "active"],
})

export default class WeekiTabBarTab extends Vue {}
</script>