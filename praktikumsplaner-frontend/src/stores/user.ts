import { defineStore } from "pinia";

export interface UserState {
  username: string | undefined;
  department: string | undefined;
  roles: string[];
}

export const useUserStore = defineStore("user", {
  state: (): UserState => ({
    username: "",
    department: "",
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
    setDepartment(payload: string): void {
      this.department = payload;
    },
    setRoles(payload: string[]): void {
      this.roles = payload;
    },
  },
});
