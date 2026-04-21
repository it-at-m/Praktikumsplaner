import { createPinia } from "pinia";

import { APP_SECURITY } from "@/constants";
import { useUserStore } from "@/stores/user";

export function useSecurity() {
  const userStore = useUserStore();
  function checkForRole(role: string) {
    return userStore.roles.includes(role) || !APP_SECURITY;
  }

  function checkForAnyRole(roles: string[]) {
    return roles.some((r) => userStore.roles.includes(r)) || !APP_SECURITY;
  }

  function checkForAllRoles(roles: string[]) {
    return (
      useUserStore(createPinia()).roles.every((r) => roles.includes(r)) ||
      !APP_SECURITY
    );
  }

  function isAusbildungsleitung() {
    return checkForRole("AUSBILDUNGSLEITUNG");
  }

  function isAusbilder() {
    return checkForRole("AUSBILDER");
  }

  return {
    checkForAnyRole,
    checkForAllRoles,
    isAusbildungsleitung,
    isAusbilder,
    checkForRole,
  };
}
