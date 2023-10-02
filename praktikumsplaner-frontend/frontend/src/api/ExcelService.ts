import FetchUtils from "@/api/FetchUtils";
import { API_BASE } from "@/Constants";
import { EXCEL_BASE } from "@/Constants";

export default {
    uploadExcelFile(file: File): Promise<void> {
        return fetch(`${API_BASE}${EXCEL_BASE}`, FetchUtils.getPOSTConfig(file))
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
            })
            .catch((err) => {
                FetchUtils.defaultResponseHandler(err);
            });
    },
};
