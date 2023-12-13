import Zeitraum from "@/types/Zeitraum";
import { API_BASE, MAIL_BASE } from "@/Constants";
import FetchUtils from "@/api/FetchUtils";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";

export default {
    sendSuccessfulAssignedMails(assignmentPeriods: { [k: string]: Zeitraum }) {
        return fetch(
            `${API_BASE}${MAIL_BASE}/send?assignmentStatus=successful`,
            FetchUtils.getPOSTConfig(assignmentPeriods)
        )
            .then((response) => {
                useSnackbarStore().showMessage({
                    message: "☑ Versenden der Mails war erfolgreich.",
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
};
