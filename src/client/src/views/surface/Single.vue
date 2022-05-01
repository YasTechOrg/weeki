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

            .col-lg-7.p-0
              carousel( v-if="product['images'].length !== 0" :settings="slider_settings" )

                Slide( v-for="item in product['images']" :key="item" )

                  img.carousel__item( :src="require('@braintree/sanitize-url').sanitizeUrl(`/api/rest/cdn/product/images/${item}`)" alt="img" )

                template( #addons )
                  Pagination
                  Navigation

              img( src="../../assets/img/images/no_img.png" :alt="product['family']" v-else )

            .col-5.flex.flex-column

              .detail.flex-grow-1.d-flex.flex-column.justify-content-between.align-items-center

                div.text-center
                  p Price per Kg
                  p.mb-0.fw-bold €{{ product['ppk'] }}

                div.text-center
                  p Inventory Amount
                  p.mb-0.fw-bold €{{ product['amount'] }}(Kg)

              .items.flex-grow-0.mt-16.text-end
                img.mr-16.cursor-pointer( src="../../assets/img/icons/icon_bookmark_gray.svg" v-if="checkAuth" alt="bookmark" )
                img.cursor-pointer( src="../../assets/img/icons/icon_share_gray.svg" @click="showShare = true" alt="share" )

          p.title.fw-bold.mb-24.d-none.d-lg-block Product Details

          .section2.d-none.d-lg-block

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Type
              p.m-0 {{ product["type"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Family
              p.m-0 {{ product["family"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 City/Country
              p.m-0 {{ product["country"] }}, {{ product["city"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Code
              p.m-0 {{ product["code"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Family
              p.m-0 {{ product["grade"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Packing
              p.m-0 {{ product["packing"] }}

            .data.d-flex.flex-column.justify-content-between.align-items-start.border-0.pb-0
              p.m-0 Description
              p.m-0 {{ product["description"] }}

      .d-lg-none
        div

          .title.d-flex.align-items-center.justify-content-between
            p.fw-bold.m-0 Product Details
            img( :src="require('../../assets/img/icons/icon_' + (mm_open ? 'plus' : 'minus') + '.svg')" @click="mm_open = !mm_open" alt="plus" )

          .data_list.mt-24( :class="{ 'd-none' : !mm_open }" )

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Type
              p.m-0 {{ product["type"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Family
              p.m-0 {{ product["family"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 City/Country
              p.m-0 {{ product["country"] }}, {{ product["city"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Code
              p.m-0 {{ product["code"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Family
              p.m-0 {{ product["grade"] }}

            .data.d-flex.align-items-center.justify-content-between
              p.m-0 Packing
              p.m-0 {{ product["packing"] }}

            .data.d-flex.flex-column.justify-content-between.align-items-start.border-0.pb-0
              p.m-0 Description
              p.m-0 {{ product["description"] }}

      .col-lg-5.pl-12
        div
          p.title.mb-24.fw-bold Seller Details

          .sd.d-flex.flex-column.justify-content-start

            .sd_card.d-flex.align-items-center.justify-content-center

              .left

                .d-flex.justify-content-start.align-items-center


              .right
                | :

          p.title.mb-24.fw-bold.mt-24 Product Location

          .d-flex.align-items-start.justify-content-start

            img( src="../../assets/img/icons/icon_location_gray.svg" alt="location" )

            p.mb-0.ml-16 {{ product['location'] }}

share-modal( :show="showShare" @update:show="showShare = false" :link="purl" )
  template( v-for="(m, key) in modules" :key="key" )
    share-module( tag="a" :href="m.url" :name="m.name" :variables="shareModalStyle" target="_blank" )
      component( :is="m.component" )

</template>

<script lang="ts">
/* eslint-disable  @typescript-eslint/no-empty-function */
/* eslint-disable  @typescript-eslint/no-this-alias */

import { Options, Vue } from 'vue-class-component'
import axios from "axios"
import { getToken } from "@/csrfManager"
import { mapGetters } from "vuex"
import { Carousel, Slide, Navigation, Pagination } from "vue3-carousel"
import { sanitizeUrl } from "@braintree/sanitize-url"
import { ChatCircle, At, Share, Twitter, FacebookFill } from "@salmon-ui/icons"
import ShareModal from "vue-share-modal";
import ShareModule from "vue-share-modal/src/components/share-module.vue"

@Options({

  // Page Components
  components: {
    Pagination,
    Carousel,
    Slide,
    Navigation,
    At,
    Share,
    Twitter,
    ChatCircle,
    FacebookFill,
    ShareModal,
    ShareModule,
  },

  // Page Variables
  data()
  {
    return {
      product: {},
      showShare: false,
      slider_settings: {
        itemsToShow: 1,
        snapAlign: 'center',
        wrapAround: true
      },
      base: location.origin,
      shareModalStyle: {
        fontFamily: 'Inter, sans-serif',
        red: '#ee4d4d',
        white: '#fefefe',
        primary: '#ee6c4d',
        primaryLight: '#ee6c4d08',
        secondary: '#3d5a80',
        secondaryLight: '#3d5a8096',
      },
      mm_open: false
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
      "getAuth",
      "checkAuth"
    ]),

    // Get Product Url
    purl()
    {
      return this.base + '/single/' + this.product['id']
    },

    modules()
    {
      const text = `${this.product["family"]}, ${this.product["city"]}, ${this.product["code"]}, ${this.product["grade"]} | Weeki.de`
      return [
        { name: "Telegram", component: ChatCircle, url: sanitizeUrl(`https://t.me/share/url?url=${this.purl}&text=${text}`) },
        { name: "Twitter", component: Twitter, url: sanitizeUrl(`https://twitter.com/intent/tweet?text=${text}&url=${this.purl}`)  },
        { name: "E-mail", component: At, url: sanitizeUrl(`mailto:?subject=${text}&body=${this.purl}`)  },
        { name: "Facebook", component: FacebookFill, url: ""  },
      ]
    },
  },

  // Page Variable Watchers
  watch: {

  }
})
export default class Single extends Vue {}
</script>

<style scoped src="../../assets/sass/page/single.sass" lang="sass"></style>
<style lang="sass">
#single > .product > .part1 > div:first-child > div > .section1 > div:first-child

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

  .carousel__pagination
    padding: 0

    @media screen and (max-width: 991px)
      margin-bottom: 0!important

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