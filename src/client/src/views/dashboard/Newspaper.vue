<!--suppress ALL -->
<template lang="pug">

#newspaper
  #newspaper_layout_base.me-5.ms-5.mt-4.mb-4
    .newspaper-notExist.fw-bold.text-align-center( v-if="newspapers.length===0" )

      p You Can Add Shortcut Newspapers Here By Entering The URL
      button.btn( data-bs-toggle="modal" data-bs-target="#WeekiNormalModal_newspaper_new" ) Add Shortcut

    .newspaper-exist( v-else )

      .d-flex.mb-4.justify-content-between

        p.mt-auto.mb-auto Your Favorite Newspapers
        button.btn.ps-3.pe-3.pt-2.pb-2.w3-hide-small( data-bs-toggle="modal"
          data-bs-target="#WeekiNormalModal_newspaper_new" )
          img.me-2( src="../../assets/img/icons/icon_plus_green.svg" alt="icon" )
          |Add a Shortcut

        button.btn.ps-2.pe-2.pt-1.pb-1.w3-hide-large.w3-hide-medium( data-bs-toggle="modal"
          data-bs-target="#WeekiNormalModal_newspaper_new" )
          img( src="../../assets/img/icons/icon_plus_green.svg" alt="icon" )

      .row.desktop-mode
        .col-3.d-flex.justify-content-center.mb-5( v-for="(item , index) in newspapers" :key="item")
          .d-flex.flex-column.p-3
            .btn-group.image-dropdown.newspaper-options.justify-content-end
              .d-flex.align-items-center.display-relative( id="dottedMenu_options" data-bs-toggle="dropdown"
                data-bs-display="static" data-bs-auto-close="true" aria-expanded="false" )
                img.ms-auto.items-menu.p-2( src="../../assets/img/icons/icon_doted_gray.svg" alt="icon")

              .dropdown-menu.dropdown-menu-end.dropdown-style.p-0( aria-labelledby="dottedMenu_options" )
                button.d-block.btn.w-100.text-align-start( data-bs-toggle="modal" @click="setValue(index)"
                  data-bs-target="#WeekiNormalModal_newspaper_edit" )
                  img( src="../../assets/img/icons/icon_edit_gray.svg" alt="icon" )
                  |Edit

                button.d-block.btn.w-100.text-align-start( @click="removeNewspapers(index)" )
                  img( src="../../assets/img/icons/icon_delete_gray.svg" class="me-1" alt="icon")
                  |Delete
            a( :href="item.url" class="d-flex flex-column")
              img.m-auto( v-if=" item.photo !== '' " :src="require('../../assets/'+ item.photo)" alt="photo")
              img.m-auto( v-else src="../../assets/img/images/newspaper.png" alt="photo" )
              p.mt-4.mb-3 {{ item.name }}




      .mobile-mode.w-100
        .row.mb-3.ms-0.me-0.mt-0( v-for="(item,index) in newspapers" :key="item" )

          .col-10.d-flex.align-items-center
            a.aStyle( :href="item.url" )
              img( v-if=" item.photo !== '' " :src="require('../../assets/'+ item.photo)" alt="photo" )
              img( v-else src="../../assets/img/images/newspaper.png" alt="photo")

            a.aStyle( :href="item.url" ) {{ item.name }}

          .col-2.d-flex.align-items-center.justify-content-end.p-0
            .btn-group.image-dropdown.justify-content-end
              .d-flex.align-items-center.display-relative( id="dottedMenu_options_mobile" data-bs-toggle="dropdown"
                data-bs-display="static" data-bs-auto-close="true" aria-expanded="false")
                img.ms-auto.items-menu.ps-3.pe-4.pt-3.pb-3( src="../../assets/img/icons/icon_doted_gray.svg" alt="icon")

              .dropdown-menu.dropdown-menu-end.dropdown-style.p-0( aria-labelledby="dottedMenu_options_mobile" )
                button.d-block.btn.w-100.text-align-start(data-bs-toggle="modal" @click="setValue(index)"
                  data-bs-target="#WeekiNormalModal_newspaper_edit")
                  img( src="../../assets/img/icons/icon_edit_gray.svg" alt="icon")
                  |Edit

                button.d-block.btn.w-100.text-align-start( @click="removeNewspapers(index)" )
                  img.me-1( src="../../assets/img/icons/icon_delete_gray.svg" alt="icon" )
                  |Delete

