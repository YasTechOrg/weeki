<!--suppress ALL -->
<template lang="pug">

#about.gm

  .mt-80.row.m-0

    .col-md-6.text.p-0.d-flex.justify-content-center.align-items-center.orderLast

      .position-relative

        h1.fw-bolder.m-0 About Us

        p.m-0.mt-24
          | With Weeki you can find all the fruits and vegetables you need to buy and sell with the fastest and shortest time.

    .col-md-6.img.p-0.d-flex.justify-content-center.align-items-center.orderFirst

      img.w-100.h-auto( src="../assets/img/images/about_01.svg" alt="a_01" )



  .mt-80.row.m-0

    .col-md-6.img.p-0.d-flex.justify-content-center.align-items-center.orderFirst

      img.w-100.h-auto( src="../assets/img/images/about_02.svg" alt="a_02" )

    .col-md-6.text.p-0.d-flex.justify-content-center.align-items-center.orderLast

      .position-relative

        h1.fw-bolder.m-0 Benefits Of Registration

        p.m-0.mt-24
          | After finding the desired product, you can send a private message to the seller or buyer of that product and complete your transaction.


  .mt-80.row.m-0

    .col-md-6.text.p-0.d-flex.justify-content-center.align-items-center.orderLast

      .position-relative

        h1.fw-bolder.m-0 About Us

        p.m-0.mt-24
          | If there is any discrepancy or damage in the product, you can register a product return request in the system.

    .col-md-6.img.p-0.d-flex.justify-content-center.align-items-center.orderFirst

      img.w-100.h-auto( src="../assets/img/images/about_03.svg" alt="a_03" )

  .mt-80.mb-80

    p.text-center.fw-bolder Companies that have joined Weeki

    carousel( :autoplay="1500" snap-align="center" :breakpoints="breakpoints" :wrap-around="true" )

      Slide.faq_carousel( v-for="item in 20" :key="item" )

        img.w-100( src="../assets/img/images/brand/logo.png" alt="Weeki" )

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { Carousel, Slide } from "vue3-carousel"

@Options({

  // Page Components
  components: {
    Carousel,
    Slide
  },

  // Page Variables
  data()
  {
    return {
      breakpoints: {

        // Mobile
        0: {
          itemsToShow: 3,
        },

        // Tablet
        770: {
          itemsToShow: 8,
        },
      },
    }
  }
})
export default class About extends Vue {}
</script>

<style scoped src="../assets/sass/page/about.sass" lang="sass"></style>