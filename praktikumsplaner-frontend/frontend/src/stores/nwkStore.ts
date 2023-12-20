import { defineStore } from "pinia";
import Nwk from "@/types/Nwk";

export const useNwkStore = defineStore("nwk", {
    state: () => ({
        nwk: new Nwk("", "", "", "", "", [], false),
    }),
    actions: {
        updateNwkId(newNwk: Nwk) {
            this.nwk = newNwk;
        },
    },
});
