<!--suppress ALL -->
<template lang="pug">

#receivedOrders

  .selectedCompanyNotExist( :class="{ 'd-none' : activeCompanyExist }" )

    .d-flex.search-date-bar
      WeekiOptionalDropDown.me-3( label="Company" v-model:value="selectedCompany" )
        option( v-for="item in companies" :key="item" :value="item.email" ) {{ item.name }}
      WeekiDateInput.me-3.flex-grow-1( name="begdate" placeholder="From" mb="false" )
      WeekiDateInput.flex-grow-1.me-3( name="enddate" placeholder="To" mb="false" )
      img.print-icon.me-3(src="../../assets/img/icons/icon_printer.svg" alt="print" role="button" @click="printPage()")
      img.calendar-icon.me-3(src="../../assets/img/icons/icon_calendar.svg" alt="calendar" role="button" data-bs-target="#WeekiNormalModal_pick_time"
        data-bs-toggle="modal")

    .company-orders.d-flex.justify-content-center.align-items-center.flex-column
      p.fw-bold( v-if=" !isNewOrder " ) you have unread orders from {{ companies.length }} companies
      p.fw-bold( v-else ) You Don't Have Any New Orders, See Last Orders.
      .company-container.d-flex.flex-wrap.justify-content-center
        .d-flex.p-3.ms-3.mb-3.align-items-center.cursor-pointer( v-for="item in companies" :key="item" rol="button" @click="selectCompany(item)" )
          img.me-2( :src="require('../../assets/img/icons/' + item.logo)" width="24" )
          p.mb-0.me-2.fw-bold {{ item.name }}
          span.ps-1.pe-1.me-2( v-if=" item.new != 0 " ) {{ item.new }}
          img( src="../../assets/img/icons/icon_arrow_right.svg" alt="icon" width="12")


  .selectedCompanyExist.w3-animate-opacity( :class="{ 'd-block' : activeCompanyExist }" )
    p.fw-bold New Received Orders :
    .companiesContainer.d-flex.flex-wrap
      .companies.d-flex.pt-2.pb-2.ps-3.pe-3.ms-3.mb-3.cursor-pointer( v-for="item in companies" :key="item" rol="button" @click=" items = item ")
        p.mb-0.me-2 {{ item.name }}
        p.tagPRed.mb-0.me-2 {{ item.new }}
        img( src="../../assets/img/icons/icon_arrow_right.svg" alt="icon" width="12")

    hr
    .d-flex.search-date-bar
      WeekiOptionalDropDown.me-3( label="Company" v-model:value="selectedCompany" )
        option( v-for="item in companies" :key="item" :value="item.email" ) {{ item.name }}
      WeekiDateInput.me-3.flex-grow-1( name="begdate" placeholder="From" mb="false" )
      WeekiDateInput.flex-grow-1.me-3( name="enddate" placeholder="To" mb="false" )
      img.print-icon.me-3(src="../../assets/img/icons/icon_printer.svg" alt="print" role="button" @click="printPage()")
      img.calendar-icon.me-3(src="../../assets/img/icons/icon_calendar.svg" alt="calendar" role="button" data-bs-target="#WeekiNormalModal_pick_time"
        data-bs-toggle="modal")


    .company-name.d-flex.align-items-center.mt-4
      img.me-3( src="../../assets/img/icons/icon_company.png" v-if="Object.keys(items) == 0 " alt="Logo")
      img.me-3( :src="require('../../assets/img/icons/' + items.logo)" v-else alt="Logo")
      p.mb-0.fw-bold.me-3 {{ items.name }}
      span.ps-1.pe-1 {{ items.new }}



    .orders-table.mt-4.me-3

      .orders-table-header.d-flex
        .w-24.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-center Product Details
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-ellipsis Packaging
        .w-8.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-ellipsis Amount(kg)
        .w-8.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-center Price Per Kg(€)
        .w-12.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-center Date & Time Ordered
        .w-12.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-center Date & Time Product Sent
        .w-12.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0 Status
        .w-8.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-center Edit Status


      .orders-table-body
        .orders-table-row.d-flex
          .w-24.p-2.d-flex.align-items-center.justify-content-start
            p.mb-0 Carrots, Anabella, Tehran, 300-400, A
          .w-16.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0 Box
          .w-8.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0 6000
          .w-8.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0 10
          .w-12.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0.text-center 12/16/2020 #[br] 08:00
          .w-12.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0.text-center 12/16/2020 #[br] 08:00
          .w-12.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0.pt-1.pb-1.pe-2.ps-2.canceled-order Canceled
          .w-8.p-2.d-flex.align-items-center.justify-content-center
            img(src="../../assets/img/icons/icon_edit_darkGray.svg" width="18" alt="button" role="button" data-bs-target="#WeekiNormalModal_order_confirmation" data-bs-toggle="modal")

        .orders-table-row.d-flex
          .w-24.p-2.d-flex.align-items-center.justify-content-start
            p.mb-0 Carrots, Anabella, Tehran, 300-400, A
          .w-16.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0 Box
          .w-8.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0 6000
          .w-8.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0 10
          .w-12.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0.text-center 12/16/2020 #[br] 08:00
          .w-12.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0.text-center 12/16/2020 #[br] 08:00
          .w-12.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0.pt-1.pb-1.pe-2.ps-2.pending-order Pending
          .w-8.p-2.d-flex.align-items-center.justify-content-center
            img(src="../../assets/img/icons/icon_edit_darkGray.svg" width="18" alt="button" role="button" data-bs-target="#WeekiNormalModal_order_confirmation" data-bs-toggle="modal")

        .orders-table-row.d-flex
          .w-24.p-2.d-flex.align-items-center.justify-content-start
            p.mb-0 Carrots, Anabella, Tehran, 300-400, A
          .w-16.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0 Box
          .w-8.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0 6000
          .w-8.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0 10
          .w-12.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0.text-center 12/16/2020 #[br] 08:00
          .w-12.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0.text-center 12/16/2020 #[br] 08:00
          .w-12.p-2.d-flex.align-items-center.justify-content-center
            p.mb-0.pt-1.pb-1.pe-2.ps-2.sent-order Sent
          .w-8.p-2.d-flex.align-items-center.justify-content-center
            img(src="../../assets/img/icons/icon_edit_darkGray.svg" width="18" alt="button" role="button" data-bs-target="#WeekiNormalModal_order_confirmation" data-bs-toggle="modal")



    .orders-carts.mt-4.me-3
      .order-cart.p-3
        p Carrots, Anabella, Tehran, 300-400, A

        .orders-carts-row.d-flex.justify-content-between
          p.mb-3.orders-cart-title Status
          p.mb-3.pt-1.pb-1.pe-2.ps-2.canceled-order canceled

        .orders-carts-row.d-flex.justify-content-between
          p.mb-3.orders-cart-title Packaging
          p.mb-3.text-ellipsis Box

        .orders-carts-row.d-flex.justify-content-between
          p.mb-3.orders-cart-title Amount
          p.mb-3 6000(Kg)

        .orders-carts-row.d-flex.justify-content-between
          p.mb-3.orders-cart-title Price Per Kg
          p.mb-3 10(€)

        .orders-carts-row.d-flex.justify-content-between
          p.mb-3.orders-cart-title Company Name
          p.mb-3.text-ellipsis Berlin Eugm

        .orders-carts-row.d-flex.justify-content-between
          p.mb-3.orders-cart-title.text-center Date & Time #[br] Product Sent
          p.mb-3.text-center 12/16/2020 #[br] 08:00

        .orders-carts-row.d-flex.justify-content-between
          p.mb-3.orders-cart-title.text-center Date & Time #[br] Product Sent
          p.mb-3.text-center 12/16/2020 #[br] 08:00

        .orders-carts-row.text-button
          p.text-center(role="button" data-bs-target="#WeekiNormalModal_order_confirmation" data-bs-toggle="modal") Edit Status



