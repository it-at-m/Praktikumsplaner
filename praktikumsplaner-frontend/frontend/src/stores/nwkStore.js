import { defineStore } from "pinia";

export const useNwkStore = defineStore("nwk", {
    state: () => ({
        nwkId: undefined,
    }),
    actions: {
        updateNwkId(newId) {
            console.log("Das ist die neue Id im Store: " + newId);
            this.nwkId = newId;
        },
    },
});
