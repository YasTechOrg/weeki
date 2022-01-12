<!--suppress ALL -->
<template lang="pug">

#sendOrders

  WeekiTabBar( :bottom="['Send New Order', 'Orders Sent']" )
    WeekiTabBarTab( btn="send_new_order" :active="true" )
      form
        .row.me-0.send_order
          p.mb-4.fw-bold.col-12 Enter The Company Name And Product Specifications To Send The Order

          .col-sm-6.col-md-4
            WeekiNormalDropDown( name="type" :list="['dobd', 'domdmo']" label="Type Of Product" )

          .col-sm-6.col-md-4
            WeekiNormalDropDown( name="family" :list="['dobd', 'domdmo']" label="Family Of Product" )

          .col-sm-6.col-md-4
            WeekiOptionalDropDown( name="country" label="Country" v-model:value="countrySelectValue" )
              option( v-for="country in countries" :key="country['nativeName']" :value="((country.name != null) ? country.name : country['nativeName'])" ) {{ country["nativeName"] }}

          .col-sm-3.col-md-4
            WeekiTextInput.mb-3( name="code" label="Code" auto-complete="false" req="true" )

          .col-sm-3.col-md-4
            WeekiTextInput.mb-3( name="grade" label="Grade" auto-complete="false" req="true" )

          .col-sm-6.col-md-4
            WeekiTextInput.mb-3( name="packing" label="Packing" auto-complete="false" req="true" )

          .col-sm-6.col-md-4
            WeekiTextInput.mb-3( name="amount" label="Amount(kg)" auto-complete="false" type="number" req="true" )

          .col-sm-6.col-md-4
            WeekiTextInput.mb-3( name="ppk" label="Price Per Kg(€)" auto-complete="false" type="number" req="true" )

          .col-sm-6.col-md-4
            WeekiOptionalDropDown( name="company" label="Company" )
              option( v-for="company in allCompanies" :key="company.name" :value="company.email" ) {{ company.name }}

          .col-12
            WeekiButton.float-end( text="Send Order" @click="submitForm" )

    WeekiTabBarTab( btn="orders_sent" )
      .orders-sent
        .d-flex
          WeekiSearchInput.me-3( placeholder="Search" v-model:value="productsSearch" )
          WeekiDateInput.me-3.flex-grow-1( name="begdate" placeholder="From" mb="false" )
          WeekiDateInput.flex-grow-1( name="enddate" placeholder="To" mb="false" )
          img.print-icon.ms-5.me-2(src="../../assets/img/icons/icon_printer.svg" alt="print" role="button" @click="printPage()")
          img.calendar-icon(src="../../assets/img/icons/icon_calendar.svg" alt="calendar" role="button")

        .orders-table.mt-4

          .orders-table-header.d-flex
            .w-24.p-2.d-flex.align-items-center.justify-content-center
              p.mb-0.text-center Product Details
            .w-8.p-2.d-flex.align-items-center.justify-content-center
              p.mb-0 Packaging
            .w-12.p-2.d-flex.align-items-center.justify-content-center
              p.mb-0 Amount(kg)
            .w-8.p-2.d-flex.align-items-center.justify-content-center
              p.mb-0.text-center Price Per Kg(€)
            .w-16.p-2.d-flex.align-items-center.justify-content-center
              p.mb-0.text-center Company Name
            .w-12.p-2.d-flex.align-items-center.justify-content-center
              p.mb-0.text-center Date & Time Product Sent
            .w-12.p-2.d-flex.align-items-center.justify-content-center
              p.mb-0 Status
            .w-8.p-2.d-flex.align-items-center.justify-content-center
              p.mb-0 Details



          .orders-table-body
            .orders-table-row.d-flex
              .w-24.p-2.d-flex.align-items-center.justify-content-start
                p.mb-0 Carrots, Anabella, Tehran, 300-400, A
              .w-8.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0 Box
              .w-12.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0 6000
              .w-8.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0 10
              .w-16.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0.text-ellipsis Berlin Company
              .w-12.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0.text-center 12/16/2020 #[br] 08:00
              .w-12.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0.pt-1.pb-1.pe-2.ps-2.canceled-order Canceled
              .w-8.p-2.d-flex.align-items-center.justify-content-center
                img(src="../../assets/img/icons/icon_eye_green.svg" alt="button" role="button" data-bs-target="#WeekiNormalModal_order_details"
                  data-bs-toggle="modal")

            .orders-table-row.d-flex
              .w-24.p-2.d-flex.align-items-center.justify-content-start
                p.mb-0 Carrots, Anabella, Tehran, 300-400, A
              .w-8.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0 Box
              .w-12.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0 6000
              .w-8.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0 10
              .w-16.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0.text-ellipsis Berlin Company
              .w-12.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0.text-center 12/16/2020 #[br] 08:00
              .w-12.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0.pt-1.pb-1.pe-2.ps-2.pending-order Pending
              .w-8.p-2.d-flex.align-items-center.justify-content-center
                img(src="../../assets/img/icons/icon_eye_green.svg" alt="button" role="button" data-bs-target="#WeekiNormalModal_order_details"
                  data-bs-toggle="modal")

            .orders-table-row.d-flex
              .w-24.p-2.d-flex.align-items-center.justify-content-start
                p.mb-0 Carrots, Anabella, Tehran, 300-400, A
              .w-8.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0 Box
              .w-12.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0 6000
              .w-8.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0 10
              .w-16.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0.text-ellipsis Berlin Company
              .w-12.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0.text-center 12/16/2020 #[br] 08:00
              .w-12.p-2.d-flex.align-items-center.justify-content-center
                p.mb-0.pt-1.pb-1.pe-2.ps-2.sent-order Sent
              .w-8.p-2.d-flex.align-items-center.justify-content-center
                img(src="../../assets/img/icons/icon_eye_green.svg" alt="button" role="button" data-bs-target="#WeekiNormalModal_order_details"
                  data-bs-toggle="modal")