WeekiNormalModal(
  name="order_confirmation"
  title="Order Confirmation"
  max-width="900px"
  max-height="75%"
  scrollable="false"
  mfs="true"
  height="unset"
)
  p Carrots, Anabella, Tehran, 300-400, A

  .orders-table

    .orders-table-header.d-flex
      .w-16.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0 Packaging
      .w-12.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0.text-center Amount (kg)
      .w-16.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0.text-center Price Per Kg (€)
      .w-24.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0.text-center Company Name
      .w-16.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0.text-center Date & Time Product Sent
      .w-16.p-2.d-flex.align-items-center.justify-content-center
        p.mb-0.text-center Date & Time Product Sent


    .orders-table-body
      .orders-table-row.d-flex
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0 Box
        .w-12.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0 6000
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0 10
        .w-24.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-ellipsis Berlin Company
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-center 12/16/2020 #[br] 08:00
        .w-16.p-2.d-flex.align-items-center.justify-content-center
          p.mb-0.text-center 12/16/2020 #[br] 08:00


  .order-cart
    .orders-carts-row.d-flex.justify-content-between
      p.mb-3.orders-cart-title Packaging
      p.mb-3.text-ellipsis Box

    .orders-carts-row.d-flex.justify-content-between
      p.mb-3.orders-cart-title Amount
      p.mb-3 6000(Kg)

    .orders-carts-row.d-flex.justify-content-between
      p.mb-3.orders-cart-title Price Per Kg
      p.mb-3 10(€)

    .orders-carts-row.d-flex.justify-content-between
      p.mb-3.orders-cart-title Company Name
      p.mb-3.text-ellipsis Berlin Eugm

    .orders-carts-row.d-flex.justify-content-between
      p.mb-3.orders-cart-title.text-center Date & Time #[br] Product Sent
      p.mb-3.text-center 12/16/2020 #[br] 08:00

    .orders-carts-row.d-flex.justify-content-between
      p.mb-3.orders-cart-title.text-center Date & Time #[br] Product Sent
      p.mb-3.text-center 12/16/2020 #[br] 08:00


  p.grey-text.mt-3.mb-2 Order Description
  textarea.form-control( rows="3" collumn="2" )
  .d-flex.justify-content-between.buttons.mt-4
    WeekiButton( text="Order Canceled" )
    WeekiButton( text="Order Sent" )



