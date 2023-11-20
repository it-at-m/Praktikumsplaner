import FetchUtils from "@/api/FetchUtils";
import { API_BASE, NWK_BASE, PRAKTIKUMSSTELLE_BASE } from "@/Constants";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";

export default {
    uploadStudiumsPraktikumsstelle(
        praktikumsstelle: Praktikumsstelle
    ): Promise<Praktikumsstelle> {
        praktikumsstelle.dienststelle =
            praktikumsstelle.normalizeDienststelle();
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/studium`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
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
    uploadAusbildungsPraktikumsstelle(
        praktikumsstelle: Praktikumsstelle
    ): Promise<Praktikumsstelle> {
        praktikumsstelle.dienststelle =
            praktikumsstelle.normalizeDienststelle();
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/ausbildung`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
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
    getAllPraktikumsstellen(): Promise<Praktikumsstelle[]> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .catch((err) => {
                FetchUtils.defaultResponseHandler(err);
            });
    },
};