WeekiNormalModal(
  name="order_details"
  title="Order Details"
  max-width="900px"
  max-height="73%"
  scrollable="true"
  mfs="true"
  height="unset"
)
  p Carrots, Anabella, Tehran, 300-400, A

  .orders-table

    .orders-table-header.d-flex
      .w-16.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0 Packaging
      .w-12.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0 Amount(kg)
      .w-8.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0.text-center Price Per Kg(€)
      .w-16.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0.text-center Company Name
      .w-16.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0.text-center Date & Time Product Sent
      .w-16.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0.text-center Date & Time Product Sent
      .w-16.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0 Status


    .orders-table-body
      .orders-table-row.d-flex
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0 Box
        .w-12.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0 6000
        .w-8.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0 10
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-ellipsis Berlin Company
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-center 12/16/2020 #[br] 08:00
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-center 12/16/2020 #[br] 08:00
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.pt-1.pb-1.pe-2.ps-2.canceled-order Canceled

  p.grey-text.mt-5.mb-2 Description
  p Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import WeekiSearchInput from "@/components/elements/WeekiSearchInput.vue"
import WeekiNormalDropDown from "@/components/elements/WeekiNormalDropDown.vue"
import WeekiOptionalDropDown from "@/components/elements/WeekiOptionalDropDown.vue"
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue"
import WeekiDateInput from "@/components/elements/WeekiDateInput.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"
import WeekiTabBar from "@/components/elements/WeekiTabBar.vue"
import WeekiTabBarTab from "@/components/elements/WeekiTabBarTab.vue"
import WeekiNormalModal from "@/components/elements/WeekiNormalModal.vue"

@Options({

  // Page Components
  components: {
    WeekiNormalDropDown,
    WeekiOptionalDropDown,
    WeekiTextInput,
    WeekiSearchInput,
    WeekiButton,
    WeekiTabBar,
    WeekiTabBarTab,
    WeekiDateInput,
    WeekiNormalModal
  },
  data(){
    return{
      allCompanies: [],
    }
  },
  methods : {
    printPage() {
      window.print()
    }
  }
})

export default class SendOrders extends Vue {}
</script>

<style scoped src="../../assets/sass/page/sendOrders.sass" lang="sass"></style>