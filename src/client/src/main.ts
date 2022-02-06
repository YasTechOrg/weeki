import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import "@/assets/lib/w3.css"
import "@/assets/lib/google_icons.css"
import 'vue3-carousel/dist/carousel.css'
import "vue-toastification/dist/index.css"
import Toast from "vue-toastification"
import Vue3Progress from "vue3-progress"

const app = createApp(App)
app.use(store)
app.use(router)
app.use(Toast)

const options = {
    position: "fixed",
    height: "3px",
    color: "#68B64D"
}

app.use(Vue3Progress, options)

app.mount('#app')