import FetchUtils from "@/api/FetchUtils";
import { API_BASE, PRAKTIKUMSSTELLE_BASE } from "@/Constants";
import Praktikumsstelle from "@/types/Praktikumsstelle";

export default {
    uploadPraktikumsstelle(praktikumsstelle: Praktikumsstelle): Promise<void> {
        return fetch(
            `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/create`,
            FetchUtils.getPOSTConfig(praktikumsstelle)
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
            })
            .catch((err) => {
                FetchUtils.defaultResponseHandler(err);
            });
    },
};
