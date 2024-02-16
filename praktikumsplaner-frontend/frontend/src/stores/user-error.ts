import { defineStore } from "pinia";

export interface UserErrorState {
    title: string | undefined;
    message: string | undefined;
    visible: boolean;
}

export const useUserErrorStore = defineStore("user-error", {
    state: (): UserErrorState => ({
        title: undefined,
        message: undefined,
        visible: false,
    }),
    getters: {},
    actions: {
        showUserError(message: {
            title?: string;
            message?: string;
            show?: boolean;
        }): void {
            this.title = message.title;
            this.message = message.message;
            this.visible = true;
        },
    },
});
