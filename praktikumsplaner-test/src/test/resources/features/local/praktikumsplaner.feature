# language: de
Funktionalität: Praktikumsplaner

    # Testet die Praktikumsplaner Webanwendung
  @web @executeLocally
  Szenario: Anmeldung Fehlerhaft
  Angenommen man befindet sich auf der Praktikumsplaner Website
  Wenn Sie "test.user" als Benutzername eingeben
  Wenn Sie "xxx" als Passwort eingeben
  Wenn Sie auf das Augensymbol klicken
  Dann steht das Passwort im Klartext
  Wenn Sie auf Anmelden klicken
  Dann erscheint eine Fehlermeldung
