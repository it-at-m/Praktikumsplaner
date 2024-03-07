import type { Ref } from "vue";

import { Levels } from "@/api/Error";
import FetchUtils from "@/api/FetchUtils";
import { API_BASE, MAIL_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import Zeitraum from "@/types/Zeitraum";

export default {
    sendSuccessfulAssignedMails(
        assignmentPeriods: {
            [k: string]: Zeitraum;
        },
        loading: Ref<boolean>
    ): Promise<Praktikumsstelle[]> {
        loading.value = true;
        return fetch(
            `${API_BASE}${MAIL_BASE}/send?assignmentStatus=successful`,
            FetchUtils.getPOSTConfig(assignmentPeriods)
        )
            .then((response) => {
                if (response.ok) {
                    // Prüfen, ob der Statuscode OK (200) ist
                    useSnackbarStore().showMessage({
                        message: "☑ Alle Mails wurden erfolgreich versendet.",
                        level: Levels.SUCCESS,
                    });
                    return [];
                } else {
                    return response.json().then((praktikumsplaetze) => {
                        useSnackbarStore().showMessage({
                            message: "Fehler beim Versenden einiger Mails.",
                            level: Levels.ERROR,
                        });
                        return praktikumsplaetze; // Liste der betroffenen Praktikumsplätze zurückgeben
                    });
                }
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: err.message,
                    level: Levels.ERROR,
                });
                return Promise.reject(err); // Fehler zurückgeben
            })
            .finally(() => {
                loading.value = false;
            });
    },
};