WeekiNormalModal.datePicker(
  name="pick_time"
  title="Pick Time"
  max-width="80%"
  max-height="75%"
  scrollable="false"
  mfs="true"
  height="unset"
)
  .d-flex.dateInputs
    WeekiDateInput.me-2.flex-grow-1( name="begdate" placeholder="From" mb="false" v-bind:value="range.start")
    WeekiDateInput.ms-2.flex-grow-1( name="enddate" placeholder="To" mb="false" v-bind:value="range.end" )

  .mt-3
    DatePicker( v-model="range" color="green" is-range)

  WeekiButton( text="Confirm" type="submit")


</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import WeekiDateInput from "@/components/elements/WeekiDateInput.vue"
import WeekiOptionalDropDown from "@/components/elements/WeekiOptionalDropDown.vue"
import WeekiNormalModal from "@/components/elements/WeekiNormalModal.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"
import { Calendar, DatePicker } from 'v-calendar'

@Options({

  // Page Components
  components: {
    WeekiDateInput,
    WeekiOptionalDropDown,
    WeekiNormalModal,
    WeekiButton,
    Calendar,
    DatePicker
  },

  computed : {
    isNewOrder(){
      return this.companies.find(e => e.new === 0);
    }
  },
  methods:{
    selectCompany(item){
      this.items = item
      this.activeCompanyExist = true
    }
  },
  data(){
    return{
      activeCompanyExist : false,
      items : { },
      companies:[
        { email:"CompanyA@gmail.com" , name: "Company A" , logo:"icon_company.png" , new : 13 },
        { email:"CompanyB@gmail.com" , name: "Company B" , logo:"icon_company.png" , new : 13 },
        { email:"CompanyC@gmail.com" , name: "Company C" , logo:"icon_company.png" , new : 13 },
        { email:"CompanyD@gmail.com" , name: "Company D" , logo:"icon_company.png" , new : 13 },
        { email:"CompanyE@gmail.com" , name: "Company E" , logo:"icon_company.png" , new : 13 },
        { email:"CompanyF@gmail.com" , name: "Company F" , logo:"icon_company.png" , new : 13 },
        { email:"CompanyG@gmail.com" , name: "Company G" , logo:"icon_company.png" , new : 13 },
        { email:"CompanyH@gmail.com" , name: "Company H" , logo:"icon_company.png" , new : 13 },
        { email:"CompanyI@gmail.com" , name: "Company I" , logo:"icon_company.png" , new : 13 },
        { email:"CompanyJ@gmail.com" , name: "Company J" , logo:"icon_company.png" , new : 13 },
      ],
      range: {
        start: 2022-10-1,
        end: 2022-10-10
      },
    }
  }
})

export default class ReceivedOrders extends Vue {}
</script>

<style scoped src="../../assets/sass/page/receivedOrders.sass" lang="sass"></style>
<style scoped src="../../assets/sass/widget/dataPicker.sass" lang="sass"></style>
