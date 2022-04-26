<template lang="pug">

#single.gm

  .loading.d-flex.justify-content-center.align-items-center( v-if="Object.keys(product).length === 0" )
    img( src="../../assets/animations/main_loader.svg" alt="Loading..." )

  .product( v-else )

    .part1.row.m-0

      .col-lg-7.pr-12
        div
          p.title.mb-0.fw-bold {{ product["family"] }}, {{ product["city"] }}, {{ product["code"] }}, {{ product["grade"] }}

      .col-lg-5.pl-12
        div
          p.title.mb-0.fw-bold Seller Details

</template>

<script lang="ts">
/* eslint-disable  @typescript-eslint/no-empty-function */
/* eslint-disable  @typescript-eslint/no-this-alias */

import { Options, Vue } from 'vue-class-component'
import axios from "axios";
import {getToken} from "@/csrfManager"
import {mapGetters} from "vuex"

@Options({

  // Page Components
  components: {
  },

  // Page Variables
  data()
  {
    return {
      product: {}
    }
  },

  // On Page Load
  async mounted()
  {
    await axios
        .get(`/api/rest/product/get/single/${this.$route.params.id}`, {
          headers: {
            "_csrf" : getToken() as any
          }
        })
        .then(value =>
        {
          this.product = value.data
        })
  },

  // Page Methods
  methods: {

  },

  // Page Computed Variables
  computed: {
    ...mapGetters([
      "getAuth"
    ]),
  },

  // Page Variable Watchers
  watch: {

  }
})
export default class Single extends Vue {}
</script>

<style scoped src="../../assets/sass/page/single.sass" lang="sass"></style>