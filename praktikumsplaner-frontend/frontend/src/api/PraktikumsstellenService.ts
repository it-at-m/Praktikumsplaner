import type { Ref } from "vue";

import { Levels } from "@/api/Error";
import FetchUtils from "@/api/FetchUtils";
import { API_BASE, PRAKTIKUMSSTELLE_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import Praktikumsstelle from "@/types/Praktikumsstelle";

export default {
    uploadStudiumsPraktikumsstelle(
        praktikumsstelle: Praktikumsstelle,
        loading: Ref<boolean>
    ): Promise<Praktikumsstelle> {
        loading.value = true;
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/studium`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                if (response.ok) {
                    useSnackbarStore().showMessage({
                        message: "☑ Praktikumsstelle erfolgreich gemeldet",
                        level: Levels.SUCCESS,
                    });
                    return response.json();
                }
            })
            .finally(() => {
                loading.value = false;
            });
    },
    uploadStudiumsPraktikumsstelleWithMeldezeitraum(
        praktikumsstelle: Praktikumsstelle,
        loading: Ref<boolean>
    ): Promise<Praktikumsstelle> {
        loading.value = true;
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/studium/ausbildungsleitung`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                if (response.ok) {
                    useSnackbarStore().showMessage({
                        message: "☑ Praktikumsstelle erfolgreich gemeldet",
                        level: Levels.SUCCESS,
                    });
                    return response.json();
                }
            })
            .finally(() => {
                loading.value = false;
            });
    },
    uploadAusbildungsPraktikumsstelle(
        praktikumsstelle: Praktikumsstelle,
        loading: Ref<boolean>
    ): Promise<Praktikumsstelle> {
        loading.value = true;
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/ausbildung`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
        )
            .then((response) => {
                if (response.ok) {
                    useSnackbarStore().showMessage({
                        message: "☑ Praktikumsstelle erfolgreich gemeldet",
                        level: Levels.SUCCESS,
                    });
                    return response.json();
                } else {
                    FetchUtils.defaultResponseHandler(response);
                }
            })
            .finally(() => {
                loading.value = false;
            });
    },
    uploadAusbildungsPraktikumsstelleWithMeldezeitraum(
        praktikumsstelle: Praktikumsstelle,
        loading: Ref<boolean>
    ): Promise<Praktikumsstelle> {
        loading.value = true;
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/ausbildung/ausbildungsleitung`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
        )
            .then((response) => {
                if (response.ok) {
                    useSnackbarStore().showMessage({
                        message: "☑ Praktikumsstelle erfolgreich gemeldet",
                        level: Levels.SUCCESS,
                    });
                    return response.json();
                } else {
                    FetchUtils.defaultResponseHandler(response);
                }
            })
            .finally(() => {
                loading.value = false;
            });
    },
    getAllPraktikumsstellenInSpecificMeldezeitraum(
        meldezeitraum: string,
        loading: Ref<boolean> | undefined
    ): Promise<Map<string, Praktikumsstelle[]>> {
        if (loading) {
            loading.value = true;
        }
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}?meldezeitraum=${meldezeitraum}`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .finally(() => {
                if (loading) {
                    loading.value = false;
                }
            });
    },
    assignNwk(
        stellenId: string,
        nwkId: string | undefined,
        loading: Ref<boolean>
    ): Promise<Praktikumsstelle> {
        loading.value = true;
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/${stellenId}?nwkId=${nwkId}`,
            FetchUtils.getPATCHConfig({})
        )
            .then((response) => {
                if (response.ok) {
                    useSnackbarStore().showMessage({
                        message: "☑ Zuweisung erfolgreich",
                        level: Levels.SUCCESS,
                    });
                    return response.json();
                } else {
                    FetchUtils.defaultResponseHandler(response);
                }
            })
            .finally(() => {
                loading.value = false;
            });
    },
    unassignNwk(
        stellenId: string,
        loading: Ref<boolean>
    ): Promise<Praktikumsstelle> {
        loading.value = true;
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/${stellenId}`,
            FetchUtils.getPATCHConfig({})
        )
            .then((response) => {
                if (response.ok) {
                    useSnackbarStore().showMessage({
                        message: "☑ Zuweisung aufgehoben",
                        level: Levels.SUCCESS,
                    });
                    return response.json();
                } else {
                    FetchUtils.defaultResponseHandler(response);
                }
            })
            .finally(() => {
                loading.value = false;
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
            throw new Error(
                "Praktikumsstelle konnte nicht nach Typ kategorisiert werden."
            );
        }
    },
};

function isStudiumsPraktikumsstelle(stelle: Praktikumsstelle): boolean {
    return stelle.studiengang !== undefined;
}

function isAusbildunsPraktikumsstelle(stelle: Praktikumsstelle): boolean {
    return stelle.ausbildungsrichtung !== undefined;
}
