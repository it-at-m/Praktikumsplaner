import type { Ref } from "vue";

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
    getCurrentMeldezeitraum(
        loading: Ref<boolean> | undefined
    ): Promise<Meldezeitraum[]> {
        if (loading !== undefined) {
            loading.value = true;
        }
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?period=current`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .finally(() => {
                if (loading !== undefined) {
                    loading.value = false;
                }
            });
    },
    getUpcomingMeldezeitraueme(
        loading: Ref<boolean> | undefined
    ): Promise<Meldezeitraum[]> {
        if (loading !== undefined) {
            loading.value = true;
        }
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?period=future`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .finally(() => {
                if (loading !== undefined) {
                    loading.value = false;
                }
            });
    },
    getPassedMeldezeitraueme(
        loading: Ref<boolean> | undefined
    ): Promise<Meldezeitraum[]> {
        if (loading !== undefined) {
            loading.value = true;
        }
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}?period=past`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .finally(() => {
                if (loading !== undefined) {
                    loading.value = false;
                }
            });
    },
    getAllMeldezeitraeume(
        loading: Ref<boolean> | undefined
    ): Promise<Meldezeitraum[]> {
        if (loading !== undefined) {
            loading.value = true;
        }
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .finally(() => {
                if (loading !== undefined) {
                    loading.value = false;
                }
            });
    },
    deleteMeldezeitraumById(
        id: string | undefined,
        loading: Ref<boolean>
    ): Promise<void> {
        loading.value = true;
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
            })
            .finally(() => {
                loading.value = false;
            });
    },
};
