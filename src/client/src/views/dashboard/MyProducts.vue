<template lang="pug">

#myProducts

  .load.d-flex.align-items-center.justify-content-center( v-if="products == null" )

    img( src="../../assets/animations/main_loader.svg" alt="Loading..." )

  .list.pt-12.row.m-0( v-else )

    WeekiSearchInput.col-sm-12( placeholder="Search" v-model:value="productsSearch" )

    .col-sm-6.col-md-6.col-lg-4.pt-12.pb-12( v-for="item in filteredProducts" :key="item" )
      ProductCardComponents( :product="item" global="false" @doProductReload="getProducts" )

  WeekiTabBar( :bottom="['Hello', 'Do']" )

    WeekiTabBarTab( btn="hello" :active="true" )
      | sdnodnpo

    WeekiTabBarTab( btn="do" )
      | djwsoindo

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { showToast, Types } from "@/toastManager"
import WeekiSearchInput from "@/components/elements/WeekiSearchInput.vue"
import ProductCardComponents from "@/components/components/ProductCardComponents.vue"
import WeekiTabBar from "@/components/elements/WeekiTabBar.vue"
import WeekiTabBarTab from "@/components/elements/WeekiTabBarTab.vue"
import axios from "axios"
import { getToken } from "@/csrfManager"
import { mapGetters } from "vuex"

@Options({

  // Page Components
  components: {
    WeekiSearchInput,
    ProductCardComponents,
    WeekiTabBar,
    WeekiTabBarTab
  },

  // Page Variables
  data()
  {
    return {
      productsSearch: "",
      products: null,
    }
  },

  // On Page Load
  mounted()
  {
    switch (this.$route.query.res)
    {
      case "done":
        showToast("System : New product added successfully!", Types.SUCCESS)
        break
    }

    this.getProducts()
  },

  // Page Methods
  methods: {

    // Get Products
    getProducts()
    {
      this.products = null

      axios
          .get("/api/rest/product/get", {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": this.getAuth
            }
          })

          .then(value => this.products = value.data)
          .catch(reason => console.log(reason))
    }
  },

  // Page Computed Variables
  computed: {

    // Get Store Getters
    ...mapGetters([
      "getAuth"
    ]),

    // Get Filtered New Employees
    filteredProducts()
    {
      return this.products.filter((el) =>
      {
        const query = `${el.type}, ${el.family}, ${el.city},  ${el.country}, ${el.code}, ${el.grade}, ${el.amount}, ${el.ppk}`
        return (query).toLowerCase().indexOf(this.productsSearch.toLowerCase()) !== -1
      })
    },
  }
})

export default class MyProducts extends Vue {}
</script>

<style scoped src="../../assets/sass/page/my_products.sass" lang="sass"></style>