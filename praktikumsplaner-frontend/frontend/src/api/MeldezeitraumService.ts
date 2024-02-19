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
        )
            .then((response) => {
                useSnackbarStore().showMessage({
                    message: "☑ Speichern erfolgreich.",
                    level: Levels.SUCCESS,
                });
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: err.message,
                    level: Levels.ERROR,
                });
                FetchUtils.defaultResponseHandler(err);
            });
    },
    getCurrentMeldezeitraum(): Promise<Meldezeitraum[]> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?period=current`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: err.message,
                    level: Levels.ERROR,
                });
                FetchUtils.defaultResponseHandler(err);
            });
    },
    getUpcomingMeldezeitraueme(): Promise<Meldezeitraum[]> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?period=future`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: err.message,
                    level: Levels.ERROR,
                });
                FetchUtils.defaultResponseHandler(err);
            });
    },
    getPassedMeldezeitraueme(): Promise<Meldezeitraum[]> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?period=past`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: err.message,
                    level: Levels.ERROR,
                });
                FetchUtils.defaultResponseHandler(err);
            });
    },
    getAllMeldezeitraeume(): Promise<Meldezeitraum[]> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: err.message,
                    level: Levels.ERROR,
                });
                FetchUtils.defaultResponseHandler(err);
            });
    },
    deleteMeldezeitraumById(id: string | undefined): Promise<void> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?id=${id}`,
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
