<template lang="pug">

#newspaper.me-5.ms-5.mt-4.mb-4
  
  .newspaper-notExist.fw-bold.text-align-center( v-if="newspapers.length===0" )

    p You Can Add Shortcut Newspapers Here By Entering The URL
    WeekiFlatButton( data-bs-target="#WeekiNormalModal_newspaper_new" data-bs-toggle="modal" text="Add Shortcut" )

  .newspaper-exist( v-else )

    .d-flex.mb-4.justify-content-between

      p.mt-auto.mb-auto Your Favorite Newspapers
      
      WeekiBorderedIconButton.ps-3.pe-3.pt-2.pb-2.w3-hide-small(
        data-bs-target="#WeekiNormalModal_newspaper_new"
        data-bs-toggle="modal"
        text="Add a Shortcut"
        icon="add"
      )
      
      WeekiBorderedIconBtn.ps-2.pe-2.pt-1.pb-1.w3-hide-large.w3-hide-medium(
        data-bs-target="#WeekiNormalModal_newspaper_new"
        data-bs-toggle="modal"
        icon="add"
      )

    .row.desktop-mode

      .col-3.d-flex.justify-content-center.mb-5( v-for="(item , index) in newspapers" :key="item")

        .d-flex.flex-column.p-3

          .btn-group.newspaper-options.justify-content-end

            div( data-bs-toggle="dropdown" data-bs-auto-close="true" aria-expanded="false" )

              img.cursor-pointer.unselectable( src="../../assets/img/icons/icon_doted_gray.svg" alt="menu" )

            ul.dropdown-menu.dropdown-menu-start

              li
                a.dropdown-item.d-flex.align-items-center.cursor-pointer(
                  @click="setValue(index)"
                  data-bs-target="#WeekiNormalModal_newspaper_edit"
                  data-bs-toggle="modal"
                )
                  span.material-icons.md-20.me-2 edit
                  | Edit

              li
                a.dropdown-item.d-flex.align-items-center.cursor-pointer( @click="removeNewspapers(index)" )
                  span.material-icons.md-20.me-2 delete
                  | Delete


          a.d-flex.flex-column.text-decoration-none.text-black( :href="item.url"  target="_blank" )
            img.m-auto( src="../../assets/img/images/newspaper.png" alt="photo" )
            p.mt-4.mb-3 {{ item.name }}

    .mobile-mode.w-100

      .row.mb-3.ms-0.me-0.mt-0( v-for="(item,index) in newspapers" :key="item" )

        .col-10.d-flex.align-items-center

          a.text-decoration-none.text-black( :href="item.url" target="_blank" )
            img( src="../../assets/img/images/newspaper.png" alt="photo")

          a.text-decoration-none.text-black( :href="item.url" target="_blank" ) {{ item.name }}

        .col-2.d-flex.align-items-center.justify-content-end.p-0

          .btn-group.newspaper-options.justify-content-end

            div( data-bs-toggle="dropdown" data-bs-auto-close="true" aria-expanded="false" )

              img.cursor-pointer.unselectable( src="../../assets/img/icons/icon_doted_gray.svg" alt="menu" )

            ul.dropdown-menu.dropdown-menu-start

              li
                a.dropdown-item.d-flex.align-items-center.cursor-pointer(
                  @click="setValue(index)"
                  data-bs-target="#WeekiNormalModal_newspaper_edit"
                  data-bs-toggle="modal"
                )
                  span.material-icons.md-20.me-2 edit
                  | Edit

              li
                a.dropdown-item.d-flex.align-items-center.cursor-pointer( @click="removeNewspapers(index)" )
                  span.material-icons.md-20.me-2 delete
                  | Delete

WeekiNormalModal(
  name="newspaper_new"
  title="Add a Shortcut"
  max-width="600px"
  max-height="281px"
  scrollable="true"
  mfs="false"
  height="281px"
)
  WeekiTextInput.mb-3( label="Name" v-model:value="newName" required )
  WeekiTextInput.mb-3( label="URL" v-model:value="newURL" required )
  WeekiButton.float-end( text="Add a Shortcut" type="submit" @click="addNewspaper" data-bs-dismiss="modal" )

