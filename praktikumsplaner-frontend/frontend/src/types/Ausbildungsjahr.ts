export const Ausbildungsjahr = [
    {
        name: "2. Lehrjahr",
        value: "JAHR2",
        zeitraumFISI: "01.09 - 28.02 oder 01.03 - 31.08",
    },
    {
        name: "3. Lehrjahr",
        value: "JAHR3",
        zeitraumFISI: "01.09 - 28.02 oder 01.03 - 31.08",
    },
] as const;

export function valueToNameAusbildungsjahr(value: string): string {
    return Ausbildungsjahr.find((a) => a.value === value)?.name || "";
}
