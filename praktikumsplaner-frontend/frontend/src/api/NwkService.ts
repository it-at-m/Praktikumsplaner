import type { Ref } from "vue";

import { Levels } from "@/api/Error";
import FetchUtils from "@/api/FetchUtils";
import { API_BASE, NWK_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import Nwk from "@/types/Nwk";
import NwkCreate from "@/types/NwkCreate";

export default {
    saveNwk(nwk: NwkCreate, loading: Ref<boolean>): Promise<void> {
        loading.value = true;
        return fetch(`${API_BASE}${NWK_BASE}`, FetchUtils.getPOSTConfig(nwk))
            .then((response) => {
                if (response.ok) {
                    useSnackbarStore().showMessage({
                        message: "☑ NWK wurde erfolgreich erstellt.",
                        level: Levels.SUCCESS,
                    });
                } else {
                    FetchUtils.defaultResponseHandler(response);
                }
            })
            .finally(() => {
                loading.value = false;
            });
    },
    uploadExcelFile(excelDatei: File, loading: Ref<boolean>): Promise<void> {
        loading.value = true;
        // File Reader encodes as Base64
        return this.readString(excelDatei).then((base64string: string) => {
            return fetch(
                `${API_BASE}${NWK_BASE}/import`,
                // Base64 String starts after the comma
                FetchUtils.getPOSTConfig(base64string.split(",")[1])
            )
                .then((response) => {
                    if (response.ok) {
                        useSnackbarStore().showMessage({
                            message: "☑ Nachwuchskräfte erfolgreich angelegt.",
                            level: Levels.SUCCESS,
                        });
                    } else {
                        FetchUtils.defaultResponseHandler(response);
                    }
                })
                .finally(() => {
                    loading.value = false;
                });
        });
    },
    readString(excelDatei: File): Promise<string> {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onloadend = () => {
                if (!(typeof reader.result == "string")) {
                    return Promise.reject();
                }
                resolve(reader.result);
            };
            reader.onerror = reject;
            reader.readAsDataURL(excelDatei);
        });
    },
    getAllActiveNwks(loading: Ref<boolean>): Promise<Nwk[]> {
        loading.value = true;
        return fetch(
            `${API_BASE}${NWK_BASE}?status=aktiv`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .finally(() => {
                loading.value = false;
            });
    },
    getAllUnassignedNwks(loading: Ref<boolean> | undefined): Promise<Nwk[]> {
        if (loading !== undefined) {
            loading.value = true;
        }
        return fetch(
            `${API_BASE}${NWK_BASE}?unassigned=true`,
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
    updateNwk(nwk: Nwk, loading: Ref<boolean>): Promise<void> {
        loading.value = true;
        return fetch(`${API_BASE}${NWK_BASE}`, FetchUtils.getPUTConfig(nwk))
            .then((response) => {
                if (response.ok) {
                    useSnackbarStore().showMessage({
                        message:
                            "☑ Nachwuchskraft wurde erfolgreich bearbeitet.",
                        level: Levels.SUCCESS,
                    });
                } else {
                    FetchUtils.defaultResponseHandler(response);
                }
            })
            .finally(() => {
                loading.value = false;
            });
    },
};