WeekiNormalModal(
  name="newspaper_edit"
  title="Edit Shortcut"
  max-width="600px"
  max-height="281px"
  scrollable="true"
  mfs="false"
  height="281px"
)
  WeekiTextInput.mb-4( label="Name" v-model:value="editName" )
  WeekiTextInput.mb-4( label="URL" v-model:value="editURL" )
  WeekiButton.float-end( text="Edit Shortcut" @click="editNewspaper" data-bs-dismiss="modal"  )

</template>

<script lang="ts">
import {Options, Vue} from 'vue-class-component'
import WeekiNormalModal from "@/components/elements/WeekiNormalModal.vue"
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"
import WeekiBorderedIconButton from "@/components/elements/WeekiBorderedIconButton.vue"
import WeekiBorderedIconBtn from "@/components/elements/WeekiBorderedIconBtn.vue"
import WeekiFlatButton from "@/components/elements/WeekiFlatButton.vue"
import { mapGetters } from "vuex"
import axios from "axios"
import { getToken } from "@/csrfManager"
import Swal from "sweetalert2"
import { showToast, Types } from "@/toastManager"

@Options({

  // Page Components
  components: {
    WeekiNormalModal,
    WeekiTextInput,
    WeekiButton,
    WeekiBorderedIconButton,
    WeekiBorderedIconBtn,
    WeekiFlatButton
  },

  // Page Variables
  data()
  {
    return {
      newName: "",
      newURL: "",
      newsIndex: 0,
      editName: "",
      editURL: "",
      newspapers: []
    }
  },

  // On Page Load
  mounted()
  {
    this.getNewspapers()
  },

  // Page Methods
  methods: {

    removeNewspapers(index)
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

      const bodyFormData = new FormData()
      bodyFormData.append("index", index)

      axios.post(
          "/api/rest/newspaper/remove",
          bodyFormData,
          {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": this.getAuth
            }
          }
      )
      .then(() =>
      {
        this.getNewspapers()
        showToast("System : Newspaper removed successfully!", Types.SUCCESS)
        Swal.close()
      })
      .catch(() =>
      {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Something went wrong!',
        })
      })
    },

    getNewspapers()
    {
      axios
          .get("/api/rest/newspaper/get", {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": this.getAuth
            }
          })
          .then(value => this.newspapers = value.data)
          .catch(reason => console.log(reason))
    },

    addNewspaper()
    {
      let newspaper = { name: "", url: "" }

      if (this.newName !== "" && this.newURL !== "")
      {
        newspaper.name = this.newName

        if (this.newURL.includes("http", 0)) newspaper.url = this.newURL
        else newspaper.url = "http://" + this.newURL

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

        const bodyFormData = new FormData()
        bodyFormData.append("name", newspaper.name)
        bodyFormData.append("url", newspaper.url)

        axios.post(
            "/api/rest/newspaper/add",
            bodyFormData,
            {
              headers: {
                "_csrf" : getToken() as any,
                "Authorization": this.getAuth
              }
            }
        )
        .then(() =>
        {
          this.getNewspapers()
          showToast("System : Newspaper added successfully!", Types.SUCCESS)
          Swal.close()
        })
        .catch(() =>
        {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
          })
        })
        .finally(() =>
        {
          this.newName = ""
          this.newURL = ""
        })
      }
    },

    setValue(index)
    {
      this.newsIndex = index
      this.editName = this.newspapers[index].name
      this.editURL = this.newspapers[index].url
    },

    editNewspaper()
    {
      if (this.editName !== "" && this.editURL !== "")
      {

        if (!this.newURL.includes("http", 0)) this.editURL = "http://" + this.editURL

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

        const bodyFormData = new FormData()
        bodyFormData.append("index", this.newsIndex)
        bodyFormData.append("name", this.editName)
        bodyFormData.append("url", this.editURL)

        axios.post(
            "/api/rest/newspaper/update",
            bodyFormData,
            {
              headers: {
                "_csrf" : getToken() as any,
                "Authorization": this.getAuth
              }
            }
        )
            .then(() =>
            {
              this.getNewspapers()
              showToast("System : Newspaper edited successfully!", Types.SUCCESS)
              Swal.close()
            })
            .catch(() =>
            {
              Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
              })
            })
            .finally(() =>
            {
              this.editName = ""
              this.editURL = ""
            })
      }
    }
  },

  // Page Computed Variables
  computed: {

    // Get Store Getters
    ...mapGetters([
      "getAuth"
    ]),
  }
})

export default class Newspaper extends Vue {}
</script>

<style scoped src="../../assets/sass/page/newspaper.sass" lang="sass"></style>