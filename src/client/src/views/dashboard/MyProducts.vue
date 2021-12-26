<template lang="pug">

#myProducts

  WeekiSearchInput( placeholder="Search" v-model:value="productsSearch" )

  .load.d-flex.align-items-center.justify-content-center( v-if="products == null" )

    img( src="../../assets/animations/main_loader.svg" alt="Loading..." )




</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { showToast, Types } from "@/toastManager"
import WeekiSearchInput from "@/components/elements/WeekiSearchInput.vue"
import axios from "axios"
import { getToken } from "@/csrfManager"
import { mapGetters } from "vuex"

@Options({

  // Page Components
  components: {
    WeekiSearchInput
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

    axios
        .get("/api/rest/product/get", {
          headers: {
            "_csrf" : getToken() as any,
            "Authorization": this.getAuth
          }
        })

        .then(value => this.products = value.data)
        .catch(reason => console.log(reason))
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
      return this.new_employee.filter((el) =>
      {
        return (el["firstname"] + el["lastname"]).toLowerCase().indexOf(this.employeeSearch.toLowerCase()) !== -1
      })
    },
  }
})

export default class MyProducts extends Vue {}
</script>

<style scoped src="../../assets/sass/page/my_products.sass" lang="sass"></style>