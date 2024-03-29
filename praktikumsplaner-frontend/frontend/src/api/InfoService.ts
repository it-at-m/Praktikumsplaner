import FetchUtils from "@/api/FetchUtils";
import { API_BASE } from "@/constants";

export interface Info {
    application: Application;
}

export interface Application {
    name: string;
    version: string;
}

export default class InfoService {
    static getInfo(): Promise<Info> {
        return fetch(
            `${API_BASE}/actuator/info`,
            FetchUtils.getGETConfig()
        ).then((response) => {
            FetchUtils.defaultResponseHandler(response);
            return response.json();
        });
    }
}
