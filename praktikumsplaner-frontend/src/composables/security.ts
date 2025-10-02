import { createPinia } from "pinia";

import { APP_SECURITY } from "@/constants";
import { useUserStore } from "@/stores/user";

export function useSecurity() {
    const userStore = useUserStore();
    function checkForRole(role: string) {
        return userStore.roles.includes(role) || APP_SECURITY !== "true";
    }

    function checkForAnyRole(roles: string[]) {
        return (
            roles.some((r) => userStore.roles.includes(r)) ||
            APP_SECURITY !== "true"
        );
    }

    function checkForAllRoles(roles: string[]) {
        return (
            useUserStore(createPinia()).roles.every((r) => roles.includes(r)) ||
            APP_SECURITY !== "true"
        );
    }

    function isAusbildungsleitung() {
        return checkForRole("ROLE_AUSBILDUNGSLEITUNG");
    }

    function isAusbilder() {
        return checkForRole("ROLE_AUSBILDER");
    }

    return {
        checkForAnyRole,
        checkForAllRoles,
        isAusbildungsleitung,
        isAusbilder,
        checkForRole,
    };
}
