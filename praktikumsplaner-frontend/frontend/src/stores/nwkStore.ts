import { defineStore } from "pinia";
import NWK from "@/types/NWK";

export const useNwkStore = defineStore("nwk", {
    state: () => ({
        nwk: new NWK("", "", "", "", "", "", false),
    }),
    actions: {
        updateNwkId(newNWK: NWK) {
            this.nwk = newNWK;
        },
    },
});
