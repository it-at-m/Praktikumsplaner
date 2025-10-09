import { Levels } from "@/api/Error";
import { getPOSTConfig } from "@/api/FetchUtils";
import { API_BASE, MAIL_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import Zeitraum from "@/types/Zeitraum";

export default {
    sendSuccessfulAssignedMails(
        assignmentPeriods: Record<string, Zeitraum>
    ): Promise<Praktikumsstelle[]> {
        return fetch(
            `${API_BASE}${MAIL_BASE}/send?assignmentStatus=successful`,
            getPOSTConfig(assignmentPeriods)
        )
            .then((response) => {
                if (response.status === 200) {
                    // Prüfen, ob der Statuscode OK (200) ist
                    useSnackbarStore().showMessage({
                        message: "☑ Alle Mails wurden erfolgreich versendet.",
                        level: Levels.SUCCESS,
                    });
                    return [];
                } else if (response.status === 207) {
                    return response.json().then((praktikumsplaetze) => {
                        useSnackbarStore().showMessage({
                            message: "Fehler beim Versenden einiger Mails.",
                            level: Levels.ERROR,
                        });
                        return praktikumsplaetze; // Liste der betroffenen Praktikumsplätze zurückgeben
                    });
                } else {
                    throw Error(
                        "Ein unbekannter Fehler ist aufgetreten. Bitte an den Service Desk melden."
                    );
                }
            })
            .catch((err) => {
                useSnackbarStore().showMessage({
                    message: err.message,
                    level: Levels.ERROR,
                });
                return Promise.reject(err); // Fehler zurückgeben
            });
    },
};
