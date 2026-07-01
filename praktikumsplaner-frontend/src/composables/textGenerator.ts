import { valueToNameAusbildungsjahr } from "@/types/Ausbildungsjahr";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { valueToNameStudiensemester } from "@/types/Studiensemester";

export function useTextGenerator() {
  function getPraktikumsstellenDescription(
    stelle: Praktikumsstelle | undefined | null
  ): string {
    let cardText = "";
    if (!stelle) return cardText;
    cardText += "Richtung: " + (stelle.richtung || "") + "\n";

    switch (stelle.art) {
      case "STUDIUM": {
        if (stelle.studiensemester) {
          cardText += "Semester: ";
          stelle.studiensemester.sort();
          for (let i = 0; i < (stelle.studiensemester?.length || 0) - 1; i++) {
            cardText +=
              valueToNameStudiensemester(stelle.studiensemester[i]) + ", ";
          }
          cardText +=
            valueToNameStudiensemester(
              stelle.studiensemester[stelle.studiensemester.length - 1]
            ) + "\n";
        }
        break;
      }
      case "AUSBILDUNG": {
        if (stelle.ausbildungsjahr) {
          cardText += "Ausbildungsjahr: ";
          stelle.ausbildungsjahr.sort();
          for (let i = 0; i < stelle.ausbildungsjahr.length - 1; i++) {
            cardText +=
              valueToNameAusbildungsjahr(stelle.ausbildungsjahr[i]) + ", ";
          }
          cardText +=
            valueToNameAusbildungsjahr(
              stelle.ausbildungsjahr[stelle.ausbildungsjahr.length - 1]
            ) + "\n";
        }
        break;
      }
    }

    if (stelle.planstelleVorhanden) {
      cardText += "Planstelle vorhanden: JA\n";
    }

    return cardText;
  }

  function getPraktikumsstellenDetailDescription(
    stelle: Praktikumsstelle | undefined | null
  ): string {
    let cardText = "";
    if (!stelle) return cardText;
    if (!stelle.dringlichkeit) return cardText;

    const dringlichkeit =
      stelle.dringlichkeit.charAt(0).toUpperCase() +
      stelle.dringlichkeit.slice(1).toLowerCase();
    cardText += "Dringlichkeit: " + dringlichkeit + "\n";
    if (stelle.programmierkenntnisse) {
      cardText += "Programmierkenntnisse: ";
      switch (stelle.programmierkenntnisse) {
        case "true":
          cardText += "Ja" + "\n";
          break;
        case "false":
          cardText += "Nein" + "\n";
          break;
        case "EGAL":
          cardText += "egal" + "\n";
          break;
      }
    }
    if (stelle.art == "AUSBILDUNG") {
      cardText +=
        "Projektarbeit: " + (stelle.projektarbeit ? "Ja" : "Nein") + "\n";
      cardText +=
        "Betreuung minderjähriger NWK Möglich? " +
        (stelle.minderjaehrigMoeglich ? "Ja" : "Nein") +
        "\n";
    }

    cardText +=
      "Ausbilder*in: " +
      stelle.oertlicheAusbilder +
      "\n" +
      "Mailadresse Ausbilder*in: " +
      stelle.email +
      "\n" +
      "Erw. Führungszeugnis: " +
      (stelle.erwFuehrungszeugnisVorhanden ? "Ja" : "Nein") +
      "\n" +
      "Tätigkeiten: " +
      stelle.taetigkeiten +
      "\n";
    if (stelle.wuensche) {
      cardText += "Wünsche: " + stelle.wuensche.split(/\n+ */).join(", ");
    }
    return cardText;
  }

  return {
    getPraktikumsstellenDescription,
    getPraktikumsstellenDetailDescription,
  };
}
