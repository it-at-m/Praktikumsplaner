import { ApiError, Levels } from "@/api/Error";
import { useSnackbarStore } from "@/stores/snackbar";
import { useUserErrorStore } from "@/stores/user-error";

/**
 * Liefert eine default GET-Config für fetch
 */
export function getGETConfig(): RequestInit {
    return {
        headers: getHeaders(),
        mode: "cors",
        credentials: getCredentials(),
        redirect: "manual",
    };
}

/**
 * Liefert eine default POST-Config für fetch
 * @param body Optional zu übertragender Body
 */
export function getPOSTConfig(body: unknown): RequestInit {
    return {
        method: "POST",
        body: getBody(body),
        headers: getHeaders(),
        mode: "cors",
        credentials: getCredentials(),
        redirect: "manual",
    };
}

/**
 * Liefert eine default PUT-Config für fetch
 * @param body Optional zu übertragender Body
 */
export function getPUTConfig(body: unknown): RequestInit {
    const headers = getHeaders();
    return {
        method: "PUT",
        body: getBody(body),
        headers,
        mode: "cors",
        credentials: getCredentials(),
        redirect: "manual",
    };
}

/**
 * Liefert eine default PATCH-Config für fetch
 * @param body Optional zu übertragender Body
 */
export function getPATCHConfig(body: unknown): RequestInit {
    const headers = getHeaders();
    return {
        method: "PATCH",
        body: getBody(body),
        headers,
        mode: "cors",
        credentials: getCredentials(),
        redirect: "manual",
    };
}

/**
 * Liefert eine default POST-Config für fetch
 * @param body Optional zu übertragender Body
 */
export function getDELETEConfig(body: unknown): RequestInit {
    const headers = getHeaders();
    return {
        method: "DELETE",
        body: getBody(body),
        headers,
        mode: "cors",
        credentials: getCredentials(),
        redirect: "manual",
    };
}

/**
 * Liefert eine default DELETE-Config für fetch ohne Body
 */
export function getDELETEConfigNoBody(): RequestInit {
    return {
        method: "DELETE",
        headers: getHeaders(),
        mode: "cors",
        credentials: getCredentials(),
        redirect: "manual",
    };
}

/**
 * Deckt das Default-Handling einer Response ab. Dazu zählt:
 *
 * - Fehler bei fehlenden Berechtigungen --> HTTP 403
 * - Reload der App bei Session-Timeout --> HTTP 3xx
 * - Fehler bei unbekannten Problemen --> alle anderen error Codes
 *
 * @param response Die response aus fetch-Befehl die geprüft werden soll.
 * @param errorMessage Die Fehlermeldung, welche bei einem HTTP-Code != 2xx angezeigt werden soll.
 */
export function defaultResponseHandler(
    response: Response,
    errorMessage = "Es ist ein unbekannter Fehler aufgetreten."
): void {
    if (!response.ok) {
        if (response.status === 403) {
            throw new ApiError({
                level: Levels.ERROR,
                message: `Sie haben nicht die nötigen Rechte um diese Aktion durchzuführen.`,
            });
        } else if (response.type === "opaqueredirect") {
            location.reload();
        } else if (isStatusInput(response)) {
            response.body
                ?.getReader()
                .read()
                .then((result) => {
                    const decoder = new TextDecoder("utf-8");
                    const message = decoder.decode(result.value);
                    useUserErrorStore().showUserError({
                        title: "Fehlerhafte Eingaben",
                        message: message,
                    });
                    throw new ApiError({
                        message: message,
                        level: Levels.ERROR,
                    });
                });
        } else if (isStatusServer(response)) {
            useSnackbarStore().showMessage({
                message:
                    "Serverfehler. Bitte versuchen Sie es später erneut, oder wenden Sie sich an die Administration.",
                level: Levels.ERROR,
            });
            throw new ApiError({
                message: errorMessage,
                level: Levels.ERROR,
            });
        } else {
            useSnackbarStore().showMessage({
                message: errorMessage,
                level: Levels.WARNING,
            });
            throw new ApiError({
                message: errorMessage,
                level: Levels.WARNING,
            });
        }
    }
}

/**
 * Prüft, ob der Status der Response auf einen Server-Fehler hinweist.
 * @param response Die Response, die geprüft werden soll.
 * @returns {boolean} true, wenn der Status auf einen Server-Fehler hinweist, sonst false.
 */
function isStatusServer(response: Response): boolean {
    return response.status >= 500 && response.status <= 599;
}

/**
 * Prüft, ob der Status der Response auf einen Input-Fehler hinweist.
 * @param response Die Response, die geprüft werden soll.
 * @returns {boolean} true, wenn der Status auf einen Input-Fehler hinweist, sonst false.
 */
function isStatusInput(response: Response): boolean {
    return response.status >= 400 && response.status <= 499;
}

/**
 *  Baut den Header fuer den Request auf
 * @returns {Headers}
 */
export function getHeaders(): Headers {
    const headers = new Headers({
        "Content-Type": "application/json",
    });
    const csrfCookie = _getXSRFToken();
    if (csrfCookie !== "") {
        headers.append("X-XSRF-TOKEN", csrfCookie);
    }
    return headers;
}

/**
 * Liefert den XSRF-TOKEN zurück.
 * @returns {string|string}
 */
export function _getXSRFToken(): string {
    const help = document.cookie.match(
        "(^|;)\\s*" + "XSRF-TOKEN" + "\\s*=\\s*([^;]+)"
    );
    return (help ? help.pop() : "") as string;
}

/**
 * @returns {any}
 */
export function getBody(body: unknown): string | undefined {
    if (!body) {
        return undefined;
    } else if (typeof body == "string") {
        return body;
    } else {
        return JSON.stringify(body);
    }
}

/**
 * Gibt an, wie im derzeit aktiven Modus mit den Credentials umzugehen ist.
 * Liefert im Development-Modus (npm run serve) den Wert "include" für Request.credentials,
 * damit die Credentials auch beim Zugriff von localhost:8081 auf localhost:8082 übertragen werden.
 * Im Production-Modus (npm run build) sollen die Credentials nur dann übertragen werden,
 * wenn der Zugriff auf den selben Host erfolgt (Request.credentials = "same-origin").
 * @private Wird nur für die Erstellung der GET/POST/PUT/PATCH/DELETE-Configs benötigt.
 */
function getCredentials(): RequestCredentials {
    return import.meta.env.MODE === "developmentSecurity" ||
        import.meta.env.MODE === "development"
        ? "include"
        : "same-origin";
}
