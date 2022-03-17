<template lang="pug">

#home

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
        | mmd

      .filter.mt-12
        | mmd

    .col-md-9.p-0.pl-16

      .ml-12.mr-12.s_part.w3-hide-small.w3-hide-medium.d-flex.align-items-center.justify-content-start

        p.mb-0 Sort By

        WeekiChipButton( :enabled="sort_by === 'near'" @click="sort_by = 'near'" text="Newest" )
        WeekiChipButton( :enabled="sort_by === 'cheap'" @click="sort_by = 'cheap'" text="Cheapest" )
        WeekiChipButton( :enabled="sort_by === 'mex'" @click="sort_by = 'mex'" text="Most Expensive" )

      nav.m-auto.pr-12.pl-12.pt-4( aria-label="Weeki Search Pagination" )
        ul.pagination

          li.page-item
            a.page-link( aria-label="Previous" )
              span( aria-hidden="true" ) &laquo;

          li.page-item
            a.page-link 1
          li.page-item
            a.page-link 2
          li.page-item
            a.page-link 3
          li.page-item
            a.page-link 4

          li.page-item
            a.page-link( aria-label="Next" )
              span( aria-hidden="true" ) &raquo;

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import WeekiOptionalDropDown from "@/components/elements/WeekiOptionalDropDown.vue"
import WeekiSearchInput from "@/components/elements/WeekiSearchInput.vue"
import WeekiChipButton from "@/components/elements/WeekiChipButton.vue"

@Options({

  // Page Components
  components: {
    WeekiOptionalDropDown,
    WeekiSearchInput,
    WeekiChipButton
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
      sortedSearchResult: []
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
  }
})
export default class Home extends Vue {}
</script>

<style scoped src="../../assets/sass/page/home.sass" lang="sass"></style>