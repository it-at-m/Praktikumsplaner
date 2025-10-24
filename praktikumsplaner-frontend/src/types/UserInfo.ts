export default interface UserInfo {
    email_verified: boolean;

    family_name: string;

    given_name: string;

    preferred_username: string;

    sub: string;

    authorities: string[];

    name: string;

    // Key isn't fix and depends on client config on sso
    resource_access?: Record<string, {
            roles: string[];
        }>;
}
