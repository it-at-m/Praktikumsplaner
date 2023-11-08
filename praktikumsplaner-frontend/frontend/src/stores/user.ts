import { defineStore } from "pinia";

export interface UserState {
    username: string | undefined;
    roles: string[];
}

export const useUserStore = defineStore("user", {
    state: (): UserState => ({
        username: "",
        roles: [],
    }),
    getters: {
        getRoles(): string[] {
            return this.roles;
        },
    },
    actions: {
        setUsername(payload: string): void {
            this.username = payload;
        },
        setRoles(payload: string[]): void {
            this.roles = payload;
        },
    },
});
