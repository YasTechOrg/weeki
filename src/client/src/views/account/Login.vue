<!--suppress ALL -->
<template lang="pug">

#login

  img.logo( src="../../assets/img/images/brand/logo.png" alt="Weeki" )

  .d-flex.position-relative

    img.me-auto.h-100.position-absolute( src="../../assets/img/icons/icon_arrow_left.svg" @click="goTo('/')" alt="back" )

    p.fw-bolder.m-auto.mb-0.w-100.col-sm-4 Login

  form( method="post" action="/api/controller/account/login" )

    input( :value="require('@/csrfManager').getToken()" name="_csrf" hidden )
    input( :value="(back === undefined || back === null || back === '') ? '/dashboard' : back" name="back" hidden )

    WeekiEmailInput( label="Email Address" name="email" )

    WeekiPasswordInput( label="Password" name="password" )

    WeekiButton( text="Login" type="submit" )

    p.m-0.mt-3.fg_btn.cursor-pointer( data-bs-toggle="modal" data-bs-target="#WeekiNormalModal_fpass" ) Forgot Password?

    p.m-0.mt-2.reg_btn not created account yet? #[router-link( to="/account/register" )  create a new account]

WeekiNormalModal( title="Forgot Your Password?" name="fpass" )

  p Enter Your Email Address And We Will Share A Link To Create A New Password.

  form( method="post" action="/api/controller/account/forgot" )

    WeekiEmailInput( label="Email Address" name="email" )

    WeekiButton.float-end( type="submit" text="Send" )

</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component'
import WeekiEmailInput from "@/components/elements/WeekiEmailInput.vue"
import WeekiPasswordInput from "@/components/elements/WeekiPasswordInput.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"
import WeekiNormalModal from "@/components/elements/WeekiNormalModal.vue"
import { showToast, Types } from '@/toastManager'

@Options({

  // Page Components
  components: {
    WeekiEmailInput,
    WeekiPasswordInput,
    WeekiButton,
    WeekiNormalModal
  },

  // Page Variables
  data()
  {
    return {
      res: this.$route.query.res,
      back: this.$route.query.back,
    }
  },

  // Page Methods
  methods: {

    // Go To Route
    goTo(route: string)
    {
      this.$router.push(route)
    }
  },

  // On Pae Load
  mounted()
  {

    // Check If Logout Response
    if (this.res === "logout")
    {
      this.$store.commit("removeAuth")
      location.href = "/account/login"
    }

    // User Not Exist
    if (this.res === "exi")
    {
      showToast("System : This user is not available!", Types.ERROR)
    }

    // User Is In Safe Mode
    if (this.res === "safe")
    {
      showToast("System : Sorry, this account has been secured! Please change your password to continue.", Types.ERROR)
    }

    // Username or Password Is Incorrect
    if (this.res === "err")
    {
      showToast("System : System : Username or Password is incorrect!", Types.ERROR)
    }

    // Dont Have Access
    if (this.res === "err_ac")
    {
      showToast("System : System : Sorry, your boss has not accessed you yet", Types.ERROR)
    }

    // User Is Registered Successfully
    if (this.res === "d_reg")
    {
      showToast("System : Your account is created successfully. Please login!", Types.SUCCESS)
    }

    // Employee Is Registered Successfully
    if (this.res === "d_reg_emp")
    {
      showToast("System : Your account is created successfully. Please wait for accept email from your boss!", Types.SUCCESS)
    }

    // Forgot Email Not Exists
    if(this.res === "f_n_exi")
    {
      showToast("System : Sorry, email is not exist!", Types.ERROR)
    }

    // Forgot Email Sent Successfully
    if(this.res === "f_send")
    {
      showToast("System : Forgot password email sent to you successfully!", Types.SUCCESS)
    }

    // Forgot No Token Found
    if(this.res === "f_t_not")
    {
      showToast("System : Sorry, token is not mentioned!", Types.ERROR)
    }

    // Forgot No Token Found
    if(this.res === "f_t_c")
    {
      showToast("System : Sorry, token is not correct!", Types.ERROR)
    }

    // Forgot Password Done
    if(this.res === "fg_done")
    {
      showToast("System : Your password changed successfully and now you can login in to your account!", Types.SUCCESS)
    }
  }
})

export default class Login extends Vue {}
</script>

<style scoped src="../../assets/sass/page/login.sass" lang="sass"></style>