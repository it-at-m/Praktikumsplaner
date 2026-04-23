import { defaultResponseHandler, getGETConfig } from "@/api/FetchUtils";
import { API_BASE } from "@/constants";

export interface Info {
  application: Application;
}

export interface Application {
  name: string;
  version: string;
}

export function getInfo(): Promise<Info> {
  return fetch(`${API_BASE}/actuator/info`, getGETConfig()).then((response) => {
    defaultResponseHandler(response);
    return response.json();
  });
}
