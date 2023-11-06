import { defineStore } from "pinia";

export interface HeaderState {
    appHeader: string;
}

export const useHeaderStore = defineStore("header", {
    state: (): HeaderState => ({
        appHeader: "Praktikumsplaner",
    }),
    getters: {},
    actions: {
        setHeader(payload: string): void {
            this.appHeader = payload;
        },
    },
});
