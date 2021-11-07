<template lang="pug">

.weekiNormalModal.w3-animate-zoom.modal.fade.pe-0(
  :id="'WeekiNormalModal_' + name"
  :aria-labelledby="'WeekiNormalModalLabel_' + name"
  aria-hidden="true"
  aria-modal="true"
  tabindex="-1"
)

  .modal-dialog(
    :style="{ 'max-width' : normalModalWidth, 'max-height' : normalModalMaxHeight , 'height' : normalModalHeight }"
    :class="{ 'modal-dialog-scrollable': scrollable, 'modal-fullscreen-sm-down' : mfs }"
  )

    .modal-content

      .modal-body

        .nm-header.d-flex.align-items-center.justify-content-between

          p.m-0.fw-bolder.nm-title {{ title }}

          p.m-0.nm-close.material-icons-round.md-16.fw-bold( data-bs-dismiss="modal" ) clear

        .nm-content

          slot

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'

@Options({

  // On Element Load
  mounted()
  {
    document.body.appendChild(document.getElementById('WeekiNormalModal_' + this.name)!)
  },

  // Element Props
  props: ["name", "title", "maxWidth", "maxHeight", "scrollable", "height", "mfs"],

  // Element Computed Variables
  computed: {

    // Modal Width
    normalModalWidth()
    {
      if (this.maxWidth != null)
      {
        return this.maxWidth
      }
      else
      {
        return '600px'
      }
    },

    // Modal Max Height
    normalModalMaxHeight()
    {
      if (this.mfs === "true")
      {
        return "unset"
      }
      else if (this.maxHeight != null)
      {
        return this.maxHeight + "!important"
      }
      else
      {
        return 'unset'
      }
    },

    // Modal Height
    normalModalHeight()
    {
      if (this.height != null)
      {
        return this.height
      }
      else
      {
        return false
      }
    }
  }
})

export default class WeekiNormalModal extends Vue {}
</script>