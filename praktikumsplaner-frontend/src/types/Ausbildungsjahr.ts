export const Ausbildungsjahr = [
  {
    name: "1. Lehrjahr",
    value: "JAHR1",
    zeitraumFISI: "01.03 - 31.08",
    zeitraumQE2: "November - Januar oder März - Juni",
    zeitraumKFB: "01.09 - 28.02 oder 01.03 - 31.08",
    zeitraumVFAK: "01.09 - 28.02 oder 01.03 - 31.08",
  },
  {
    name: "2. Lehrjahr",
    value: "JAHR2",
    zeitraumFISI: "01.09 - 28.02 oder 01.03 - 31.08",
    zeitraumQE2: "Juli - November oder Dezember - März",
    zeitraumKFB: "01.09 - 28.02 oder 01.03 - 31.08",
    zeitraumVFAK: "01.09 - 28.02 oder 01.03 - 31.08",
  },
  {
    name: "3. Lehrjahr",
    value: "JAHR3",
    zeitraumFISI: "01.09 - 28.02 oder 01.03 - 31.08",
    zeitraumQE2: "Nicht in ausgewähltem Lehrjahr verfügbar",
    zeitraumKFB: "01.09 - 30.06",
    zeitraumVFAK: "01.09 - 30.06",
  },
] as const;

export function valueToNameAusbildungsjahr(value: string | undefined): string {
  return Ausbildungsjahr.find((a) => a.value === value)?.name || "";
}
