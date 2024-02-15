import { defineStore } from "pinia";

export interface ErrorState {
    title: string | undefined;
    message: string | undefined;
    show: boolean;
}

export const useErrorStore = defineStore("error", {
    state: (): ErrorState => ({
        title: undefined,
        message: undefined,
        show: false,
    }),
    getters: {},
    actions: {
        showMessage(message: {
            title?: string;
            message?: string;
            show?: boolean;
        }): void {
            this.title = message.title;
            this.message = message.message;
            this.show = true;
        },
        updateShow(show: boolean): void {
            this.show = show;
        },
    },
});
