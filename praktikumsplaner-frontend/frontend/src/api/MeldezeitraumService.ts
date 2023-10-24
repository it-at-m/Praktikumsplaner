import FetchUtils from "@/api/FetchUtils";
import {API_BASE, MELDEZEITRAUM_BASE} from "@/Constants";
import Meldezeitraum from "@/types/Meldezeitraum";

export default class MeldezeitraumService {
    public create(meldezeitraum: Meldezeitraum): Promise<Meldezeitraum> {
        return fetch(
            `${API_BASE}${MELDEZEITRAUM_BASE}`,
            FetchUtils.getPOSTConfig(meldezeitraum)
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .catch((err) => {
                FetchUtils.defaultResponseHandler(err);
            });
    }
}
