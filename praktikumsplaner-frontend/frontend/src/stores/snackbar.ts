import { Levels } from "@/api/error";
import { defineStore } from "pinia";

export interface SnackbarState {
    message: string | undefined;
    level: Levels;
    show: boolean;
}

export const useSnackbarStore = defineStore("snackbar", {
    state: (): SnackbarState => ({
        message: undefined,
        level: Levels.INFO,
        show: false,
    }),
    getters: {},
    actions: {
        showMessage(message: {
            message?: string;
            level?: Levels;
            show?: boolean;
        }): void {
            this.message = message.message;
            this.level = message.level ? message.level : Levels.INFO;
            this.show = true;
        },
        updateShow(show: boolean): void {
            this.show = show;
        },
    },
});
