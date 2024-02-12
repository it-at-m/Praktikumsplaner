import Praktikumsstelle from "@/types/Praktikumsstelle";
import { valueToNameStudiensemester } from "@/types/Studiensemester";
import { valueToNameAusbildungsjahr } from "@/types/Ausbildungsjahr";

export function useTextGenerator() {
    function getPraktikumsstellenCardText(
        stelle: Praktikumsstelle | undefined | null
    ): string {
        let cardText = "";
        if (!stelle) return cardText;

        if (stelle.studiengang) {
            cardText += getStudiumssPraktikumsstellenCardText(stelle);
        } else if (stelle.ausbildungsrichtung) {
            cardText += getAusbildungsPraktikumsstellenCardText(stelle);
        }

        return cardText;
    }

    function getPraktikumsstellenCardDetailText(
        stelle: Praktikumsstelle | undefined | null
    ): string {
        let cardText = "";
        if (!stelle) return cardText;

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
        if (stelle.ausbildungsrichtung) {
            cardText +=
                "Projektarbeit: " +
                (stelle.projektarbeit ? "Ja" : "Nein") +
                "\n";
        }
        cardText +=
            "Ausbilder*in: " +
            stelle.oertlicheAusbilder +
            "\n" +
            "Mailadresse Ausbilder*in: " +
            stelle.email +
            "\n" +
            "Tätigkeiten: " +
            stelle.taetigkeiten;
        return cardText;
    }

    function getAusbildungsPraktikumsstellenCardText(
        stelle: Praktikumsstelle
    ): string {
        let cardText = "";
        cardText += "Ausbildungsrichtung: " + stelle.ausbildungsrichtung + "\n";
        if (stelle.ausbildungsjahr) {
            cardText += "Ausbildungsjahr: ";
            stelle.ausbildungsjahr.sort();
            for (let i = 0; i < stelle.ausbildungsjahr.length - 1; i++) {
                cardText +=
                    valueToNameAusbildungsjahr(stelle.ausbildungsjahr[i]) +
                    ", ";
            }
            cardText +=
                valueToNameAusbildungsjahr(
                    stelle.ausbildungsjahr[stelle.ausbildungsjahr.length - 1]
                ) + "\n";
        }
        return cardText;
    }

    function getStudiumssPraktikumsstellenCardText(
        stelle: Praktikumsstelle
    ): string {
        let cardText = "";
        cardText += "Studiengang: " + stelle.studiengang + "\n";
        cardText += "Semester: ";
        if (stelle.studiensemester) {
            stelle.studiensemester.sort();
            for (
                let i = 0;
                i < (stelle.studiensemester?.length || 0) - 1;
                i++
            ) {
                cardText +=
                    valueToNameStudiensemester(stelle.studiensemester[i]) +
                    ", ";
            }
            cardText +=
                valueToNameStudiensemester(
                    stelle.studiensemester[stelle.studiensemester.length - 1]
                ) + "\n";
        }
        return cardText;
    }

    return { getPraktikumsstellenCardText, getPraktikumsstellenCardDetailText };
}
