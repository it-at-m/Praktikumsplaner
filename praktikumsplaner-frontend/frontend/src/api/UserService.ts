import type UserInfo from "@/types/UserInfo";

import FetchUtils from "@/api/FetchUtils";
import { API_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";

export class UserService {
    private static URL: string = API_BASE + "/api/sso/userinfo/";

    getPermissions(): Promise<UserInfo> {
        return fetch(UserService.URL, FetchUtils.getGETConfig())
            .then((response) => response.json())
            .catch((err) => {
                const snackbarStore = useSnackbarStore();
                snackbarStore.showMessage(err);
                FetchUtils.defaultResponseHandler(
                    err,
                    "Berechtigungen konnten nicht erfasst werden"
                );
            });
    }
}
