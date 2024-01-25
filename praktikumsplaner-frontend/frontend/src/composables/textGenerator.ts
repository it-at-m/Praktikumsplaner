import Praktikumsstelle from "@/types/Praktikumsstelle";

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
        return (
            "Ausbildungsrichtung: " +
            stelle.ausbildungsrichtung +
            "\n" +
            "Ausbildungsjahr: " +
            stelle.ausbildungsjahr?.charAt(stelle.ausbildungsjahr?.length - 1) +
            ". Ausbildungsjahr"
        );
    }

    function getStudiumssPraktikumsstellenCardText(
        stelle: Praktikumsstelle
    ): string {
        return (
            "Studiengang: " +
            stelle.studiengang +
            "\n" +
            "Studiensemester: ab " +
            stelle.studiensemester?.charAt(stelle.studiensemester?.length - 1) +
            ". Semester"
        );
    }

    return { getPraktikumsstellenCardText, getPraktikumsstellenCardDetailText };
}
