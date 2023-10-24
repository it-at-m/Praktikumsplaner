# Create Meldezeitraum
This file contains the usability test tasks written for the feature [Create Meldezeitraum](../features/meldezeitraum.md).

## Create a valid Meldezeitraum
Try to create a Meldezeitraum with a Zeitraumname of `Testzeitraum`, a Startzeitpunkt of `01.03.2020` and a Endzeitpunkt of `01.06.2020`.
This task is successful when you see a blue badge telling `Der Meldezeitraum wurde erfolgreich angelegt`.

## One day long Meldezeitraum
Try to create a Meldezeitraum which starts on `10.10.2020` and also ends on `10.10.2020`.

## Meldezeitraum without title
Try to create a Meldezeitraum without a name.

## Meldezeitraum wrong Start and End
Try to create a Meldezeitraum with a Startzeitpunkt of `10.10.2020` and an Endzeitpunkt of `10.09.2020`.