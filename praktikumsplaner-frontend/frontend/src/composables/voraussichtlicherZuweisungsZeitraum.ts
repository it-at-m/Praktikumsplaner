import { Studienart } from "@/types/Studienart";
import { Studiensemester } from "@/types/Studiensemester";

export function useZeitraeume() {
    function studiumsZeitraum(studienart: any, semester: any): string {
        const meldungMonth = new Date().getMonth() + 1;
        console.log(meldungMonth);
        console.log(studienart);
        console.log(semester);
        if (studienart == Studienart.find((s) => s.name === "BSC")?.name) {
            console.log("jo");
            if (meldungMonth == 1 || 7 < meldungMonth) {
                return "Februar - Mitte März";
            } else if (1 < meldungMonth && meldungMonth < 7) {
                return "Juli - September";
            } else if (
                semester ==
                Studiensemester.find((s) => s.name === "SEMESTER5")?.name
            ) {
                return "Juli - März";
            }
        }
        return "undefiniert";
    }
    return { studiumsZeitraum };
}
