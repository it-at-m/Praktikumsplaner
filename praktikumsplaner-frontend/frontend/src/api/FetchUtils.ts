import { ApiError, Levels } from "@/api/Error";
import { useSnackbarStore } from "@/stores/snackbar";
import { useUserErrorStore } from "@/stores/user-error";

export default class FetchUtils {
    /**
     * Liefert eine default GET-Config für fetch
     */
    static getGETConfig(): RequestInit {
        return {
            headers: this.getHeaders(),
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }

    /**
     * Liefert eine default POST-Config für fetch
     * @param body Optional zu übertragender Body
     */
    static getPOSTConfig(body: unknown): RequestInit {
        return {
            method: "POST",
            body: this.getBody(body),
            headers: FetchUtils.getHeaders(),
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }

    /**
     * Liefert eine default PUT-Config für fetch
     * @param body Optional zu übertragender Body
     */
    static getPUTConfig(body: unknown): RequestInit {
        const headers = FetchUtils.getHeaders();
        return {
            method: "PUT",
            body: this.getBody(body),
            headers,
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }

    /**
     * Liefert eine default PATCH-Config für fetch
     * @param body Optional zu übertragender Body
     */
    static getPATCHConfig(body: unknown): RequestInit {
        const headers = FetchUtils.getHeaders();
        return {
            method: "PATCH",
            body: this.getBody(body),
            headers,
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }
    /**
     * Liefert eine default POST-Config für fetch
     * @param body Optional zu übertragender Body
     */
    static getDELETEConfig(body: unknown): RequestInit {
        const headers = FetchUtils.getHeaders();
        return {
            method: "DELETE",
            body: this.getBody(body),
            headers,
            mode: "cors",
            credentials: this.getCredentials(),
            redirect: "manual",
        };
    }

    /**
     * Liefert eine default DELETE-Config für fetch ohne Body
     */
    static getDELETEConfigNoBody(): RequestInit {
        return {
            method: "DELETE",
            headers: this.getHeaders(),
            mode: "cors",
            credentials: this.getCredentials(),
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
    static defaultResponseHandler(
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
            } else if (this.isStatusInput(response)) {
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
            } else if (this.isStatusServer(response)) {
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
    private static isStatusServer(response: Response): boolean {
        return response.status >= 500 && response.status <= 599;
    }

    /**
     * Prüft, ob der Status der Response auf einen Input-Fehler hinweist.
     * @param response Die Response, die geprüft werden soll.
     * @returns {boolean} true, wenn der Status auf einen Input-Fehler hinweist, sonst false.
     */
    private static isStatusInput(response: Response): boolean {
        return response.status >= 400 && response.status <= 499;
    }

    /**
     *  Baut den Header fuer den Request auf
     * @returns {Headers}
     */
    static getHeaders(): Headers {
        const headers = new Headers({
            "Content-Type": "application/json",
        });
        const csrfCookie = this._getXSRFToken();
        if (csrfCookie !== "") {
            headers.append("X-XSRF-TOKEN", csrfCookie);
        }
        return headers;
    }

    /**
     * Liefert den XSRF-TOKEN zurück.
     * @returns {string|string}
     */
    static _getXSRFToken(): string {
        const help = document.cookie.match(
            "(^|;)\\s*" + "XSRF-TOKEN" + "\\s*=\\s*([^;]+)"
        );
        return (help ? help.pop() : "") as string;
    }

    /**
     * @returns {any}
     */
    static getBody(body: unknown): string | undefined {
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
    private static getCredentials(): RequestCredentials {
        return import.meta.env.MODE === "developmentSecurity" ||
            import.meta.env.MODE === "development"
            ? "include"
            : "same-origin";
    }
}
