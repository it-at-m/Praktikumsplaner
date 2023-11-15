import Vue, { watch } from "vue";
import Vuetify from "vuetify/lib";
import { useUserStore } from "@/stores/user";
import { APP_SECURITY } from "@/Constants";

Vue.use(Vuetify);

Vue.directive("security", {
    inserted: function (el, binding, vnode) {
        if (APP_SECURITY !== "true") return;
        const userStore = useUserStore();

        const requiredRole = binding.value;

        const nodeElement = vnode.elm;
        if (nodeElement) {
            (nodeElement as HTMLElement).style.display = "none";
        }
        watch(
            () => userStore.getRoles,
            (newRoles) => {
                const nodeElement = vnode.elm;

                if (!nodeElement) return;

                if (
                    (binding.modifiers.allow &&
                        newRoles.some((role) => role == requiredRole)) ||
                    (binding.modifiers.restrict &&
                        !newRoles.some((role) => role == requiredRole))
                ) {
                    (nodeElement as HTMLElement).style.display = "";
                } else {
                    (nodeElement as HTMLElement).style.display = "none";
                }
            },
            {
                deep: true,
            }
        );
    },
});
