<template lang="pug">

#addProducts

  .drop.d-flex.justify-content-center.align-items-center.flex-column.mb-3( v-bind="getData.rootProps()" )

    input( v-bind="getData.inputProps()" accept="image/jpeg, image/png" )

    img.mb-16( src="../../assets/img/images/upload.svg" alt="upload" )

    p.name.fw-bold( v-if="Object.keys(file).length !== 0" ) {{ file["name"] }}

    .txt.d-flex.justify-content-center.align-items-center.flex-column( v-else )

      p.title.fw-bold Drag And Drop Photo

      p.or Or

    WeekiMiniFlatButton( text="Browse" )

  form.row.m-0

    .col-sm-6.col-md-4

      WeekiNormalDropDown( name="type" :list="['dobd', 'domdmo']" label="Type Of Product" )

    .col-sm-6.col-md-4

      WeekiNormalDropDown( name="family" :list="['dobd', 'domdmo']" label="Family Of Product" )

    .col-sm-6.col-md-4

      WeekiOptionalDropDown( name="country" label="Country" v-model:value="countrySelectValue" )

        option( v-for="country in countries" :key="country['nativeName']" :value="((country.name != null) ? country.name : country['nativeName'])" ) {{ country["nativeName"] }}

    .col-sm-6.col-md-4

      WeekiNormalDropDown( name="city" :list="returnableCity" label="City" )

    .col-sm-3.col-md-4

      WeekiTextInput.mb-3( name="code" label="Code" auto-complete="false" req="true" )

    .col-sm-3.col-md-4

      WeekiTextInput.mb-3( name="grade" label="Grade" auto-complete="false" req="true" )

    .col-sm-6.col-md-4

      WeekiTextInput.mb-3( name="packing" label="Packing" auto-complete="false" req="true" )

    .col-sm-6.col-md-8

      WeekiTextInput.mb-3( name="location" label="Location" auto-complete="false" req="true" )

    .col-sm-6.col-md-4

      WeekiTextInput.mb-3( name="amount" label="Inventory Amount(kg)" auto-complete="false" type="number" req="true" min="0" max="9999999" )

    .col-sm-6.col-md-4

      WeekiTextInput.mb-3( name="ppk" label="Price Per Kg(€)" auto-complete="false" type="number" req="true" min="0" max="9999999" )

    .col-sm-6.col-md-4

      WeekiNormalDropDown.mb-3( name="bs" :list="['seller', 'buyer']" label="I am" )

    .col-sm-12

      WeekiTextArea.mb-3( label="Description..." resize="false" height="120px" req="true" )

  WeekiButton.float-end.mr-12( text="Add Products" @click="submitForm" )

</template>

<script lang="ts">
/* eslint-disable @typescript-eslint/no-var-requires */
import { Options, Vue } from 'vue-class-component'
import WeekiNormalDropDown from "@/components/elements/WeekiNormalDropDown.vue"
import WeekiOptionalDropDown from "@/components/elements/WeekiOptionalDropDown.vue"
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue"
import WeekiTextArea from "@/components/elements/WeekiTextArea.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"
import WeekiMiniFlatButton from "@/components/elements/WeekiMiniFlatButton.vue"
import { useDropzone } from "vue3-dropzone"
import { showToast, Types } from "@/toastManager"
import Swal from "sweetalert2"
import axios from "axios"
import { getToken } from "@/csrfManager"
import { mapGetters } from "vuex"
const countries = require('@/assets/json/countries.json')
const cities = require('@/assets/json/cities.json')

