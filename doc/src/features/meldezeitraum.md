# Meldezeitraum

## Create Meldezeitraum
On the page `[app-url]/#/meldezeitraeume` it is possible to create a [Meldezeitraum](../glossary.md#meldezeitraum)
by clicking on the button labeled `+ Meldezeitraum Anlegen`.

After clicking a dialog is opening up, where the user can enter a name to
identify the [Meldezeitraum](../glossary.md#meldezeitraum) and select a Startzeitpunkt (Startdate) and an Endzeitpunkt (Enddate).
These dates are validated so that the Startzeitpunkt cannot be a later or the same date as the
Endzeitpunkt. Similarly, the Endzeitpunkt cannot be earlier than the Startzeitpunkt.
The Name is also validated and must not be longer than 255 characters.

When clicking on `Anlegen` the application tries to save the [Meldezeitraum](../glossary.md#meldezeitraum) and if it was
successful a success message will be displayed. Otherwise, an errormessage will be displayed.
The dialog will be closed and reset either way.

When clicking on `Schließen` the dialog will be closed and reset, so when you open the dialog again
you get a clean dialog.