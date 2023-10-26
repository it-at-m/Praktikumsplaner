import { shallowMount, createLocalVue } from "@vue/test-utils";
import Vuetify from "vuetify";
import Vue from "vue";
import { createPinia, PiniaVuePlugin } from "pinia";
import ZeitraumPicker from "@/components/Meldezeitraeume/ZeitraumPicker.vue";
import Zeitraum from "@/types/Zeitraum";

const localVue = createLocalVue();
const pinia = createPinia();

describe("Zeitraumpicker.vue", () => {
    let vuetify: any;

    beforeAll(() => {
        Vue.use(PiniaVuePlugin);
        Vue.use(Vuetify);
    });

    beforeEach(() => {
        vuetify = new Vuetify();
    });

    it("renders error messages in Zeitraumpicker when date order is wrong", () => {
        const errorMessageStart = "Das Startdatum muss vor dem Enddatum liegen.";
        const errorMessageEnd = "Das Enddatum muss nach dem Startdatum liegen.";

        const zeitraum = new Zeitraum("2020-10-10", "2019-10-10");
        const wrapper = shallowMount(ZeitraumPicker, {
            localVue,
            pinia,
            vuetify,
            propsData: { value: zeitraum},
        });

        expect(wrapper.html()).toContain(errorMessageEnd);
        expect(wrapper.html()).toContain(errorMessageStart);
    });

    it("renders no error messages in Zeitraumpicker when the dates are in correct order", () => {
        const errorMessageStart = "Das Startdatum muss vor dem Enddatum liegen.";
        const errorMessageEnd = "Das Enddatum muss nach dem Startdatum liegen.";

        const zeitraum = new Zeitraum("2019-10-10", "2020-10-10");
        const wrapper = shallowMount(ZeitraumPicker, {
            localVue,
            pinia,
            vuetify,
            propsData: { value: zeitraum},
        });

        expect(wrapper.html()).not.toContain(errorMessageEnd);
        expect(wrapper.html()).not.toContain(errorMessageStart);
    });
});
