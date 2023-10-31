export const API_BASE: string | undefined = import.meta.env
    .VITE_VUE_APP_API_URL;
export const ROUTER_BASE: string = import.meta.env.BASE_URL;

const API_BACKEND_BASE = "/api/backend-service";
export const NWK_BASE = `${API_BACKEND_BASE}/nachwuchskraft`;
export const MELDEZEITRAUM_BASE = `${API_BACKEND_BASE}/meldezeitraum`;
