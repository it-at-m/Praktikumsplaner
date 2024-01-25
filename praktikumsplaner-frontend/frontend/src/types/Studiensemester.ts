export const Studiensemester = [
    {
        name: "1. Semester",
        value: "SEMESTER1",
        zeitraumBSC: "Februar - Mitte März",
        zeitraumBWI: "01.09 - 28.02",
        zeitraumVI: "Februar - März",
    },
    {
        name: "2. Semester",
        value: "SEMESTER2",
        zeitraumBSC: "Juli - Ende September",
        zeitraumBWI: "01.03 - 01.09",
        zeitraumVI: "Nicht in ausgewähltem Semester verfügbar",
    },
    {
        name: "3. Semester",
        value: "SEMESTER3",
        zeitraumBSC: "Februar - Mitte März",
        zeitraumBWI: "01.09 - 28.02",
        zeitraumVI: "Februar - März",
    },
    {
        name: "4. Semester",
        value: "SEMESTER4",
        zeitraumBSC: "Juli - Mitte März (Praxissemester)",
        zeitraumBWI: "01.03 - 01.09",
        zeitraumVI: "Oktober - März",
    },
    {
        name: "5. Semester",
        value: "SEMESTER5",
        zeitraumBSC: "Juli - Mitte März (Praxissemester)",
        zeitraumBWI: "01.09 - 30.06",
        zeitraumVI: "Oktober - März",
    },
    {
        name: "6. Semester",
        value: "SEMESTER6",
        zeitraumBSC: "Juli - Ende September",
        zeitraumBWI: "01.09 - 30.06",
        zeitraumVI: "Nicht in ausgewähltem Semester verfügbar",
    },
] as const;
export function valueToNameStudiensemester(value: string): string {
    return Studiensemester.find((s) => s.value === value)?.name || "";
}
