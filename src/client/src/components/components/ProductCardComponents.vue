<template lang="pug">

.productCardComponents

  .position-relative.p_image

    img( :src="getImage" alt="product image" )

    .bs_badge.position-absolute

      p.m-0( v-if="product['bs'] === 'buyer'" ) For Buy
      p.m-0( v-else ) For Sale

  .content.pl-12.pr-12.mt-24.pb-16

    p.m-0.w-100.fw-bolder
      | {{ product["family"] }}, {{ product["city"] }}, {{ product["code"] }}, {{ product["grade"] }}

    .dl.d-flex.flex-column.mt-24

      .d-flex.justify-content-start.align-items-center

        img( src="../../assets/img/icons/icon_money_gray.svg" alt="ppk" )

        p.title.mb-0.fw-bolder €{{ product["ppk"] }}

        p.currency.mb-0 Per(kg)

      .d-flex.justify-content-start.align-items-center.mt-16

        img( src="../../assets/img/icons/icon_products_gray.svg" alt="amount" )

        p.title.mb-0.fw-bolder €{{ product["amount"] }}

      .d-flex.justify-content-start.align-items-center.mt-16

        img( src="../../assets/img/icons/icon_company_gray.svg" alt="company" )

        p.title.mb-0.fw-bolder( v-if="product['company'] !== null" ) {{ product['company'] }}
        p.title.mb-0.fw-bolder( v-else ) -

      .d-flex.justify-content-start.align-items-center.mt-16

        img( src="../../assets/img/icons/icon_person_gray.svg" alt="seller" )

        p.title.mb-0.fw-bolder( v-if="product['seller'] !== null" ) {{ product['seller'] }}
        p.title.mb-0.fw-bolder( v-else ) -

      .d-flex.justify-content-start.align-items-center.mt-16

        img( src="../../assets/img/icons/icon_location_gray.svg" alt="location" )

        p.title.mb-0.fw-bolder {{ product["location"] }}

    .navigation.d-flex.align-items-center.justify-content-between.mt-24

      .d-flex.align-items-center.justify-content-start

        img.cursor-pointer( src="../../assets/img/icons/icon_share_gray.svg" alt="share" )

        img.cursor-pointer.ml-16(
          src="../../assets/img/icons/icon_message_gray.svg"
          v-if="checkAuth"
          alt="message"
        )

        img.cursor-pointer.ml-16(
          src="../../assets/img/icons/icon_bookmark_gray.svg"
          v-if="checkAuth"
          alt="bookmark"
        )

      router-link.d-flex.align-items-center.justify-content-end.text-decoration-none(
        :to="'/single/' + product['id']"
      )

        p.mb-0 View Details

        img( src="../../assets/img/icons/icon_arrow_right_green.svg" alt="gtp" )

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { mapGetters } from "vuex"

@Options({

  // Widget Props
  props: ["product"],

  // Widget Components
  computed: {

    ...mapGetters([
      "checkAuth"
    ]),

    // Get Product Image
    getImage()
    {
      const img = this.product["images"]
      return img ? `/api/rest/cdn/product/images/${img}` : require("../../assets/img/images/no_img.png")
    }
  },

  // Widget Variables
  data()
  {
    return {

    }
  }
})

export default class ProductCardComponents extends Vue {}
</script>

<style src="../../assets/sass/components/productCardComponents.sass" scoped lang="sass"></style>