import Vue, { watch } from "vue";
import Vuetify from "vuetify/lib";
import { useUserStore } from "@/stores/user";
import { APP_SECURITY } from "@/Constants";

Vue.use(Vuetify);

Vue.directive("security-allow", {
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
                // eslint-disable-next-line no-console
                console.log("newRoles >" + newRoles);
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
            },
            {
                deep: true,
            }
        );
    },
});
Vue.directive("security-restrict", {
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
                // eslint-disable-next-line no-console
                console.log("newRoles >" + newRoles);
                // eslint-disable-next-line no-console
                console.log(
                    "newRolesSome >" +
                        newRoles.some((role) => role == requiredRole)
                );
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
            },
            {
                deep: true,
            }
        );
    },
});
