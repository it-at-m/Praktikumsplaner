import Vue from "vue";

import { APP_SECURITY } from "@/Constants";
import Vuetify from "vuetify/lib";
import { useUserStore } from "@/stores/user";
import { createPinia, PiniaVuePlugin } from "pinia";

Vue.use(Vuetify);
Vue.use(PiniaVuePlugin);
Vue.use(createPinia);

Vue.directive("security", {
    inserted: function (el, binding, vnode) {
        const userStore = useUserStore();

        // eslint-disable-next-line no-console
        console.log(userStore);
        // eslint-disable-next-line no-console
        console.log(userStore.$state);
        // eslint-disable-next-line no-console
        console.log(userStore.$state.roles);
        //binding.value ist die geforderte Rolle
        if (userStore.$state.roles && APP_SECURITY) {
            if (Array.isArray(binding.value)) {
                if (
                    !binding.value.some((a) =>
                        userStore.$state.roles?.includes(a)
                    )
                ) {
                    //Wenn der User nicht eine der geforderten Rollen besitzt, dann wird das Element nicht angezeigt.
                    vnode.elm?.parentElement?.removeChild(vnode.elm);
                }
            } else if (!userStore.$state.roles?.includes(binding.value)) {
                //Wenn der User nicht die geforderte Rolle besitzt, dann wird das Element nicht angezeigt.
                vnode.elm?.parentElement?.removeChild(vnode.elm);
            }
        }
    },
});
