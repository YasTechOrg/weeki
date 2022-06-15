<template lang="pug">

#home.gm

  .w-100.w3-hide-large

    .w-100.row.m-0

      .col-10.d-flex.justify-content-start.align-items-center.p-0

        p.mb-0.mr-8 Sort By

        WeekiOptionalDropDown(
          label="Sort By"
          v-model:value="sort_by"
          sl="false"
          mb="false"
        )

          option( value="near" :selected="sort_by === 'near'" ) Newest
          option( value="cheap" :selected="sort_by === 'cheap'" ) Cheapest
          option( value="mex" :selected="sort_by === 'mex'" ) Most Expensive

      .col-2.d-flex.justify-content-end.align-items-center.p-0

        img.cursor-pointer( src="../../assets/img/icons/icon_filter.svg" alt="filter" )

    p.w-100.mb-0.mt-16 {{ getSearchQueries }}

  .d_content.row.m-0

    .col-md-3.p-0.w3-hide-medium.w3-hide-small

      WeekiSearchInput( placeholder="Search Fruits Or Vegetables" v-model:value="s_text" )

      .bs.mt-12

        p.fw-bolder.mb-20 Buyer / Seller

        .d-flex.justify-content-center.align-items-center

          .pe-1.w-100
            WeekiChipButton.w-100( :enabled="s_bs === 1" @click="s_bs = 1" text="I am a buyer" )

          .ps-1.w-100
            WeekiChipButton.w-100( :enabled="s_bs === 2" @click="s_bs = 2" text="I am a seller" )

      .filter.mt-12

        .part

          p.fw-bolder.mb-20 Product Details

          WeekiTextInput.mt-16(
            label="Product's City"
            v-model:value="s_pc"
            class="mt-16"
            name="pc"
            type="text"
            :list="cities"
            mb="false" req="false"
          )

          WeekiTextInput.mt-16(
            label="Code"
            v-model:value="s_pcd"
            class="mt-16"
            name="pcd"
            type="text"
            mb="false" req="false"
          )

          WeekiTextInput.mt-16(
            label="Grade"
            v-model:value="s_pg"
            class="mt-16"
            name="pg"
            type="text"
            mb="false" req="false"
          )

          WeekiTextInput.mt-16(
            label="Packing"
            v-model:value="s_pp"
            class="mt-16"
            name="pp"
            type="text"
            mb="false" req="false"
          )

        .part

          p.fw-bolder.mb-20.mt-20 Amount (kg)

          Slider.mt-24.me-2.ms-2( v-model="s_am" :min="0" :max="9999999" :tooltips="false" )

          .row.m-0.mt-16

            .col-6.ps-0
              WeekiTextInput(
                mb="false"
                v-model:value="s_am[0]"
                label="Min"
                name="min_am"
                type="number"
                min="0" max="9999999"
              )

            .col-6.pe-0
              WeekiTextInput(
                mb="false"
                v-model:value="s_am[1]"
                label="Max"
                name="max_am"
                type="number"
                min="0" max="9999999"
              )

        .part

          p.fw-bolder.mb-20.mt-20 Price Per Kg (€)

          Slider.mt-24.me-2.ms-2( v-model="s_ppk" :min="0" :max="9999999" :tooltips="false" )

          .row.m-0.mt-16

            .col-6.ps-0
              WeekiTextInput(
                mb="false"
                v-model:value="s_ppk[0]"
                label="Min"
                name="min_ppk"
                type="number"
                min="0" max="9999999"
              )

            .col-6.pe-0
              WeekiTextInput(
                mb="false"
                v-model:value="s_ppk[1]"
                label="Max"
                name="max_ppk"
                type="number"
                min="0" max="9999999"
              )

        div

          p.fw-bolder.mb-20.mt-20 Seller Details

          WeekiTextInput.mt-16(
            label="Company Name"
            v-model:value="s_cn"
            type="text"
            name="cn"
            mb="false"
            req="false"
          )

          WeekiTextInput.mt-16(
            label="Seller Name"
            v-model:value="s_sn"
            type="text"
            name="cn"
            mb="false"
            req="false"
          )

    .col-md-9.p-0.pl-16.me-auto.ms-auto

      .ml-12.mr-12.s_part.w3-hide-small.w3-hide-medium.d-flex.align-items-center.justify-content-start

        p.mb-0 Sort By

        WeekiChipButton( :enabled="sort_by === 'near'" @click="sort_by = 'near'" text="Newest" )
        WeekiChipButton( :enabled="sort_by === 'cheap'" @click="sort_by = 'cheap'" text="Cheapest" )
        WeekiChipButton( :enabled="sort_by === 'mex'" @click="sort_by = 'mex'" text="Most Expensive" )

      vue-lottie-player.mt-40.mb-40.w-auto(
        :animationData="require('../../assets/animations/loading.json')"
        v-if="searchResult != null && searchResult.length === 0"
        name="loading"
        width="256px"
        height="256px"
        loop
        autoPlay
      )

      vue-lottie-player.mt-40.mb-40.w-auto(
        :animationData="require('../../assets/animations/notfound.json')"
        v-if="searchResult == null"
        name="loading"
        width="256px"
        height="256px"
        loop
        autoPlay
      )

      .pl.row.m-0( v-if="searchResult != null && searchResult.length > 0" )

        .col-md-4.pl-12.pr-12.pt-12( v-for="product in sortedSearchResult" :key="product" )

          ProductCardComponent( global="true" :product="product" )

      nav.m-auto.pr-12.pl-12.pt-40( aria-label="Weeki Search Pagination" v-if="searchResult != null && searchResult.length > 0 && pages !== 0" )

        ul.pagination

          li.page-item
            a.page-link( aria-label="Previous" v-if="pages !== 0 && page !== 1" @click="page = page - 1" )
              span( aria-hidden="true" ) &laquo;

          li.page-item( v-for="item in pages" :key="item" :class="{ 'active' : item === page }" )
            a.page-link( @click="page = item" ) {{ item }}

          li.page-item
            a.page-link( aria-label="Next" v-if="pages !== 0 && page !== pages" @click="page = page + 1" )
              span( aria-hidden="true" ) &raquo;

