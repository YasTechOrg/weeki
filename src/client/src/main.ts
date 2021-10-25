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
import BalmUI from 'balm-ui'
import BalmUIPlus from 'balm-ui/dist/balm-ui-plus'
import "balm-ui/dist/balm-ui.css"

const app = createApp(App)
app.use(store)
app.use(router)
app.use(BalmUI)
app.use(Toast)
app.use(BalmUIPlus)
 app.mount('#app')
