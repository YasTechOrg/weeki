<template lang="pug">

#password

  form( method="post" action="/api/controller/account/change" )

    input( :value="require('@/csrfManager').getToken()" name="_csrf" hidden )
    input( :value="userInfo['email']" name="email" hidden )

    WeekiPasswordInput.mb-20( label="Current Password" name="cp" v-model:value="cp" )

    WeekiPasswordInput.mb-20( label="New Password" name="np" v-model:value="pass" )
    WeekiPasswordInput.mb-20( label="Confirm New Password" v-model:value="cPass" )

    WeekiButton( text="Change Password" type="submit" disabled )

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue"
import WeekiPasswordInput from "@/components/elements/WeekiPasswordInput.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"
import {showToast, Types} from "@/toastManager";

@Options({

  // Page Variables
  data()
  {
    return {
      cp: "",
      pass: "",
      cPass: ""
    }
  },

  // On Page Load
  mounted()
  {
    switch (this.$route.query.res)
    {
      case "np_done":
        showToast("System : Password changed successfully!", Types.SUCCESS)
        break

      case "cp_wrr":
        showToast("System : Current is incorrect!", Types.ERROR)
        break
    }
  },

  // Page Components
  components: {
    WeekiTextInput,
    WeekiPasswordInput,
    WeekiButton
  },

  // Page Watchers
  watch:{

    cPass()
    {
      (document.querySelector("#password > form > button")! as HTMLButtonElement).disabled = this.cPass !== this.pass || this.cp === this.pass
    },

    pass()
    {
      (document.querySelector("#password > form > button")! as HTMLButtonElement).disabled = this.cPass !== this.pass || this.cp === this.pass
    },
  },

  // Page Computed Variables
  computed: {

    // Get User Data
    userInfo()
    {
      return this.$store.state.userData
    },
  }
})

export default class Password extends Vue {}
</script>

<style scoped src="../../assets/sass/page/password.sass" lang="sass"></style>