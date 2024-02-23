import { Levels } from "@/api/Error";
import FetchUtils from "@/api/FetchUtils";
import { API_BASE, PRAKTIKUMSSTELLE_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import Praktikumsstelle from "@/types/Praktikumsstelle";

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
                    message: "☑ Praktikumsstelle erfolgreich gemeldet",
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
                    message: "☑ Praktikumsstelle erfolgreich gemeldet",
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
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Praktikumsstelle erfolgreich gemeldet",
                    level: Levels.SUCCESS,
                });
                return response.json();
            } else {
                FetchUtils.defaultResponseHandler(response);
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
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Praktikumsstelle erfolgreich gemeldet",
                    level: Levels.SUCCESS,
                });
                return response.json();
            } else {
                FetchUtils.defaultResponseHandler(response);
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
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Zuweisung erfolgreich",
                    level: Levels.SUCCESS,
                });
                return response.json();
            } else {
                FetchUtils.defaultResponseHandler(response);
            }
        });
    },
    unassignNwk(stellenId: string): Promise<Praktikumsstelle> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/${stellenId}`,
            FetchUtils.getPATCHConfig({})
        ).then((response) => {
            if (response.ok) {
                useSnackbarStore().showMessage({
                    message: "☑ Zuweisung aufgehoben",
                    level: Levels.SUCCESS,
                });
                return response.json();
            } else {
                FetchUtils.defaultResponseHandler(response);
            }
        });
    },
    deletePraktikumsstelle(stelle: Praktikumsstelle): Promise<void> {
        if (isAusbildunsPraktikumsstelle(stelle)) {
            return fetch(
                `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/ausbildung/${stelle.id}`,
                FetchUtils.getDELETEConfig({})
            ).then((response) => {
                FetchUtils.defaultResponseHandler(response);
                useSnackbarStore().showMessage({
                    message: "☑ Praktikumsstelle erfolgreich gelöscht",
                    level: Levels.SUCCESS,
                });
            });
        } else if (isStudiumsPraktikumsstelle(stelle)) {
            return fetch(
                `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/studium/${stelle.id}`,
                FetchUtils.getDELETEConfig({})
            ).then((response) => {
                FetchUtils.defaultResponseHandler(response);
                useSnackbarStore().showMessage({
                    message: "☑ Praktikumsstelle erfolgreich gelöscht",
                    level: Levels.SUCCESS,
                });
            });
        } else {
            throw new Error("Praktikumsstelle nicht gefunden.");
        }
    },
};

function isStudiumsPraktikumsstelle(stelle: Praktikumsstelle): boolean {
    return stelle.studiengang !== undefined;
}

function isAusbildunsPraktikumsstelle(stelle: Praktikumsstelle): boolean {
    return stelle.ausbildungsrichtung !== undefined;
}
