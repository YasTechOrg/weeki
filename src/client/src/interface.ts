import {Types} from "@/toastManager"

export interface response {
    text: string,
    type: Types
}

/*
export interface progressPlugin {
    $init: boolean,
    state: {
        timer: { fadeAway: any, fill: any },
        isFull: boolean,
        isWFA: boolean,
    },
    init(),
    __fillBeginning(),
    __fillFinally(),
    __fadeBeginning(),
    __fadeFinally(),
    __isFilling(),
    __isFading(),
    __killFading(),
    __waiting(),
    __isWaiting(),
    start(),
    height(h),
    __hide(),
    finish(),
}*/
