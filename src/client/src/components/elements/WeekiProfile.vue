<!--suppress ALL -->
<template lang="pug">

.weekiProfile

  img( :src="avatar" v-if="img" :alt="name" )

  div.d-flex.justify-content-center.align-items-center( v-else ) {{ name[0] }}
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Element Variables
  data()
  {
    return {
      avatar: `${location.origin}/api/rest/account/avatar`,
      img: false
    }
  },

  // Element Name
  name: "WeekiProfile",

  // Element Props
  props: ["info"],

  // Element Compoted Variables
  computed: {

    // User Name
    name()
    {
      if (this.info === null || Object(this.info).length === 0)
      {
        return " "
      }
      else
      {
        if(this.info["name"] != null)
        {
          return this.info["name"]
        }
        else
        {
          return this.info["firstname"]
        }
      }
    }
  }
})
export default class WeekiProfile extends Vue {}
</script>

<style scoped lang="sass">
.weekiProfile > img, .weekiProfile > div
  width: 42px
  height: 42px

.weekiProfile > img, .weekiProfile.employee > div
  width: 48px!important
  height: 48px!important

.weekiProfile > img, .weekiProfile.profile > div
  width: 56px!important
  height: 56px!important

.weekiProfile > img, .weekiProfile.chat > div
  width: 32px!important
  height: 32px!important

.weekiProfile > div
  background: #D1E9C9
  border-radius: 360px
  color: #68B64D
  font-weight: bold
  font-size: 14px
  line-height: 14px

</style>