<template lang="pug">

.tasksWidget

  .w-100.d-flex.justify-content-between.align-items-center

    p.mb-0.fw-bolder( v-if="day == null" ) Today's Tasks
    p.mb-0.fw-bolder( v-else ) {{ getFormattedDate }} Tasks

    WeekiBorderedIconButton(
      data-bs-target="#WeekiNormalModal_tasks_add"
      data-bs-toggle="modal"
      text="Add a Task"
      icon="add"
    )
    WeekiBorderedIconBtn.d-none(
      data-bs-target="#WeekiNormalModal_tasks_add"
      data-bs-toggle="modal"
      icon="add"
    )

  .content.mt-12

    p.mb-0.text-center.d-flex.align-items-center.justify-content-center.h-100( v-if="tasks.length === 0" ) No task found !

    .w-100.d-flex(
      v-for="(task, index) in tasks.sort((a, b) => { return getImportance(b['importance']) - getImportance(a['importance']) })"
      @mouseenter="toggleTaskHover"
      @mouseleave="toggleTaskHover"
      :key="task"
      v-else
    )

      .task_cb.cursor-pointer( @click="checkTask(index)" )

        WeekiCheckBox( :checked="task['checked']" )

      .task_content.flex-grow-1.d-flex.align-items-center.justify-content-center.pr-16

        .mb-0.w-100( :class="{ 'checked' : task['checked'] }" ) {{ task["title"] }}

      .task_bar.d-none

        .d-flex.align-items-center.justify-content-center

          img.pr-16.cursor-pointer.unselectable(
            src="../../assets/img/icons/icon_edit_gray.svg"
            data-bs-target="#WeekiNormalModal_tasks_edit"
            data-bs-toggle="modal"
            @click="openEditTask(index)"
            alt="edit"
          )

          .btn-group

            div( data-bs-toggle="dropdown" data-bs-auto-close="true" aria-expanded="false" )

              img.pr-16.cursor-pointer.unselectable( src="../../assets/img/icons/icon_eye_gray.svg" alt="see details" )

            .dropdown-menu.dropdown-menu-start

              p.mb-0 {{ task["details"] }}

              .mt-12.d-flex.align-items-center.justify-content-start

                span.material-icons.mr-8.md-16( v-if="task['notification']" ) notifications
                span.material-icons.mr-8.md-16( v-else ) notifications_off

                p.mb-0 {{ task["time"] }}

          img.pr-16.cursor-pointer.unselectable(
            src="../../assets/img/icons/icon_delete_gray.svg"
            @click="deleteTask(index)"
            alt="delete"
          )

WeekiNormalModal(
  name="tasks_edit"
  title="Edit Task"
  max-width="500px"
  max-height="73%"
  scrollable="true"
  mfs="true"
  height="unset"
)

  WeekiTextInput(
    v-model:value="update.title"
    name="title"
    type="text"
    label="Task Title"
    mb="false"
  )

  WeekiTextArea.mt-20(
    v-model:value="update.details"
    resize="none"
    label="Task Details"
    height="200px"
    name="details"
  )

  .row.mt-20.ms-0.me-0

    .p-0.col-6

      WeekiTextInput( name="time" type="time" label="Alarm" mb="false" v-model:value="update.time" )

    .p-0.col-6.d-flex.justify-content-end.align-items-center

      input( type="text" name="notification"  v-model="update.notification" hidden )

      span.material-icons.md-30.cursor-pointer.true.unselectable( @click="changeEditNotificationState" ) notifications

  .im.mt-24.mb-24

    p.mb-0 The importance of this task

    input( type="range" name="importance" min="1" max="3" v-model="update.importance" multiple )

    ul.list-unstyled.d-flex.justify-content-between

      li Low
      li Medium
      li Much

  WeekiButton.float-end( @click="updateTask" text="Edit Task" )

WeekiNormalModal(
  name="tasks_add"
  title="Create Task"
  max-width="500px"
  max-height="73%"
  scrollable="true"
  mfs="true"
  height="unset"
)

  WeekiTextInput( name="title" type="text" label="Task Title" mb="false" )

  WeekiTextArea.mt-20(
    resize="none"
    label="Task Details"
    height="200px"
    name="details"
  )

  .row.mt-20.ms-0.me-0

    .p-0.col-6

      WeekiTextInput( name="time" type="time" label="Alarm" mb="false" )

    .p-0.col-6.d-flex.justify-content-end.align-items-center

      input( type="text" name="notification" :value="true" hidden )

      span.material-icons.md-30.cursor-pointer.true.unselectable( @click="changeAddNotificationState" ) notifications

  .im.mt-24.mb-24

    p.mb-0 The importance of this task

    input( type="range" name="importance" min="1" max="3" value="2" multiple )

    ul.list-unstyled.d-flex.justify-content-between

      li Low
      li Medium
      li Much

  WeekiButton.float-end( @click="addTask" text="Create a Task" )

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
import axios from 'axios'
import { getToken } from "@/csrfManager"
import { mapGetters } from "vuex"
import Swal from 'sweetalert2'