@Options({

  // Page Components
  components: {
    WeekiNormalDropDown,
    WeekiOptionalDropDown,
    WeekiTextInput,
    WeekiTextArea,
    WeekiButton,
    WeekiMiniFlatButton
  },

  // Page Data
  data()
  {
    return {
      countrySelectValue: "",
      countries: countries,
      cities: cities.data,
      returnableCity: [],
      file: {}
    }
  },

  // On Page Load
  mounted()
  {
    switch (this.$route.query.res)
    {
      case "err":
        showToast("System : An error occurred creating new file!", Types.ERROR)
        break
    }
  },

  // Page Variable Listeners
  watch: {

    countrySelectValue(val)
    {
      this.returnableCity = []
      this.cities.find(city =>
      {
        if (val === city.country)
        {
          if (!this.returnableCity.includes(city.city))
          {
            this.returnableCity.push(city.city)
          }
        }
        else
        {
          if (!this.returnableCity.includes(val))
          {
            this.returnableCity.push(val)
          }
        }
      })
    },
  },

  // Page Methods
  methods: {

    // On User Drop
    onDrop(file)
    {
      if (["image/png", "image/jpeg"].includes(file[0]["type"]))
      {
        if (file[0]["size"] <= 819200)
        {
          this.file = file[0]
        }
        else
        {
          showToast("System : Image size is more than 800MB!", Types.ERROR)
        }
      }
      else
      {
        showToast("System : Please import the file in photo format!", Types.ERROR)
      }
    },

    // Submit Form
    submitForm()
    {
      const form = (document.querySelector("#addProducts > .row") as HTMLFormElement)

      if (form.checkValidity())
      {
        Swal.fire({
          title: 'Are you sure?',
          text: "Are you sure about data that you entered?",
          icon: 'question',
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

                const name = function (add) { return `#addProducts > .row > div > ${add}` }

                const dropdowns = document.querySelectorAll(name("div > select"))
                const inputs = document.querySelectorAll(name(".weekiTextInput > input"))
                const textarea = document.querySelector(name(".weekiTextArea > textarea"))

                const productFormData = new FormData()
                productFormData.append("type", (dropdowns[0] as HTMLSelectElement).value)
                productFormData.append("family", (dropdowns[1] as HTMLSelectElement).value)
                productFormData.append("country", (dropdowns[2] as HTMLSelectElement).value)
                productFormData.append("city", (dropdowns[3] as HTMLSelectElement).value)
                productFormData.append("bs", (dropdowns[4] as HTMLSelectElement).value)
                productFormData.append("code", (inputs[0] as HTMLInputElement).value)
                productFormData.append("grade", (inputs[1] as HTMLInputElement).value)
                productFormData.append("packing", (inputs[2] as HTMLInputElement).value)
                productFormData.append("location", (inputs[3] as HTMLInputElement).value)
                productFormData.append("amount", (inputs[4] as HTMLInputElement).value)
                productFormData.append("ppk", (inputs[5] as HTMLInputElement).value)
                productFormData.append("description", (textarea as HTMLTextAreaElement).value)

                axios.post(
                    "/api/rest/product/add",
                    productFormData,
                    {
                      headers: {
                        "_csrf" : getToken() as any,
                        "Authorization": this.getAuth,
                      }
                    }
                ).then(value =>
                {
                  if (Object.keys(this.file).length !== 0)
                  {
                    const bodyFormData = new FormData()
                    bodyFormData.append("file", this.file)

                    axios.post(
                        `/api/rest/product/image/${value.data}`,
                        bodyFormData,
                        {
                          headers: {
                            "_csrf" : getToken() as any,
                            "Authorization" : this.getAuth,
                            "Content-Type" : "multipart/form-data"
                          }
                        }
                    )
                    .then(() => location.href = "/dashboard/products/self?res=done")
                    .catch(reason =>
                    {
                      console.log(reason)
                      location.href = "/dashboard/products/add?res=err"
                    })
                  }
                  else location.href = "/dashboard/products/self?res=done"
                })
                .catch(reason =>
                {
                  console.log(reason)
                  location.href = "/dashboard/products/add?res=err"
                })
              }
            })
      }
      else
      {
        form.reportValidity()
      }
    }
  },

  // Page Computed Variables
  computed: {

    getData()
    {
      const { getRootProps, getInputProps, ...rest } = useDropzone({ onDrop: this.onDrop })
      return { rootProps: getRootProps, inputProps: getInputProps, rest: rest }
    },

    ...mapGetters([
      "getAuth"
    ]),
  }
})

export default class AddProducts extends Vue {}
</script>

<style scoped src="../../assets/sass/page/addProducts.sass" lang="sass"></style>