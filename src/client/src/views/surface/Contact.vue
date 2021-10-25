<!--suppress ALL -->
<template lang="pug">

#contact.row.mt-40

  .col-md-6.p-0.orderLast

    h1.m-0.text-start.fw-bolder Contact To Weeki

    .d-flex.justify-content-start

      .icons.d-flex.flex-column

        img( src="../../assets/img/icons/icon_phone2_gray.svg" alt="phone" )

        img( src="../../assets/img/icons/icon_mail_gray.svg" alt="mail" )

        img( src="../../assets/img/icons/icon_fax_gray.svg" alt="fax" )

        img( src="../../assets/img/icons/icon_post_gray.svg" alt="zip code" )

        img( src="../../assets/img/icons/icon_location_gray.svg" alt="location" )

      .titles.d-flex.flex-column

        p.mb-0 phone number:

        p.mb-0 email address:

        p.mb-0 fax number:

        p.mb-0 zip code:

        p.mb-0 address:

      .content.d-flex.flex-column

        p.mb-0.flex-grow-1 6597-56

        p.mb-0.flex-grow-1 info@weeki.de

        p.mb-0.flex-grow-1 6597-56

        p.mb-0.flex-grow-1 65975461356

        p.mb-0.flex-grow-1 123 Main Street, New York, NY 10030 123 main street, new york, ny 10030

  .col-md-6.p-0.d-flex.align-items-center.justify-content-end.orderFirst

    img( src="../../assets/img/images/contact_01.svg" alt="contact" )
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({
  components: {
  },
})
export default class Contact extends Vue {}
</script>

<style scoped src="../../assets/sass/page/contact.sass" lang="sass"></style>