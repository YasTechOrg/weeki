<template lang="pug">

.notificationsWidget

  .header.w-100.d-flex.justify-content-start.align-items-center

    p.mb-0.fw-bolder Notifications

  .er.d-flex.align-items-center.justify-content-center.flex-column( v-if="notifications.length === 0" )

    p.mb-16.fw-bold No Notification Found !

  .mt-12( v-else )

    .w-100.d-flex( v-for="notification in notifications" :key="notification" v-else )

      .nt_icon

        img( :src="require('@/assets/img/icons/icon_' + notification.type + '_gray.svg')" alt="type" )

      .nt_content.flex-grow-1.d-flex.align-items-center.justify-content-center

        p.mb-0.w-100 {{ notification["content"] }}

      .nt_time.d-flex.align-items-center.justify-content-center

        p.m-0 {{ timeCalc(notification['time']) }}

</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import WeekiBorderedIconButton from "@/components/elements/WeekiBorderedIconButton.vue"
import WeekiBorderedIconBtn from "@/components/elements/WeekiBorderedIconBtn.vue"
import WeekiCheckBox from "@/components/elements/WeekiCheckBox.vue"
import WeekiNormalModal from "@/components/elements/WeekiNormalModal.vue"
import WeekiTextInput from "@/components/elements/WeekiTextInput.vue"
import WeekiTextArea from "@/components/elements/WeekiTextArea.vue"
import WeekiButton from "@/components/elements/WeekiButton.vue"
import moment from 'moment'

@Options({

  // Widget Components
  components: {
    WeekiBorderedIconBtn,
    WeekiBorderedIconButton,
    WeekiCheckBox,
    WeekiNormalModal,
    WeekiTextArea,
    WeekiTextInput,
    WeekiButton
  },

  // Widget Computed Variables
  computed: {

    // Get Notifications
    notifications()
    {
      return this.$store.state.notifications.slice().reverse()
    },
  },

  // Widget Methods
  methods: {

    // Calculate Time
    timeCalc(time): string
    {
      return moment().from(new Date(time), true)
    },
  }
})

export default class NotificationsWidget extends Vue {}
</script>

<style src="../../assets/sass/widget/notificationsWidget.sass" scoped lang="sass"></style>