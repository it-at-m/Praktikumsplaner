# Security concept

For the security concept, we identified the [personas](#personas) listed below.
Then we determined which persona is allowed to use which feature.
The results of this process are listed under [features](#features-and-the-personas-that-can-use-it)

## Personas

- [örtliche Ausbildungsleitung](../../../glossary.md#ortliche-ausbildungsleitung)
- [örtliche Ausbilderin](../../../glossary.md#ortliche-ausbilderin)
- [Nwk](../../../glossary.md#nwk)

## Features and the Personas that can use it

- [Nachwuchskräfte](../../../features/Nachwuchskraefte.md) (örtliche Ausbildungsleitung)
- [Meldezeitraum](../../../features/meldezeitraum.md) (örtliche Ausbildungsleitung)
- [Submission örtl. Ausbilder](../../../features/Praktikumsplaetze.md#submission-örtliche-ausbilder) (örtliche Ausbilderin)
- [Submission örtl. Ausbildungsleitung](../../../features/Praktikumsplaetze.md#submission-örtliche-ausbildungsleitung) (örtliche Ausbildungsleitung)
- [Assignment](../../../features/Zuweisung.md) (örtliche Ausbildungsleitung)

## Technical Implementation
On the frontend side, the security features are controlled by the `VITE_APP_SECURITY` in the `.env` files.
By default, security features are enabled in `production` and disabled in `development`.

On the frontend side, we have a `security composable`.
This composable has several methods:
 - checkForRole(string): this method checks wether the logged-in user has the provided role
 - checkForAnyRole(string[]): this method checks wether the logged-in user has any of the provided roles
 - checkForAllRoles(string[]): this method checks wether the logged-in user has all provided  roles
 - isAusbildungsleitung(): This method is used for checking wether the logged-in user is a [Ausbildungsleitung](../../../glossary.md#ortliche-ausbildungsleitung)

Those methods are used in `v-if` directives to control what is shown (rendered) to the user.
As this is controlled on frontend side in the browser a user could possibly bypass this security
so this is not to be meant to prevent a user doing things is not allowed, but more a way
to guide the user in the application and only show him things he is allowed to do and not confuse
him with many error messages due to missing privileges.