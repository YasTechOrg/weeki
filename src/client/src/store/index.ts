import { createStore } from 'vuex'
import createPersistedState from "vuex-persistedstate"

export default createStore({
  state: {
    userData: {},
    at: "",
    at_time: "",
    d_menu: "open"
  },

  plugins: [createPersistedState()],

  mutations: {

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
    }
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
