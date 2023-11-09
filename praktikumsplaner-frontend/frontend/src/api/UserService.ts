import { API_BASE } from "@/Constants";
import UserInfo from "@/types/UserInfo";
import FetchUtils from "@/api/FetchUtils";

export class UserService {
    private static URL: string = API_BASE + "/api/sso/userinfo/";

    getPermissions(): Promise<UserInfo> {
        return fetch(UserService.URL, FetchUtils.getGETConfig())
            .then((response) => response.json())
            .catch((err) => {
                FetchUtils.defaultResponseHandler(
                    err,
                    "Berechtigungen konnten nicht erfasst werden"
                );
            });
    }
}
