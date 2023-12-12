# Meldezeitraum

## Meldezeitraum Overview
The navigation drawer lists the page `Meldezeitraum` when clicking on this link the user is directed to a page
where  you can see all Meldezeiträume that have been created so far.
They are displayed in three different lists.
The first only shows the current [Meldezeitraum](../glossary.md#meldezeitraum).
The second list shows all [Meldezeiträume](../glossary.md#meldezeitraum) that have already ended.
The third list shows all [Meldezeiträume](../glossary.md#meldezeitraum) that have not yet started.
If amy of the lists is empty, a message will be displayed instead.

## Create a Meldezeitraum
On the page `[app-url]/#/meldezeitraum` it is possible to create a [Meldezeitraum](../glossary.md#meldezeitraum).

After clicking, a dialog opens up where the user can enter a name to identify the[Meldezeitraum](../glossary.md#meldezeitraum) and select a Startzeitpunkt (Startdate) and an Endzeitpunkt (Enddate).
These dates are validated so that the Startzeitpunkt cannot be later or the same date as the Endzeitpunkt. Similarly, the Endzeitpunkt cannot be earlier than the Startzeitpunkt.

When clicking on `Speichern`, the application tries to save the [Meldezeitraum](../glossary.md#meldezeitraum) and if it was successful, a success message will be displayed.
Otherwise, an error message will be displayed.
The user will be redirected to the landing page of the application either way.

When clicking the `Zurück` button, the user will be redirected to the landing page.