import { ApiError, Levels } from "@/api/Error";

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
    // eslint-disable-next-line
    static getPOSTConfig(body: any): RequestInit {
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
     * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
     * als "If-Match"-Header mitgesetzt.
     * @param body Optional zu übertragender Body
     */
    // eslint-disable-next-line
    static getPUTConfig(body: any): RequestInit {
        const headers = FetchUtils.getHeaders();
        if (body.version) {
            headers.append("If-Match", body.version);
        }
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
     * In dieser wird, wenn vorhanden, die Version der zu aktualisierenden Entität
     * als "If-Match"-Header mitgesetzt.
     * @param body Optional zu übertragender Body
     */
    // eslint-disable-next-line
    static getPATCHConfig(body: any):RequestInit {
        const headers = FetchUtils.getHeaders();
        if (body.version !== undefined) {
            headers.append("If-Match", body.version);
        }
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
     * Deckt das Default-Handling einer Response ab. Dazu zählt:
     *
     * - Fehler bei fehlenden Berechtigungen --> HTTP 403
     * - Reload der App bei Session-Timeout --> HTTP 3xx
     * - Default-Fehler bei allen HTTP-Codes !2xx
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
            }
            throw new ApiError({
                level: Levels.WARNING,
                message: errorMessage,
            });
        }
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
