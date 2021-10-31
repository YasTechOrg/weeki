<template lang="pug">

#faq

  .vp.me-auto.ms-auto

    iframe(
      :src="getVideoUrl"
      :title="videos[selected].title"
      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
      allowfullscreen
    )

    h1.mb-0.mt-40.fw-bolder.text-start {{ videos[selected].description }}

  .lp.row.me-0.ms-0

    .col-md-4.pl-16.pr-16.cursor-pointer(
      v-for="(video, index) in videos"
      @click="goToVideo(index)"
      :key="video"
    )

      .d-flex.justify-content-center.align-items-center

        p.fw-bolder.text-center.m-0 {{ video["title"] }}

      p.mb-0.pl-1.pr-16 {{ video["description"] }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import { sanitizeUrl } from "@braintree/sanitize-url"
const medias = require('@/assets/json/faq_videos.json')

/* eslint @typescript-eslint/no-var-requires: "off" */
@Options({

  // Page Variables
  data()
  {
    return {

      // Selected
      selected: 0,

      // Videos
      videos: medias
    }
  },

  // Page Computed Variables
  computed: {

    getVideoUrl()
    {
      return sanitizeUrl(`https://www.youtube.com/embed/${ this.videos[this.selected].url }?showinfo=0&enablejsapi=1&origin=${ location.origin }`)
    }
  },

  // Page Methods
  methods: {

    // Go To Video
    goToVideo(index)
    {
      this.selected = index
      window.scrollTo(0,0)
    }
  },

})

export default class Faq extends Vue {}
</script>

<style scoped src="../../assets/sass/page/faq.sass" lang="sass"></style>