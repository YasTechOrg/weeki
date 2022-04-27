<template lang="pug">

#single.gm

  .loading.d-flex.justify-content-center.align-items-center( v-if="Object.keys(product).length === 0" )
    img( src="../../assets/animations/main_loader.svg" alt="Loading..." )

  .product( v-else )

    .part1.row.m-0

      .col-lg-7.pr-12
        div
          p.title.fw-bold.mb-24 {{ product["family"] }}, {{ product["city"] }}, {{ product["code"] }}, {{ product["grade"] }}

          .section1.row.m-0

            .col-7.p-0
              carousel( v-if="product['images'].length !== 0" :settings="slider_settings" )

                Slide( v-for="item in product['images']" :key="item" )

                  img.carousel__item( :src="require('@braintree/sanitize-url').sanitizeUrl(`/api/rest/cdn/product/images/${item}`)" alt="img" )

                template( #addons )
                  Pagination
                  Navigation

              img( src="../../assets/img/images/no_img.png" :alt="product['family']" v-else )

            .col-5.flex.flex-column

              .detail.flex-grow-1.flex.flex-column.justify-content-between

                div
                  | test

                div
                  | test

              .items.flex-grow-0.mt-16.text-end
                img.pl-16( src="../../assets/img/icons/icon_bookmark_gray.svg" alt="bookmark" )
                img( src="../../assets/img/icons/icon_share_gray.svg" alt="share" )

      .col-lg-5.pl-12
        div
          p.title.mb-24.fw-bold Seller Details

</template>

<script lang="ts">
/* eslint-disable  @typescript-eslint/no-empty-function */
/* eslint-disable  @typescript-eslint/no-this-alias */

import { Options, Vue } from 'vue-class-component'
import axios from "axios"
import { getToken } from "@/csrfManager"
import { mapGetters } from "vuex"
import { Carousel, Slide, Navigation, Pagination } from "vue3-carousel"

@Options({

  // Page Components
  components: {
    Pagination,
    Carousel,
    Slide,
    Navigation,
  },

  // Page Variables
  data()
  {
    return {
      product: {},
      slider_settings: {
        itemsToShow: 1,
        snapAlign: 'center',
        wrapAround: true
      },
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
    ])
  },

  // Page Variable Watchers
  watch: {

  }
})
export default class Single extends Vue {}
</script>

<style scoped src="../../assets/sass/page/single.sass" lang="sass"></style>
<style lang="sass">
#single > .product > .part1 > div > div:first-child > .section1 > div:first-child

  .carousel
    background-color: transparent!important

  .carousel__item
    background-color: whitesmoke

  .carousel__item, > img
    aspect-ratio: 16 / 9
    border-radius: 4px
    width: 100%
    -o-object-fit: cover
    object-fit: cover

  .carousel__pagination-button
    padding-right: 0
    padding-left: 0
    width: 7px
    height: 7px
    background-color: #E4E4E4

  .carousel__pagination-button--active
    width: 12px!important
    background-color: #68B64D

  .carousel__icon
    width: 50px
    height: 50px

  .carousel__next, .carousel__prev
    color: #6E6E6E!important
    height: 60px!important
    border-radius: 4px!important
    background-color: rgba(245, 245, 245, 0.6)!important
    transform: translate(0%,-50%)!important

</style>