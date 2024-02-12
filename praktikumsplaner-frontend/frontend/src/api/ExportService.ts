import { API_BASE, EXPORT_BASE } from "@/constants";
import FetchUtils from "@/api/FetchUtils";

export default {
    downloadExcelFile(): Promise<void> {
        return fetch(
            `${API_BASE}${EXPORT_BASE}`,
            FetchUtils.getGETConfig()
        ).then((response) => {
            response.text().then((base64) => {
                fetch(
                    `data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,${base64}`
                ).then((response) => {
                    response.blob().then((blob) => {
                        const url = window.URL.createObjectURL(blob);
                        const a = document.createElement("a");
                        a.href = url;
                        a.download = "ITM_IT_POR_EXPORT.xlsx";
                        a.click();
                        window.URL.revokeObjectURL(url);
                    });
                });
            });
        });
    },
};
