import { Levels } from "@/api/Error";
import FetchUtils from "@/api/FetchUtils";
import { API_BASE, MELDEZEITRAUM_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import Meldezeitraum from "@/types/Meldezeitraum";

export default {
    create(meldezeitraum: Meldezeitraum): Promise<Meldezeitraum> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}`,
            FetchUtils.getPOSTConfig(meldezeitraum)
        ).then((response) => {
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Meldezeitraum erfolgreich angelegt",
                    level: Levels.SUCCESS,
                });
                return response.json();
            } else {
                FetchUtils.defaultResponseHandler(response);
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
    deleteMeldezeitraumById(id: string | undefined): Promise<void> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}/${id}`,
            FetchUtils.getDELETEConfigNoBody()
        )
            .then((response) => {
                useSnackbarStore().showMessage({
                    message: "☑ Löschen erfolgreich.",
                    level: Levels.SUCCESS,
                });
                FetchUtils.defaultResponseHandler(response);
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: err.message,
                    level: Levels.ERROR,
                });
                FetchUtils.defaultResponseHandler(err);
            });
    },
};
