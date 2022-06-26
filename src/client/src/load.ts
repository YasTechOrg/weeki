import store from "@/store"
import router from "@/router"
import axios from "axios"
import {getToken} from "@/csrfManager"
import {showToast, Types} from "@/toastManager"
import {computed} from "vue"
import {response} from "@/interface"
import SockJS from "sockjs-client"
import Stomp, {Client} from "webstomp-client"

export const load = async () =>
{
    // Variables
    const route = router.currentRoute.value
    const layout = route.meta["layout"]
    const http_conf = { "_csrf": getToken(), "Authorization": store.getters.getAuth }
    const userInfo = computed(() => store.state.userData)
    const dashboard_responses = {
        add_task_comp: { text: "Task added successfully!", type: Types.SUCCESS },
        add_task_err: { text: "An error occurred while add new task!", type: Types.ERROR },
        da: { text: "Your access to this page has been restricted by your boss!", type: Types.ERROR },
        da_nu: { text: "Normal Users don't have access to the employee page!", type: Types.ERROR },
        update_task_comp: { text: "Task edited successfully!", type: Types.SUCCESS },
        update_task_err: { text: "An error occurred while edit a task!", type: Types.ERROR }
    }
    const notifications = computed(() => store.state.notifications)
    let socket: WebSocket
    let stompClient: Client

    // Methods
    const goTo = (url: string) => router.push(url)
    const getNotifications = async () => store.commit("setNotifications", await new Promise(resolve =>
    {
        axios
            .get("/api/rest/notifications/get", { headers: http_conf })
            .then(value => resolve(value.data))
            .catch(reason => console.log(reason))
    }))

    // Expiration Check
    if (store.getters.checkAuth)
    {
        if (store.getters.checkAuth)
        {
            const d: any = store.state.at_time
            const date1: any = new Date(d)
            const date2: any = new Date()
            const diffTime = Math.abs(date2 - date1)
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24))

            if (diffDays >= 5)
            {
                store.commit("removeAuth")
                if (layout === 'dashboard') await goTo("/account/login?res=expi")
            }
        }

        // Login Duplicate Security CHeck
        if (layout === "account") await goTo("/dashboard")

        // Get User Info
        await axios
            .get("/api/rest/account/user/get", { headers: http_conf})
            .then(async value =>
            {
                // Login tasks
                await store.commit("setUserData", value.data)
                await store.commit("clearProfileModal")
                await store.commit("getTasks")
            })
            .catch(reason =>
            {
                // On Authentication Failed
                console.log(`System Error : Load -> Auth -> Failed`)
                console.log(reason)

                store.commit("removeAuth")

                showToast("System : Your login has expired", Types.WARNING)

                if (layout === 'dashboard') goTo("/account/login?res=expi&back=" + route.path)
            })
            .finally(async () =>
            {
                // Dashboard Process
                if (layout === "dashboard")
                {
                    // Dashboard Security
                    if ((userInfo.value["access"] as string[]).includes(route.meta["id"] as string)) location.href = "/dashboard?res=da"
                    else if (route.meta["id"] === "employee" && userInfo["role"] === "NORMAL_USER") location.href = "/dashboard?res=da_nu"

                    // Dashboard Responses
                    if (route.query.res)
                    {
                        (route.query.res as []).forEach((value: string) => { if (Object.keys(dashboard_responses).includes(value))
                        {
                            const response = (dashboard_responses[value] as response)
                            showToast(`System : ${response.text}`, response.type)
                        }})
                    }

                    // Get Notifications
                    await getNotifications()
                    const seenNotifications: boolean[] = []

                    // Notification Process
                    for (const item in notifications) seenNotifications.push(item["seen"])
                    if (seenNotifications.includes(false)) store.commit("setNotifications", true)

                    // Connect To Socket Server
                    socket = new SockJS(location.origin + "/wst", [], {
                        sessionId: () => userInfo["socketId"]
                    })

                    // Define Stop Client
                    stompClient = Stomp.over(socket)

                    // Disable Stomp Logging
                    stompClient.debug = () => {}

                    // Connect Stomp
                    stompClient.connect({}, () =>
                    {
                        // Subscribe Stomp
                        stompClient.subscribe("/user/notifications/get", (response) =>
                        {
                            // Show Notifications
                            showToast(JSON.parse(response.body).content, Types.INFO)

                            getNotifications()
                            store.commit("unSeenNotification", true)
                        }, http_conf)
                    })
                }
            })
    }
    else if (layout === "dashboard") await goTo("/account/login")
}