</template>

<script lang="ts">
/* eslint-disable  @typescript-eslint/no-empty-function */
/* eslint-disable  @typescript-eslint/no-this-alias */

import { Options, Vue } from 'vue-class-component'
import WeekiOptionalDropDown from "@/components/elements/WeekiOptionalDropDown.vue"
import WeekiSearchInput from "@/components/elements/WeekiSearchInput.vue"
import WeekiChipButton from "@/components/elements/WeekiChipButton.vue"
import VueLottiePlayer from "vue-lottie-player"
import ProductCardComponents from "@/components/components/ProductCardComponents.vue"
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue"
import city from "../../assets/json/cities.json"
import Slider from "@vueform/slider"
import SockJS from "sockjs-client"
import Stomp from "webstomp-client"
import {watch} from "vue"

@Options({

  // Page Components
  components: {
    WeekiOptionalDropDown,
    WeekiSearchInput,
    WeekiChipButton,
    VueLottiePlayer,
    WeekiTextInput,
    Slider,
    ProductCardComponent: ProductCardComponents
  },

  // Page Variables
  data()
  {
    return {
      baseurl: location.origin,
      searchClient: null,
      cities: [],
      companies: [],
      sellers: [],

      // Search Items
      sort_by: "near",
      s_text: "",
      s_bs: 1,
      s_location: "all",
      s_pc: "",
      s_pcd: "",
      s_pg: "",
      s_pp: "",
      s_am: [0, 9999999],
      s_ppk: [0, 9999999],
      s_cn: "",
      s_sn: "",

      searchResult: [],
      sortedSearchResult: [],

      page: 1,
      pages: 0
    }
  },

  // On Page Load
  async mounted()
  {

    // Define Queries
    const urlQueries = [
      "text",
      "bs",
      "location",
      "pc",
      "pcd",
      "pg",
      "pp",
      "min_am",
      "max_am",
      "min_ppk",
      "max_ppk",
      "cn",
      "sn"
    ]

    // Set Search Value
    for (const [key, value] of Object.entries(this.$route.query))
    {
      if (urlQueries.includes(key) && value !== null)
      {
        switch (key)
        {
          case "min_am":
            this.s_am[0] = value
            break

          case "max_am":
            this.s_am[1] = value
            break

          case "min_ppk":
            this.s_ppk[0] = value
            break

          case "max_ppk":
            this.s_ppk[1] = value
            break

          case "bs":
            this.s_bs = Number(value)
            break

          default:
            this["s_" + key] = value
            break
        }
      }
    }

    // Define City Array
    for (let i = 0; i < city.data.length; i++)
    {
      this.cities.push(city.data[i].city)
    }

    // Connect To Socket Server
    this.searchSocket = new SockJS(this.baseurl + "/wst")

    // Define Stomp Client
    this.searchClient = Stomp.over(this.searchSocket)

    // Disable Stomp Logging
    this.searchClient.debug = () => {}

    // Define this
    const t = this

    // Connect Stomp
    this.searchClient.connect({}, () =>
    {

      // Subscribe Stomp
      this.searchClient.subscribe('/user/search/get', function (response)
      {
        const resp = JSON.parse(response.body)

        if (resp[1].length === 0)
        {
          t.searchResult = null
          t.pages = 0
        }
        else
        {
          t.searchResult = resp[1]
          t.getSortedProducts()
          t.pages = resp[0]
        }
      })

      this.searchClient.send("/ss/do-search", "", { 'search': JSON.stringify(this.getSearchObject), 'page': this.page })

      watch(() => this.getSearchObject, () =>
      {
        this.searchResult = []
        this.searchClient.send("/ss/do-search", "", { 'search': JSON.stringify(this.getSearchObject), 'page': this.page })
      })

      watch(() => this.page, () =>
      {
        window.scrollTo(0, 0)
        this.searchResult = []
        this.searchClient.send("/ss/do-search", "", { 'search': JSON.stringify(this.getSearchObject), 'page': this.page })
      })
    })
  },

  // Page Methods
  methods: {
    getSortedProducts()
    {
      if (this.searchResult)
      {
        this.sortedSearchResult = this.searchResult.sort((a, b) =>
        {
          if(this.sort_by === 'near') return +new Date(b["date"]) - +new Date(a["date"])

          else if(this.sort_by === 'cheap') return a["ppk"] - b["ppk"]

          else if(this.sort_by === 'mex') return b["ppk"] - a["ppk"]
        })
      }
    }
  },

  // Page Computed Variables
  computed: {

    // Get Search Queries
    getSearchQueries()
    {
      let bs = ""
      if (this.s_bs === 1)
      {
        bs = "I'm Buyer"
      }
      else
      {
        bs = "I'm Seller"
      }

      const items = [
        bs,
        this.s_text,
        this.s_location,
        this.s_pc,
        this.s_pg,
        this.s_pcd,
        this.s_pp,
        `${this.s_am[0]} kg - ${this.s_am[1]} kg`,
        `${this.s_ppk[0]} € - ${this.s_ppk[1]} €`,
        this.s_cn,
        this.s_sn
      ]

      let result = ""
      for (let i = 0; i < items.length; i++)
      {
        if (items[i] !== "")
        {
          if (i !== items.length - 1)
          {
            result += items[i] + " / "
          }
          else
          {
            result += items[i]
          }
        }
      }

      return result
    },

    // Get Search Items As Object
    getSearchObject()
    {
      return {
        text: this.s_text,
        bs: this.s_bs,
        location: this.s_location,
        pc: this.s_pc,
        pcd: this.s_pcd,
        pg: this.s_pg,
        pp: this.s_pp,
        min_am: this.s_am[0],
        max_am: this.s_am[1],
        min_ppk: this.s_ppk[0],
        max_ppk: this.s_ppk[1],
        cn: this.s_cn,
        sn: this.s_sn
      }
    }
  },

  // Page Variable Watchers
  watch: {

    // Watch Sort By
    sort_by()
    {
      this.getSortedProducts()
    }
  }
})
export default class Home extends Vue {}
</script>

<style scoped src="../../assets/sass/page/home.sass" lang="sass"></style>