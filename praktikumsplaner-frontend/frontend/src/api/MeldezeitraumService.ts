import FetchUtils from "@/api/FetchUtils";
import { API_BASE, MELDEZEITRAUM_BASE } from "@/Constants";
import Meldezeitraum from "@/types/Meldezeitraum";
import { useSnackbarStore } from "@/stores/snackbar";
import { Levels } from "@/api/error";

export default class MeldezeitraumService {
    public create(meldezeitraum: Meldezeitraum): Promise<Meldezeitraum> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}`,
            FetchUtils.getPOSTConfig(meldezeitraum)
        )
            .then((response) => {
                useSnackbarStore().showMessage({
                    message: "Meldezeitraum erfolgreich angelegt",
                    level: Levels.INFO,
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
    }
}
