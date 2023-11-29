# Security concept

For the security concept, we identified the [personas](#personas) listed below.
Then we determined which persona is allowed to use which feature.
The results of this process are listed under [features](#features)

## Personas

- [örtliche Ausbildungsleitung](../../../glossary.md#ortliche-ausbildungsleitung)
- [örtliche Ausbilderin](../../../glossary.md#ortliche-ausbilderin)
- [Nwk](../../../glossary.md#nwk)

## Features

- [Excel-Import](../../../features/ExcelImport.md) (örtliche Ausbildungsleitung)
- [Meldezeitraum](../../../features/meldezeitraum.md) (örtliche Ausbildungsleitung)
- [Meldung örtl. Ausbilder](../../../features/MeldungOertlAusbilder.md) (örtliche Ausbilderin / anyone except Nwk)

## Technical Implementation
On the frontend side, the security features are controlled by the `VITE_APP_SECURITY` in the `.env` files.
By default, security features are enabled in `production` and disabled in `development`.

On the frontend side, we have a `v-security` directive. This directive has two modifiers: `allow` and `restrict`.
`allow` is a whitelisting mechanism, so the role that is used as a parameter with `allow` can see the content displayed underneath the tag.
`restrict` is a blacklisting mechanism, so the role that is used as a parameter with `restrct` cannot see the content displayed underneath the tag.
The reason for black- and whitelisting is that there is the persona [Ausbilder](../../../glossary.md#ortliche-ausbilderin) which is applicable to every person who is not a [Nwk](../../../glossary.md#nwk).
It would be unreasonable to give this role to every employee that is not a [Nwk](../../../glossary.md#nwk), so we decided not to create a role for this, but instead
use this black- and white-listing, depending on the other roles.