import { shallowMount } from "@vue/test-utils";
import ZeitraumPicker from "@/components/Meldezeitraeume/ZeitraumPicker.vue";
import Zeitraum from "@/types/Zeitraum";

describe("Zeitraumpicker.vue", () => {
    it("renders error messages in Zeitraumpicker when date order is wrong", () => {
        const errorMessageStart =
            "Das Startdatum muss vor dem Enddatum liegen.";
        const errorMessageEnd = "Das Enddatum muss nach dem Startdatum liegen.";

        const zeitraum = new Zeitraum("2020-10-10", "2019-10-10");
        const wrapper = shallowMount(ZeitraumPicker, {
            propsData: {
                value: zeitraum,
            },
        });

        expect(wrapper.html()).toContain(errorMessageEnd);
        expect(wrapper.html()).toContain(errorMessageStart);
    });

    it("renders no error messages in Zeitraumpicker when the dates are in correct order", () => {
        const errorMessageStart =
            "Das Startdatum muss vor dem Enddatum liegen.";
        const errorMessageEnd = "Das Enddatum muss nach dem Startdatum liegen.";

        const zeitraum = new Zeitraum("2019-10-10", "2020-10-10");
        const wrapper = shallowMount(ZeitraumPicker, {
            propsData: { value: zeitraum },
        });

        expect(wrapper.html()).not.toContain(errorMessageEnd);
        expect(wrapper.html()).not.toContain(errorMessageStart);
    });
});