WeekiNormalModal(
  name="newspaper_new"
  title="Add a Shortcut"
  max-width="600px"
  max-height="33%"
  scrollable="true"
  mfs="false"
  height="unset"
)
  WeekiTextInput.mb-3( label="Name" v-model:value="newName" required )
  WeekiTextInput.mb-3( label="URL" v-model:value="newURL" required )
  WeekiButton.float-end( text="Add a Shortcut" type="submit" )

WeekiNormalModal(
  name="newspaper_edit"
  title="Edit Shortcut"
  max-width="600px"
  max-height="33%"
  scrollable="true"
  mfs="false"
  height="unset"
)
  WeekiTextInput.mb-4( label="Name" v-model:value="editName" required )
  WeekiTextInput.mb-4( label="URL" v-model:value="editURL" required )
  WeekiButton.float-end( text="Edit Shortcut" type="submit" )

</template>

<script lang="ts">

import {Options, Vue} from 'vue-class-component'
import WeekiNormalModal from "@/components/elements/WeekiNormalModal.vue"
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue";
import WeekiButton from "@/components/elements/WeekiButton.vue";

@Options({

  // Page Components
  components: {
    WeekiNormalModal,
    WeekiTextInput,
    WeekiButton
  },
  methods: {
    removeNewspapers(index) {

      this.newspapers.splice(index, 1)
    },
    addNewspaper() {

      let newspaper = {name: "", url: "", photo: ""}

      if (this.newName !== "" && this.newURL !== "") {

        newspaper.name = this.newName
        newspaper.photo = ""
        if (this.newURL.includes("http", 0)) newspaper.url = this.newURL
        else newspaper.url = "http://" + this.newURL

        this.newspapers.push(newspaper)
        this.newName = ""
        this.newURL = ""
      }
    },

    setValue(index) {

      this.newsIndex = index
      this.editName = this.newspapers[index].name
      this.editURL = this.newspapers[index].url
    },

    editNewspaper() {

      if (this.editName !== "" && this.editURL !== "") {

        this.newspapers[this.newsIndex].name = this.editName
        this.newspapers[this.newsIndex].photo = ""
        if (this.newURL.includes("http", 0)) this.newspapers[this.newsIndex].url = this.editURL
        else this.newspapers[this.newsIndex].url = "http://" + this.editURL

        this.editName = ""
        this.editURL = ""

      }


    }

  },
  data() {
    return {
      newName: "",
      newURL: "",
      newsIndex: 0,
      editName: "",
      editURL: "",
      newspapers: [
        {name: "Berlin NewspaperBerlin Newspaper", url: "https://www.google.com/", photo: ""},
        {name: "Berlin NewspaperBerlin Newspaper", url: "https://www.google.com/", photo: ""},
        {name: "Berlin NewspaperBerlin Newspaper", url: "https://www.google.com/", photo: ""},
        {name: "Berlin NewspaperBerlin Newspaper", url: "https://www.google.com/", photo: ""},
        {name: "Berlin NewspaperBerlin Newspaper", url: "https://www.google.com/", photo: ""},
        {name: "Berlin NewspaperBerlin Newspaper", url: "https://www.google.com/", photo: ""},
        {name: "Berlin NewspaperBerlin Newspaper", url: "https://www.google.com/", photo: ""},
        {name: "Berlin NewspaperBerlin Newspaper", url: "https://www.google.com/", photo: ""},
        {name: "Berlin NewspaperBerlin Newspaper", url: "https://www.google.com/", photo: ""}
      ]
    }
  }
})

export default class Newspaper extends Vue {
}
</script>
<style scoped src="../../assets/sass/page/newspaper.sass" lang="sass"></style>
