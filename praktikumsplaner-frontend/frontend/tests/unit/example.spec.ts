import { shallowMount, createLocalVue } from "@vue/test-utils";
import Vuetify from "vuetify";
import Vue from "vue";
import TheSnackbar from "@/components/TheSnackbar.vue";
import { createPinia, PiniaVuePlugin } from "pinia";

const localVue = createLocalVue();
const pinia = createPinia();

describe("TheSnackbar.vue", () => {
    let vuetify: any;

    beforeAll(() => {
        Vue.use(PiniaVuePlugin);
        Vue.use(Vuetify);
    });

    beforeEach(() => {
        vuetify = new Vuetify();
    });

    it("renders props.message when passed", () => {
        const message = "Hello_World";
        const wrapper = shallowMount(TheSnackbar, {
            localVue,
            pinia,
            vuetify,
            propsData: { message: message },
        });

        expect(wrapper.html()).toContain(message);
    });
});