@Options({

  // Widget Props
  props: ["day"],

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

  // Widget Variables
  data()
  {
    return {
      update: {
        id: "",
        title: "",
        details: "",
        time: "",
        notification: true,
        importance: 2,
        index: 0
      }
    }
  },

  // Widget Computed Variables
  computed: {

    // Tasks C Variables
    tasks()
    {
      return this.$store.state.tasks
    },

    // Get Formatted Date
    getFormattedDate()
    {
      const date = new Date(this.day).toString().split(" ")
      return `${date[1]},${date[0]} ${date[2]},${date[3]}`
    },

    ...mapGetters([
      "getAuth"
    ]),
  },

  // Widget Methods
  methods: {

    // Task Hover
    toggleTaskHover(event)
    {
      event.target.querySelector(".task_bar").classList.toggle("d-none")
    },

    // Get Importance
    getImportance(importance)
    {
      switch (importance)
      {
        case "LOW":
          return 1

        case "MEDIUM":
          return  2

        case "HIGH":
          return 3
      }
    },

    // Check Task
    checkTask(index)
    {
      this.$store.commit("checkTask", index)
    },

    // Delete Task
    deleteTask(index)
    {
      this.$store.commit("deleteTask", index)
    },

    // Update Task
    updateTask()
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
      bodyFormData.append("id", this.update.id)
      bodyFormData.append("title", this.update.title)
      bodyFormData.append("details", this.update.details)
      bodyFormData.append("time", this.update.time)
      bodyFormData.append("notification", this.update.notification)
      bodyFormData.append("importance", this.update.importance)
      bodyFormData.append("date", this.tasks[this.update.index]["date"])

      axios.post(
          "/api/rest/account/tasks/update",
          bodyFormData,
          {
            headers : {
              "_csrf" : getToken() as any,
              "Authorization": this.getAuth
            }
          }
      )
      .then(() => location.href = `${this.$route.path}?res=update_task_comp`)
      .catch(() => location.href = `${this.$route.path}?res=update_task_err`)
    },

    // Add Task
    addTask()
    {
      const base = document!
          .querySelector("#WeekiNormalModal_tasks_add > div > div > div > .nm-content")!

      const inputs = base.getElementsByTagName("input")
      const textAreas = base.getElementsByTagName("textarea")

      const bodyFormData = new FormData()
      bodyFormData.append("title", inputs[0].value)
      bodyFormData.append("details", textAreas[0].value)
      bodyFormData.append("time", inputs[1].value)
      bodyFormData.append("notification", inputs[2].value)
      bodyFormData.append("importance", inputs[3].value)

      axios.post(
          "/api/rest/account/tasks/add",
          bodyFormData,
          {
            headers : {
              "Content-Type": "multipart/form-data",
              "_csrf" : getToken() as any,
              "Authorization": this.getAuth
            }
          }
      )
      .then(() => location.href = `${this.$route.path}?res=add_task_comp`)
      .catch(() => location.href = `${this.$route.path}?res=add_task_err`)
    },

    // Change Add Task Notification State
    changeAddNotificationState()
    {
      const btn = document!
          .querySelector("#WeekiNormalModal_tasks_add > div > div > div > .nm-content > div.row > div:last-child > span")!
      const input = document!
          .querySelector("#WeekiNormalModal_tasks_add > div > div > div > .nm-content > div.row > div:last-child > input")! as HTMLInputElement
      if (btn.classList.contains("false"))
      {
        btn.classList.remove("false")
        btn.classList.add("true")
        input.value = String(true)
        btn.innerHTML = "notifications"
      }
      else if (btn.classList.contains("true"))
      {
        btn.classList.remove("true")
        btn.classList.add("false")
        input.value = String(false)
        btn.innerHTML = "notifications_off"
      }
    },

    // Change Edit Task Notification State
    changeEditNotificationState()
    {
      const btn = document!
          .querySelector("#WeekiNormalModal_tasks_edit > div > div > div > .nm-content > div.row > div:last-child > span")!
      const input = document!
          .querySelector("#WeekiNormalModal_tasks_edit > div > div > div > .nm-content > div.row > div:last-child > input")! as HTMLInputElement
      if (btn.classList.contains("false"))
      {
        btn.classList.remove("false")
        btn.classList.add("true")
        input.value = String(true)
        btn.innerHTML = "notifications"
        this.update.notification = true
      }
      else if (btn.classList.contains("true"))
      {
        btn.classList.remove("true")
        btn.classList.add("false")
        input.value = String(false)
        btn.innerHTML = "notifications_off"
        this.update.notification = false
      }
    },

    // Open Edit Task Modal
    openEditTask(index)
    {
      this.update.id = this.tasks[index].id
      this.update.title = this.tasks[index].title
      this.update.details = this.tasks[index].details
      this.update.time = this.tasks[index].time
      this.update.index = index

      switch (this.tasks[index].importance)
      {
        case "LOW":
          this.update.importance = 1
          break

        case "MEDIUM":
          this.update.importance = 2
          break

        case "HIGH":
          this.update.importance = 3
          break
      }

      this.update.notification = this.tasks[index].notification
      const btn = document!
          .querySelector("#WeekiNormalModal_tasks_edit > div > div > div > .nm-content > div.row > div:last-child > span")!
      const input = document!
          .querySelector("#WeekiNormalModal_tasks_edit > div > div > div > .nm-content > div.row > div:last-child > input")! as HTMLInputElement
      console.log(this.tasks[index].notification)
      if (this.tasks[index].notification === false)
      {
        btn.classList.remove("true")
        btn.classList.add("false")
        input.value = String(false)
        btn.innerHTML = "notifications_off"
      }
      else
      {
        btn.classList.remove("false")
        btn.classList.add("true")
        input.value = String(true)
        btn.innerHTML = "notifications"
      }
    }
  }
})

export default class TasksWidget extends Vue {}
</script>

<style src="../../assets/sass/widget/tasksWidget.sass" scoped lang="sass"></style>