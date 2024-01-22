import Warning from "@/types/Warning";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import Nwk from "@/types/Nwk";

export function useWarnings() {
    function getBeforeAssignmentWarnings(
        stelle: Praktikumsstelle,
        nwk: Nwk
    ): Warning[] {
        const warnings: Warning[] = [];
        // Check if Studiums or Ausbildungspraktikumsstelle
        if (
            stelle.ausbildungsrichtung == undefined &&
            nwk.studiengang == undefined
        ) {
            const warningText =
                "Wollen sie wirklich " +
                nwk.vorname +
                " " +
                nwk.nachname +
                " auf eine Studiumspraktikumsstelle setzen, obwohl er/sie Auszubildende/r ist?";
            warnings.push(new Warning("", warningText));
        }

        if (
            stelle.studiengang == undefined &&
            nwk.ausbildungsrichtung == undefined
        ) {
            const warningText =
                "Wollen sie wirklich " +
                nwk.vorname +
                " " +
                nwk.nachname +
                " auf eine Ausbildungspraktikumsstelle setzen, obwohl er/sie Student*in ist?";
            warnings.push(new Warning("", warningText));
        }

        // Check if studiengang is the same
        if (
            stelle.studiengang &&
            nwk.ausbildungsrichtung == undefined &&
            stelle.studiengang != nwk.studiengang
        ) {
            const warningText =
                "Wollen sie wirklich eine/n " +
                nwk.studiengang +
                " Student*in auf eine " +
                stelle.studiengang +
                " Stelle setzen?\n";
            warnings.push(new Warning("", warningText));
        }

        // Check if requested Nwk is the same
        if (
            stelle.namentlicheAnforderung &&
            stelle.namentlicheAnforderung?.toUpperCase() !=
                nwk.vorname.toUpperCase() + " " + nwk.nachname.toUpperCase()
        ) {
            const warningText =
                "Wollen sie wirklich " +
                nwk.vorname +
                " " +
                nwk.nachname +
                " auf diese Stelle setzen obwohl explizit " +
                stelle.namentlicheAnforderung +
                " angefordert wurde?";
            warnings.push(new Warning("", warningText));
        }

        // Check if Nwk is in the right semester
        if (
            stelle.studiengang != undefined &&
            nwk.ausbildungsrichtung == undefined &&
            stelle.studiensemester
        ) {
            const expectedSemester: number = +stelle.studiensemester.substring(
                8,
                10
            );
            const actualSemester = calculateSemester(nwk);
            if (expectedSemester > actualSemester) {
                const warningText =
                    "Wollen sie wirklich eine/n Student*in im " +
                    actualSemester +
                    " Semester auf diese Stelle setzen, obwohl ein/e Student*in ab dem " +
                    expectedSemester +
                    " Semester gefordert ist?";
                warnings.push(new Warning("", warningText));
            }
        }

        // Check if Nwk is in the right Lehrjahr
        if (
            stelle.ausbildungsrichtung != undefined &&
            nwk.studiengang == undefined &&
            stelle.ausbildungsjahr
        ) {
            const expectedLehrjahr: number = +stelle.ausbildungsjahr.substring(
                4,
                6
            );
            const actualLehrjahr = calculateLehrjahr(nwk);
            if (expectedLehrjahr > actualLehrjahr) {
                const warningText =
                    "Wollen sie wirklich eine/n Auszubildende/n im " +
                    actualLehrjahr +
                    " Lehrjahr auf diese Stelle setzen, obwohl eine/n Auszubildende/n ab dem " +
                    expectedLehrjahr +
                    " Lehrjahr gefordert ist?";
                warnings.push(new Warning("", warningText));
            }
        }
        return warnings;
    }

    function getAfterAssignmentWarnings(
        stellen: Praktikumsstelle[],
        nwks: Nwk[]
    ): Warning[] {
        const warnings: Warning[] = [];
        for (const nwk of nwks) {
            const warning = new Warning(
                "NWK",
                "Die NWK " +
                    nwk.vorname +
                    " " +
                    nwk.nachname +
                    " ist nicht verplant."
            );

            warnings.push(warning);
        }
        for (const stelle of stellen) {
            if (
                (stelle.dringlichkeit.toLocaleLowerCase() == "dringend" ||
                    stelle.dringlichkeit.toLocaleLowerCase() == "zwingend") &&
                stelle.assignedNwk == undefined
            ) {
                const warning = new Warning(
                    "Dringlichkeit",
                    "Der Praktikumsstelle " +
                        stelle.dienststelle +
                        " bei " +
                        stelle.oertlicheAusbilder +
                        " ist keine NWK zugewiesen, die Dringlichkeit ist jedoch mit " +
                        stelle.dringlichkeit +
                        " angegeben."
                );
                warnings.push(warning);
            }

            if (
                stelle.namentlicheAnforderung != null &&
                stelle.assignedNwk == undefined
            ) {
                const warning = new Warning(
                    "Namentliche Anforderung",
                    "Der Praktikumsstelle " +
                        stelle.dienststelle +
                        " bei " +
                        stelle.oertlicheAusbilder +
                        " ist keine NWK zugewiesen, es liegt jedoch eine namentliche Anforderung für " +
                        stelle.namentlicheAnforderung +
                        " vor."
                );
                warnings.push(warning);
            }
        }
        return [];
    }

    function calculateSemester(nwk: Nwk) {
        if (!nwk) return -1;
        if (nwk.studiengang == undefined) return 0;
        let semester: number;
        const startYear: number = +nwk.jahrgang.substring(0, 2) + 2000;
        const currentYear: number = new Date().getFullYear();
        const difference = currentYear - startYear;
        semester = difference * 2;
        if (new Date().getMonth() > 8) semester += 1;
        if (new Date().getMonth() < 3) semester -= 1;
        return semester;
    }

    function calculateLehrjahr(nwk: Nwk) {
        if (!nwk) return -1;
        if (nwk.ausbildungsrichtung == undefined) return 0;

        let lehrjahr: number;
        const startYear: number = +nwk.jahrgang.substring(0, 2) + 2000;
        const currentYear: number = new Date().getFullYear();
        lehrjahr = currentYear - startYear;
        if (new Date().getMonth() > 8) {
            lehrjahr += 1;
        } else {
            lehrjahr -= 1;
        }
        return lehrjahr;
    }

    return {
        getBeforeAssignmentWarnings,
        getAfterAssignmentWarnings,
    };
}
