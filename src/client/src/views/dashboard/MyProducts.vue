<template>
<div id="myProducts">

  <!-- On Load -->
  <div class="load d-flex align-items-center justify-content-center" v-if="products == null" >
    <img src="../../assets/animations/main_loader.svg" alt="Loading...">
  </div>

  <!-- Tab Bar -->
  <WeekiTabBar :bottom="['For Sale', 'For Buy']" v-else>

    <!-- Product Search -->
    <WeekiSearchInput placeholder="Search" v-model:value="productsSearch"/>

    <!-- Buyer Tab -->
    <WeekiTabBarTab btn="for_sale" :active="true">
      <div class="list pt-12 row m-0">
        <div class="col-sm-6 col-md-6 col-lg-4 pt-12 pb-12"
             v-for="item in products" :key="item">

          <!-- Product Card -->
          <ProductCardComponents :product="item" global="false" @doProductReload="getProducts"/>
        </div>
      </div>
    </WeekiTabBarTab>

    <!-- Seller Tab -->
    <WeekiTabBarTab btn="for_buy">
      <div class="list pt-12 row m-0">
        <div class="col-sm-6 col-md-6 col-lg-4 pt-12 pb-12"
             v-for="item in products" :key="item">

          <!-- Product Card -->
          <ProductCardComponents :product="item" global="false" @doProductReload="getProducts"/>
        </div>
      </div>
    </WeekiTabBarTab>
  </WeekiTabBar><!--

  &lt;!&ndash; Tab Bar &ndash;&gt;
  <WeekiTabBar :bottom="['For Sale', 'For Buy']" v-else>

    &lt;!&ndash; Product Search &ndash;&gt;
    <WeekiSearchInput placeholder="Search" v-model:value="productsSearch"/>

    &lt;!&ndash; Buyer Tab &ndash;&gt;
    <WeekiTabBarTab btn="for_sale" :active="true">
      <div class="list pt-12 row m-0">
        <div class="col-sm-6 col-md-6 col-lg-4 pt-12 pb-12"
             v-for="item in searchedProductsSwitch('buyer')" :key="item">

          &lt;!&ndash; Product Card &ndash;&gt;
          <ProductCardComponents :product="item" global="false" @doProductReload="getProducts"/>
        </div>
      </div>
    </WeekiTabBarTab>

    &lt;!&ndash; Seller Tab &ndash;&gt;
    <WeekiTabBarTab btn="for_buy">
      <div class="list pt-12 row m-0">
        <div class="col-sm-6 col-md-6 col-lg-4 pt-12 pb-12"
             v-for="item in searchedProductsSwitch('seller')" :key="item">

          &lt;!&ndash; Product Card &ndash;&gt;
          <ProductCardComponents :product="item" global="false" @doProductReload="getProducts"/>
        </div>
      </div>
    </WeekiTabBarTab>
  </WeekiTabBar>-->
</div>
</template>

<script lang="ts" setup>
// ToDo : BS Mode
import { showToast, Types } from "@/toastManager"
import axios from "axios"
import { getToken } from "@/csrfManager"
import {computed, defineAsyncComponent, onMounted, ref} from "vue"
import router from "@/router";
import store from "@/store";

const WeekiSearchInput = defineAsyncComponent(() => import("@/components/elements/WeekiSearchInput.vue"))
const ProductCardComponents = defineAsyncComponent(() => import("@/components/components/ProductCardComponents.vue"))
const WeekiTabBar = defineAsyncComponent(() => import("@/components/elements/WeekiTabBar.vue"))
const WeekiTabBarTab = defineAsyncComponent(() => import("@/components/elements/WeekiTabBarTab.vue"))

const productsSearch = ref("")
const products = ref(null)

const buyerMethod = (value: any) => value["bs"] === "buyer"
const sellerMethod = (value: any) => value["bs"] === "seller"

const getProducts = () => {
  products.value = null

  axios
      .get("/api/rest/product/get", {
        headers: {
          "_csrf" : getToken() as any,
          "Authorization": store.getters.getAuth
        }
      })
      .then(value => products.value = value.data)
      .catch(reason => console.log(reason))
}

/*
const searchedProducts = computed(() =>
{
  if (products.value !== null) return (products.value as []).filter((el : any) =>
  {
    const query = `${el.type} ${el.family} ${el.city} ${el.country} ${el.code} ${el.grade} ${el.amount} ${el.ppk}`
    return (query).toLowerCase().indexOf(productsSearch.value.toLowerCase()) !== -1
  })
  else return []
})
*/

//const searchedProductsSwitch = (bs: string) => bs === "buyer" ? searchedProducts.value.filter(buyerMethod) : searchedProducts.value.filter(sellerMethod)

onMounted(() =>
{
  switch (router.currentRoute.value.query.res)
  {
    case "done":
      showToast("System : New product added successfully!", Types.SUCCESS)
      break

    case "u_done":
      showToast("System : Product updated successfully!", Types.SUCCESS)
      break
  }

  getProducts()
})
</script>

<style scoped src="../../assets/sass/page/my_products.sass" lang="sass"></style>