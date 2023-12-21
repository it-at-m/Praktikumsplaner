import Zeitraum from "@/types/Zeitraum";
import { API_BASE, MAIL_BASE } from "@/Constants";
import FetchUtils from "@/api/FetchUtils";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";
import Praktikumsstelle from "@/types/Praktikumsstelle";

export default {
    sendSuccessfulAssignedMails(assignmentPeriods: {
        [k: string]: Zeitraum;
    }): Promise<Praktikumsstelle[]> {
        return fetch(
            `${API_BASE}${MAIL_BASE}/send?assignmentStatus=successful`,
            FetchUtils.getPOSTConfig(assignmentPeriods)
        )
            .then((response) => {
                if (response.ok) {
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
                        return praktikumsplaetze;
                    });
                }
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: err.message,
                    level: Levels.ERROR,
                });
                return Promise.reject(err);
            });
    },
};
