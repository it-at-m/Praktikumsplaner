import FetchUtils from "@/api/FetchUtils";
import { API_BASE, NWK_BASE } from "@/Constants";
import Nwk from "@/types/Nwk";

export default {
    uploadExcelFile(excelDatei: File): Promise<void> {
        // File Reader encodes as Base64
        return this.readString(excelDatei).then((base64string: string) => {
            return fetch(
                `${API_BASE}${NWK_BASE}/import`,
                // Base64 String starts after the comma
                FetchUtils.getPOSTConfig(base64string.split(",")[1])
            )
                .then((response) => {
                    FetchUtils.defaultResponseHandler(response);
                })
                .catch((err) => {
                    FetchUtils.defaultResponseHandler(err);
                });
        });
    },
    readString(excelDatei: File): Promise<string> {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onloadend = () => {
                if (!(typeof reader.result == "string")) {
                    return Promise.reject();
                }
                resolve(reader.result);
            };
            reader.onerror = reject;
            reader.readAsDataURL(excelDatei);
        });
    },
    getAllActiveNWKs(): Promise<Nwk[]> {
        return fetch(
            `${API_BASE}${NWK_BASE}?status=aktiv`,
            FetchUtils.getGETConfig()
        )
            .then((response) => {
                FetchUtils.defaultResponseHandler(response);
                return response.json();
            })
            .catch((err) => {
                FetchUtils.defaultResponseHandler(err);
            });
    },
};
