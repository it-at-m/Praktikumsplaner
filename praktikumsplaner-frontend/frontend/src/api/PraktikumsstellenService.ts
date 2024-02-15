import FetchUtils from "@/api/FetchUtils";
import { API_BASE, PRAKTIKUMSSTELLE_BASE } from "@/constants";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/Error";

export default {
    uploadStudiumsPraktikumsstelle(
        praktikumsstelle: Praktikumsstelle
    ): Promise<Praktikumsstelle> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/studium`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Praktikumsstelle erfolgreich angelegt",
                    level: Levels.SUCCESS,
                });
                return response.json();
            }
        });
    },
    uploadStudiumsPraktikumsstelleWithMeldezeitraum(
        praktikumsstelle: Praktikumsstelle
    ): Promise<Praktikumsstelle> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/studium/ausbildungsleitung`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Praktikumsstelle erfolgreich angelegt",
                    level: Levels.SUCCESS,
                });
                return response.json();
            }
        });
    },
    uploadAusbildungsPraktikumsstelle(
        praktikumsstelle: Praktikumsstelle
    ): Promise<Praktikumsstelle> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/ausbildung`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Praktikumsstelle erfolgreich angelegt",
                    level: Levels.SUCCESS,
                });
                return response.json();
            }
        });
    },
    uploadAusbildungsPraktikumsstelleWithMeldezeitraum(
        praktikumsstelle: Praktikumsstelle
    ): Promise<Praktikumsstelle> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/ausbildung/ausbildungsleitung`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Praktikumsstelle erfolgreich angelegt",
                    level: Levels.SUCCESS,
                });
                return response.json();
            }
        });
    },
    getAllPraktikumsstellenInSpecificMeldezeitraum(
        meldezeitraum: string
    ): Promise<Map<string, Praktikumsstelle[]>> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}?meldezeitraum=${meldezeitraum}`,
            FetchUtils.getGETConfig()
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            return response.json();
        });
    },
    assignNwk(
        stellenId: string,
        nwkId: string | undefined
    ): Promise<Praktikumsstelle> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/${stellenId}?nwkId=${nwkId}`,
            FetchUtils.getPATCHConfig({})
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Zuweisung erfolgreich",
                    level: Levels.SUCCESS,
                });
                return response.json();
            }
        });
    },
    unassignNwk(stellenId: string): Promise<Praktikumsstelle> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/${stellenId}`,
            FetchUtils.getPATCHConfig({})
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Zuweisung erfolgreich aufgehoben",
                    level: Levels.SUCCESS,
                });
                return response.json();
            }
        });
    },
};
