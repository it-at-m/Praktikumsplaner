import FetchUtils from "@/api/FetchUtils";
import { API_BASE } from "@/Constants";
import { EXCEL_BASE } from "@/Constants";

export default {
    uploadExcelFile(string: string): Promise<void> {
        return fetch(
            `${API_BASE}/api/aa-a-backend-service${EXCEL_BASE}`,
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
