import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate"
import axios from "axios"
import { getToken } from "@/csrfManager"
import Swal from "sweetalert2"
/* eslint @typescript-eslint/no-var-requires: "off" */
/* eslint-disable  @typescript-eslint/no-explicit-any */
const schedule = require('node-schedule')

const store = createStore({
  state: {
    userData: {},
    at: "",
    at_time: "",
    d_menu: "open",
    tasks: [],
    notifications: [],
    unSeenNotification: false,
    profileModal: {},
    profileModalCo: {},
    profileModalStars: {},
    productModalEdit: {}
  },

  plugins: [createPersistedState()],

  mutations: {

    setProfileModal(state, id)
    {
      store.commit("clearProfileModal")

      axios
          .get(`/api/rest/employee/get/${ id }`, {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": store.getters.getAuth
            }
          })

          .then(value =>
          {
            axios

                .get("/api/rest/account/sd/" + value.data["company"], {
                  headers: {
                    "_csrf" : getToken() as any,
                  }
                })

                .then(value2 =>
                {
                  state.profileModalCo = value2.data
                  state.profileModal = value.data

                  const rates = value.data["rate"]

                  state.profileModalStars = Object.keys(rates).includes(state.userData['email']) ? rates[state.userData['email']] : 0
                })

                .catch(reason2 => console.log(reason2))
          })

          .catch(reason => console.log(reason))
    },

    setProfileModalStars(state, num)
    {
      state.profileModalStars = num
    },

    unSeenNotification(state, seen)
    {
      state.unSeenNotification = seen
    },

    clearProfileModal(state)
    {
      state.profileModal = {}
      state.profileModalCo = {}
      state.profileModalStars = null as any
    },

    setNotifications(state, data)
    {
      state.notifications = data
    },

    setAuth(state, token)
    {
      state.at = token
      state.at_time = String(new Date())
    },

    removeAuth(state)
    {
      state.at = ""
      state.at_time = ""
      state.userData = {}
    },

    setUserData(state, data)
    {
      state.userData = data
    },

    changeDashboardMenuState(state, ms)
    {
      state.d_menu = ms
    },

    checkTask(state, index)
    {
      const bodyFormData = new FormData()
      bodyFormData.append("id", state.tasks[index]['id'])
      bodyFormData.append("check", `${!state.tasks[index]['checked']}`)

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
          "/api/rest/tasks/tick",
          bodyFormData,
          {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": `Bearer ${state.at}`
            }
          }
      )
          .then(() => {
            store.commit("getTasks")
            Swal.close()
          })
          .catch(() => Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
          }))
    },

    deleteTask(state, index)
    {
      const bodyFormData = new FormData()
      bodyFormData.append("id", state.tasks[index]['id'])
      Swal.fire({
        title: 'Are you sure?',
        text: "Do you want to delete this task?",
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
                  "/api/rest/tasks/delete",
                  bodyFormData,
                  {
                    headers: {
                      "_csrf" : getToken() as any,
                      "Authorization": `Bearer ${state.at}`
                    }
                  }
              )
                  .then(() => {
                    store.commit("getTasks")
                    Swal.close()
                  })
                  .catch(() => Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Something went wrong!',
                  }))
            }
          })
    },

    disableNotificationsSchedule()
    {
      const all: any[] = Object.values(schedule.scheduledJobs)

      for (let i = 0; i < all.length; i++) all[i].cancel()
    },

    getTasks(state)
    {
      // Get User Tasks
      axios

          // Send Request
          .get("/api/rest/tasks/get", {
                headers: {
                  "_csrf" : getToken() as any,
                  "Authorization": `Bearer ${state.at}`
                }
          })

          // On Success
          .then(tasks =>
          {

            // Set Tasks
            state.tasks = tasks.data

            const allTasks  = state.tasks
            for (let i = 0; i < allTasks.length; i++)
            {
              if (allTasks[i]["notification"] && !allTasks[i]["checked"])
              {
                const title = allTasks[i]["title"]
                const time = allTasks[i]["time"] as string

                const date = new Date()
                date.setHours(Number(time.split(":")[0]))
                date.setMinutes(Number(time.split(":")[1]))
                date.setSeconds(0)
                date.setMilliseconds(0)
                schedule.scheduleJob(date, function()
                {
                  const bodyFormData = new FormData()
                  bodyFormData.append("content", `you should ${title} at ${time}`)
                  bodyFormData.append("type", "notification")

                  axios.post(
                      "/api/rest/notifications/add",
                      bodyFormData,
                      {
                        headers: {
                          "_csrf" : getToken() as any,
                          "Authorization": `Bearer ${state.at}`
                        }
                      }
                  )
                      .then(() => {
                        //
                      })
                      .catch(reason => console.log(reason))
                })
              }
            }
          })

          // On Error
          .catch(reason => console.log(reason))
    },

    addToContacts(state, { id, url })
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
      bodyFormData.append("id", id)

      axios.post(
          "/api/rest/account/contact/add",
          bodyFormData,
          {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": store.getters.getAuth
            }
          }
      )
          .then(() =>
          {
            location.href = `${ url }?res=ac_done`
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

    removeFromContacts(state, { id, url, promise })
    {
      if (promise)
      {
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
                removeContact(id, url)
              }
            })
      }
      else
      {
        removeContact(id, url)
      }
    },

    submitUserStars(state, { id, url, stars })
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
      bodyFormData.append("id", id)
      bodyFormData.append("rate", stars)

      axios.post(
          "/api/rest/account/stars/update",
          bodyFormData,
          {
            headers: {
              "_csrf" : getToken() as any,
              "Authorization": store.getters.getAuth
            }
          }
      )
          .then(() => location.href = `${ url }?res=us_done`)
          .catch(() =>
          {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Something went wrong!',
            })
          })
    },

    setProductEdit(state, data)
    {
      state.productModalEdit = data
    },
  },

  getters: {

    getAuth: state =>
    {
      return `Bearer ${state.at}`
    },

    checkAuth: state =>
    {
      return !(state.at == null || state.at.length === 0 || typeof state.at === "undefined" || state.userData === null)
    },
  },
})

function removeContact(id, url)
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
  bodyFormData.append("id", id)

  axios.post(
      "/api/rest/account/contact/remove",
      bodyFormData,
      {
        headers: {
          "_csrf" : getToken() as any,
          "Authorization": store.getters.getAuth
        }
      }
  )
  .then(() =>
  {
    location.href = `${ url }?res=ac_done`
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

export default store