<template lang="pug">

.productCardComponents

  .position-relative.p_image.img_loading( ref="p_img" )

    img( :src="getImage" alt="product image" @load="loadImage" )

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

      .d-flex.justify-content-start.align-items-center.mt-16( v-if="getGlobal" )

        img( src="../../assets/img/icons/icon_company_gray.svg" alt="company" )

        p.title.mb-0.fw-bolder( v-if="product['company'] !== null" ) {{ product['company'] }}
        p.title.mb-0.fw-bolder( v-else )  {{ "-" }}

      .d-flex.justify-content-start.align-items-center.mt-16( v-if="getGlobal" )

        img( src="../../assets/img/icons/icon_person_gray.svg" alt="seller" )

        p.title.mb-0.fw-bolder( v-if="product['seller'] !== ''" ) {{ product['seller'] }}
        p.title.mb-0.fw-bolder( v-else )  {{ product['company'] }}

      .d-flex.justify-content-start.align-items-center.mt-16( v-if="getGlobal" )

        img( src="../../assets/img/icons/icon_location_gray.svg" alt="location" )

        p.title.mb-0.fw-bolder {{ product["location"] }}

    .navigation.d-flex.align-items-center.justify-content-between.mt-24

      .d-flex.align-items-center.justify-content-start( v-if="getGlobal" )

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

      .d-flex.align-items-center.justify-content-start( v-else )

        img.cursor-pointer(
          src="../../assets/img/icons/icon_delete_gray.svg"
          @click="deleteProduct"
          alt="delete"
        )

        img.cursor-pointer.ml-16(
          data-bs-target="#WeekiNormalModal_product_edit"
          src="../../assets/img/icons/icon_edit_gray.svg"
          @click="setProductEdit"
          data-bs-toggle="modal"
          alt="edit"
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
import Swal from "sweetalert2"
import axios from "axios"
import { getToken } from "@/csrfManager"
import { showToast, Types } from "@/toastManager"
import WeekiNormalModal from "@/components/elements/WeekiNormalModal.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"

@Options({

  // Widget Props
  props: ["product", "global"],

  // Widget Components
  components: {
    WeekiNormalModal,
    WeekiButton
  },

  // Widget Computed Variables
  computed: {

    ...mapGetters([
      "checkAuth",
      "getAuth"
    ]),

    // Get Product Image
    getImage()
    {
      const img = this.product["images"]
      return img.length !== 0 ? `/api/rest/cdn/product/images/${img[0]}` : require("../../assets/img/images/no_img.png")
    },

    // Get Is Global
    getGlobal()
    {
      return this.global === "true"
    }
  },

  // Page Methods
  methods: {

    // Delete Product
    deleteProduct()
    {
      const bodyFormData = new FormData()
      bodyFormData.append("id", this.product["id"])

      Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        reverseButtons: true,
        confirmButtonColor: '#68B64D',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes'
      })
      .then((result) =>
      {
        if (result.isConfirmed)
        {
          Swal.fire({
            padding: "60px",
            width: 153,
            allowOutsideClick: false,
            allowEscapeKey: false,
            didOpen()
            {
              Swal.showLoading()
            }
          })
          axios.post(
              "/api/rest/product/remove",
              bodyFormData,
              {
                headers: {
                  "_csrf" : getToken() as string,
                  "Authorization": this.getAuth
                }
              }
          )
          .then(() =>
          {
            this.$emit("doProductReload")
            Swal.close()
            showToast("System : Product removed successfully!", Types.SUCCESS)
          })
          .catch(() =>
          {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Something went wrong!',
            })
          })
        }
      })
    },

    // Load Image
    loadImage()
    {
      this.$refs.p_img.classList.replace('img_loading', 'img_loaded')
    },

    // Get Complete Employee
    setProductEdit()
    {
      const product = this.product
      this.$store.commit("setProductEdit", {
        id: product["id"],
        files: product["images"],
        query: `${product["family"]}, ${product["city"] }, ${product["code"] }, ${product["grade"]}`,
        packing: product["packing"],
        location: product["location"],
        amount: product["amount"],
        ppk: product["ppk"],
        description: product["description"]
      })
    },
  }
})

export default class ProductCardComponents extends Vue {}
</script>

<style src="../../assets/sass/components/productCardComponents.sass" scoped lang="sass"></style>