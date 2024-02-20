import FetchUtils from "@/api/FetchUtils";
import { API_BASE, MELDEZEITRAUM_BASE } from "@/constants";
import Meldezeitraum from "@/types/Meldezeitraum";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/Error";

export default {
    create(meldezeitraum: Meldezeitraum): Promise<Meldezeitraum> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}`,
            FetchUtils.getPOSTConfig(meldezeitraum)
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Meldezeitraum erfolgreich angelegt",
                    level: Levels.SUCCESS,
                });
                return response.json();
            }
        });
    },
    getCurrentMeldezeitraum(): Promise<Meldezeitraum[]> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?period=current`,
            FetchUtils.getGETConfig()
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            return response.json();
        });
    },
    getUpcomingMeldezeitraueme(): Promise<Meldezeitraum[]> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?period=future`,
            FetchUtils.getGETConfig()
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            return response.json();
        });
    },
    getPassedMeldezeitraueme(): Promise<Meldezeitraum[]> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?period=past`,
            FetchUtils.getGETConfig()
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            return response.json();
        });
    },
    getAllMeldezeitraeume(): Promise<Meldezeitraum[]> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}`,
            FetchUtils.getGETConfig()
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            return response.json();
        });
    },
};
