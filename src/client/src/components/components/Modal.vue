<script setup lang="ts">

import {computed, defineProps} from "vue"

const props = defineProps<{
  name: string,
  title: string,
  maxWidth: string,
  maxHeight: string,
  scrollable: boolean,
  height: string,
  mfs: boolean
}>()

const normalModalWidth = computed(() =>
{
  if (props.maxWidth != null) return props.maxWidth
  else return '600px'
})

const getMfs = computed(() => props.mfs)

const normalModalMaxHeight = computed(() =>
{
  if (props.mfs) return "unset"
  else if (props.maxHeight != null) return props.maxHeight + "!important"
  else return 'unset'
})

const normalModalHeight = computed(() =>
{
  if (props.height != null) return props.height
  else return false
})
</script>

<template>
  <teleport to="#modal">
    <div class="weekiNormalModal w3-animate-zoom modal fade"
         :class="{ 'pe-0 ps-0' : getMfs, 'pe-2 ps-2' : !getMfs }"
         :id="'WeekiNormalModal_' + name"
         :aria-labelledby="'WeekiNormalModalLabel_' + name"
         aria-hidden="true"
         aria-modal="true"
         tabindex="-1">

    <div class="modal-dialog"
         :style="{ 'max-width' : normalModalWidth, 'max-height' : normalModalMaxHeight , 'height' : normalModalHeight }"
         :class="{ 'modal-dialog-scrollable': scrollable, 'modal-fullscreen-sm-down' : mfs }">

      <div class="modal-content">
        <div class="modal-body">
          <div class="nm-header d-flex align-items-center justify-content-between">
            <p class="m-0 fw-bolder nm-title">{{ title }}</p>
            <p class="m-0 nm-close material-icons-round md-16 fw-bold" data-bs-dismiss="modal">clear</p>
          </div>

          <div class="nm-content">
            <slot/>
          </div>
        </div>
      </div>
    </div>
    </div>
  </teleport>
</template>
