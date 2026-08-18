import { valueToNameAusbildungsjahr } from "@/types/Ausbildungsjahr";
import { findBildungsrichtung, isAusbildung, isStudium } from "@/types/Bildungsrichtung";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { valueToNameStudiensemester } from "@/types/Studiensemester";


export function useTextGenerator() {
  function getPraktikumsstellenDescription(
    stelle: Praktikumsstelle | undefined | null
  ): string {
    let cardText = "";
    if (!stelle) return cardText;

    const richtung = findBildungsrichtung(stelle.richtung ?? "");

    if (isStudium(richtung)) {
      cardText += getStudiumsPraktikumsstellenDescription(stelle);
    } else if (isAusbildung(richtung)) {
      cardText += getAusbildungsPraktikumsstellenDescription(stelle);
    }

    return cardText;
  }

  function getPraktikumsstellenDetailDescription(
    stelle: Praktikumsstelle | undefined | null
  ): string {
    let cardText = "";
    if (!stelle) return cardText;
    if (!stelle.dringlichkeit) return cardText;

    const richtung = findBildungsrichtung(stelle.richtung ?? "");
    const dringlichkeit =
      stelle.dringlichkeit.charAt(0).toUpperCase() +
      stelle.dringlichkeit.slice(1).toLowerCase();
    cardText += "Dringlichkeit: " + dringlichkeit + "\n";
    cardText += "Programmierkenntnisse: " + (stelle.programmierkenntnisse ? "Ja" : "Nein") + "\n";

    if (stelle.ausbildungsjahr && stelle.ausbildungsjahr.length > 0) {
      cardText +=
        "Ausbildungsjahr: " + getAusbildungsjahreString(stelle) + "\n";
    } else if (stelle.studiensemester && stelle.studiensemester.length > 0) {
      cardText += "Semester: " + getStudiumssemesterString(stelle) + "\n";
    }

    if (isAusbildung(richtung)) {
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

  function getAusbildungsPraktikumsstellenDescription(
    stelle: Praktikumsstelle
  ): string {
    let cardText = "";
    cardText += "Richtung: " + stelle.richtung + "\n";
    if (stelle.ausbildungsjahr) {
      cardText +=
        "Ausbildungsjahr: " + getAusbildungsjahreString(stelle) + "\n";
    }
    if (stelle.planstelleVorhanden) {
      cardText += "Planstelle vorhanden: JA\n";
    }
    return cardText;
  }

  function getStudiumsPraktikumsstellenDescription(
    stelle: Praktikumsstelle
  ): string {
    let cardText = "";
    cardText += "Richtung: " + stelle.richtung + "\n";
    if (stelle.studiensemester) {
      cardText += "Semester: " + getStudiumssemesterString(stelle) + "\n";
    }
    if (stelle.planstelleVorhanden) {
      cardText += "Planstelle vorhanden: JA\n";
    }
    return cardText;
  }

  function getAusbildungsjahreString(stelle: Praktikumsstelle): string {
    let text = "";
    if (stelle.ausbildungsjahr) {
      stelle.ausbildungsjahr.sort();
      for (let i = 0; i < stelle.ausbildungsjahr.length - 1; i++) {
        text +=
          valueToNameAusbildungsjahr(stelle.ausbildungsjahr[i]) + ", ";
      }
      text +=
        valueToNameAusbildungsjahr(
          stelle.ausbildungsjahr[stelle.ausbildungsjahr.length - 1]
        );
    }
    return text;
  }

  function getStudiumssemesterString(stelle: Praktikumsstelle): string {
    let text = "";
    if (stelle.studiensemester) {
      stelle.studiensemester.sort();
      for (let i = 0; i < stelle.studiensemester.length - 1; i++) {
        text +=
          valueToNameStudiensemester(stelle.studiensemester[i]) + ", ";
      }
      text +=
        valueToNameStudiensemester(
          stelle.studiensemester[stelle.studiensemester.length - 1]
        );
    }
    return text;
  }

  return {
    getPraktikumsstellenDescription,
    getPraktikumsstellenDetailDescription,
  };
}
