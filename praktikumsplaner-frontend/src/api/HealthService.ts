import { defaultResponseHandler, getGETConfig } from "@/api/FetchUtils";
import { API_BASE } from "@/constants";
import HealthState from "@/types/HealthState";

export function checkHealth(): Promise<HealthState> {
  return fetch(`${API_BASE}/actuator/health`, getGETConfig())
    .then((response) => {
      defaultResponseHandler(response);
      return response.json();
    })
    .catch((err) => {
      defaultResponseHandler(err);
    });
}
