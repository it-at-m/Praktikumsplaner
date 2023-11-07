export function useZeitraeume() {
    function studiumsZeitraum(
        studienart: string | undefined,
        semester: string | undefined
    ): string {
        if (studienart == "BSC") {
            if (semester == "SEMESTER1" || semester == "SEMESTER3") {
                return "Februar - Mitte März";
            } else if (semester == "SEMESTER2" || semester == "SEMESTER6") {
                return "Juli - Ende September";
            } else if (semester == "SEMESTER4" || semester == "SEMESTER5") {
                return "Juli - Mitte März";
            } else {
                return "Nicht in ausgewähltem Semester verfügbar";
            }
        }
        if (studienart == "BWI") {
            if (semester == "SEMESTER1" || semester == "SEMESTER3") {
                return "01.09 - 28.02";
            } else if (semester == "SEMESTER2" || semester == "SEMESTER4") {
                return "01.03 - 01.09";
            } else if (semester == "SEMESTER5" || semester == "SEMESTER6") {
                return "01.09 - 30.06";
            } else {
                return "Nicht in ausgewähltem Semester verfügbar";
            }
        }
        if (studienart == "VI") {
            if (semester == "SEMESTER1" || semester == "SEMESTER3") {
                return "Februar - März";
            } else if (semester == "SEMESTER4" || semester == "SEMESTER5") {
                return "Oktober - März";
            } else {
                return "Nicht in ausgewähltem Semester verfügbar";
            }
        }
        return "";
    }

    function ausbildungsZeitraum(
        ausbildungsrichtung: string | undefined,
        ausbildungsjahr: string | undefined
    ): string {
        if (!ausbildungsrichtung || !ausbildungsjahr) return "";
        const currentMonth = new Date().getMonth() + 1;
        if (ausbildungsrichtung == "FISI") {
            if (
                !(ausbildungsjahr == "JAHR1") &&
                2 < currentMonth &&
                currentMonth < 9
            ) {
                return "01.09 - 28.02";
            } else if (
                !(ausbildungsjahr == "JAHR1") &&
                (currentMonth < 3 || 8 < currentMonth)
            ) {
                return "01.03 - 31.08";
            } else {
                return "Nicht in ausgewähltem Jahr verfügbar (Erstes Jahr bei der SWM)";
            }
        }
        return "";
    }

    return { studiumsZeitraum, ausbildungsZeitraum };
}
