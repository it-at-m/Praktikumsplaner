import { APP_SECURITY } from "@/constants";
import { useUserStore } from "@/stores/user";
import { watch } from "vue";

export function useSecurity() {
    function checkRoles(role: string | undefined | null): boolean {
        if (APP_SECURITY !== "true") return true;
        const userStore = useUserStore();

        watch(
            () => userStore.getRoles,
            (newRoles) => {
                return newRoles.some((r) => r == role);
            },
            {
                deep: true,
            }
        );
        return false;
    }

    return { checkRoles };
}
