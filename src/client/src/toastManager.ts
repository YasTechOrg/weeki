import { POSITION, useToast } from "vue-toastification"

// Define Toast Service
const toast = useToast()

// Define Toast Setting
const defaultToastSetting = {
    position: POSITION.TOP_RIGHT,
    timeout: 5000,
    closeOnClick: true,
    pauseOnFocusLoss: true,
    pauseOnHover: true,
    draggable: true,
    draggablePercent: 0.6,
    showCloseButtonOnHover: false,
    hideProgressBar: true,
    icon: true,
    rtl: false
}

// Defile Toast Types
export enum Types
{
    WARNING,
    ERROR,
    SUCCESS,
    INFO
}

// Show Toast Method
export function showToast(text: string, type: Types)
{
    switch (type)
    {
        case Types.ERROR:
            toast.error(text, defaultToastSetting)
            break

        case Types.INFO:
            toast.info(text, defaultToastSetting)
            break

        case Types.SUCCESS:
            toast.success(text, defaultToastSetting)
            break

        case Types.WARNING:
            toast.warning(text, defaultToastSetting)
            break
    }
}