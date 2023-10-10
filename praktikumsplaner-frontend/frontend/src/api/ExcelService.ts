import FetchUtils from "@/api/FetchUtils";
import { API_BASE, NWK_BASE } from "@/Constants";

export default {
    uploadExcelFile(string: string): Promise<void> {
        return fetch(
            `${API_BASE}/api/aa-a-backend-service${NWK_BASE}/import`,
            FetchUtils.getPOSTConfigString(string)
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
            })
            .catch((err) => {
                FetchUtils.defaultResponseHandler(err);
            });
    },
};
