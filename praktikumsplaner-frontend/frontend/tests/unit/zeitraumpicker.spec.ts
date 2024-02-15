import { shallowMount } from "@vue/test-utils";
import { describe, expect, it } from "vitest";
import ZeitraumPicker from "@/components/meldezeitraeume/ZeitraumPicker.vue";
import Meldezeitraum from "@/types/Meldezeitraum";
import Zeitraum from "@/types/Zeitraum";

describe("Zeitraumpicker.vue", () => {
    it("renders error messages in Zeitraumpicker when date order is wrong", () => {
        const errorMessageStart =
            "Der Beginn des Meldezeitraums muss vor dem Ende liegen.";
        const errorMessageEnd =
            "Das Ende des Meldezeitraums darf nicht vor dem Beginn liegen.";

        const zeitraum = new Meldezeitraum(
            "name",
            new Zeitraum("2020-10-10", "2019-10-10")
        );
        const wrapper = shallowMount(ZeitraumPicker, {
            propsData: {
                value: zeitraum,
                label: "Meldezeitraum",
            },
        });

        expect(wrapper.html()).toContain(errorMessageEnd);
        expect(wrapper.html()).toContain(errorMessageStart);
    });

    it("renders no error messages in Zeitraumpicker when the dates are in correct order", () => {
        const errorMessageStart =
            "Der Beginn des Meldezeitraums muss vor dem Ende liegen.";
        const errorMessageEnd =
            "Das Ende des Meldezeitraums darf nicht vor dem Beginn liegen.";

        const zeitraum = new Meldezeitraum(
            "name",
            new Zeitraum("2019-10-10", "2020-10-10")
        );
        const wrapper = shallowMount(ZeitraumPicker, {
            propsData: { value: zeitraum },
        });

        expect(wrapper.html()).not.toContain(errorMessageEnd);
        expect(wrapper.html()).not.toContain(errorMessageStart);
    });
});
