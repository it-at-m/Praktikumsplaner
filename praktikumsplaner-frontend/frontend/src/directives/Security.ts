import Vue, { watch } from "vue";
import Vuetify from "vuetify/lib";
import { useUserStore } from "@/stores/user";
import { APP_SECURITY } from "@/Constants";

Vue.use(Vuetify);

Vue.directive("security", {
    inserted: function (el, binding, vnode) {
        if (!APP_SECURITY) return;
        const userStore = useUserStore();

        const requiredRole = binding.value;

        const nodeElement = vnode.elm;
        if (nodeElement) {
            (nodeElement as HTMLElement).style.display = "none";
        }
        watch(
            () => userStore.getRoles,
            (newRoles) => {
                if (binding.modifiers.allow) {
                    if (newRoles.some((role) => role == requiredRole)) {
                        const nodeElement = vnode.elm;
                        if (nodeElement) {
                            (nodeElement as HTMLElement).style.display = "";
                        }
                    } else {
                        const nodeElement = vnode.elm;
                        if (nodeElement) {
                            (nodeElement as HTMLElement).style.display = "none";
                        }
                    }
                }
                if (binding.modifiers.restrict) {
                    if (!newRoles.some((role) => role == requiredRole)) {
                        const nodeElement = vnode.elm;
                        if (nodeElement) {
                            (nodeElement as HTMLElement).style.display = "";
                        }
                    } else {
                        const nodeElement = vnode.elm;
                        if (nodeElement) {
                            (nodeElement as HTMLElement).style.display = "none";
                        }
                    }
                }
            },
            {
                deep: true,
            }
        );
    },
});
