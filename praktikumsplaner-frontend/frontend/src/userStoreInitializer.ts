import { UserService } from "@/api/UserService";
import { useUserStore } from "@/stores/user";

export async function initializeUserStore() {
    const userService = new UserService();
    const userStore = useUserStore();
    if (userStore.username) {
        return;
    }

    await userService.getPermissions().then((userinfo) => {
        userStore.setUsername(userinfo.name);
        if (userinfo.user_roles) {
            userStore.setRoles(userinfo.user_roles);
        }
    });
}
