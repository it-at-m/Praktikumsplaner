import { useUserStore } from "@/stores/user";
import { APP_SECURITY } from "@/constants";
import { createPinia } from "pinia";

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

    return {
        checkForAnyRole,
        checkForAllRoles,
        isAusbildungsleitung,
        checkForRole,
    };
}
