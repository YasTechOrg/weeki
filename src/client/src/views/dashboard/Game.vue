<!--suppress ALL -->
<template lang="pug">

#game.me-5.ms-5.mt-4.mb-4
  p.mb-4 Play And Relax !

  .row.desktop-mode
    .col-3.d-flex.justify-content-center.mb-5( v-for="item in game" :key="item" )
      .pe-3.ps-3.pt-5.pb-5
        a.text-decoration-none.text-black( :href="item.url" class="d-flex flex-column" )
          img.m-auto( v-if=" item.photo !== '' " :src="require('../../assets/'+ item.photo)" alt="photo")
          img.m-auto( v-else src="../../assets/img/images/game-console.png" alt="photo" )
          p.mt-4.pb-0 {{ item.name }}

  .mobile-mode
    .d-flex.justify-content-between.pt-3.pb-3.ps-4.pe-4( v-for="item in game" :key="item" )
      a.text-decoration-none.text-black( :href="item.url")
        .d-flex.align-items-center
          img( v-if=" item.photo !== '' " :src="require('../../assets/'+ item.photo)" alt="photo" )
          img( v-else src="../../assets/img/images/game-console.png" alt="photo" )
          p.mb-0 {{ item.name }}


</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // Page Components
  components: {

  },
  data() {
    return {
      game: [
        {name: "Sudoku Game", url: "https://www.google.com/", photo: ""},
        {name: "Sudoku Game", url: "https://www.google.com/", photo: ""},
        {name: "Sudoku Game", url: "https://www.google.com/", photo: ""},
        {name: "Sudoku Game", url: "https://www.google.com/", photo: ""},
        {name: "Sudoku Game", url: "https://www.google.com/", photo: ""},
        {name: "Sudoku Game", url: "https://www.google.com/", photo: ""},
        {name: "Sudoku Game", url: "https://www.google.com/", photo: ""},
        {name: "Sudoku Game", url: "https://www.google.com/", photo: ""},
        {name: "Sudoku Game", url: "https://www.google.com/", photo: ""}
      ]
    }
  }
})

export default class Game extends Vue {}
</script>

<style scoped src="../../assets/sass/page/newspaper.sass" lang="sass"></style>