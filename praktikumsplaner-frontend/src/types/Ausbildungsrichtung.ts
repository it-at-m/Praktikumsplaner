export const Ausbildungsrichtung = [
    {
        name: "Fachinformatiker Systemintegration (FISI)",
        value: "FISI",
        color: "light-blue-lighten-3",
    },
    {
        name: "Verwaltungswirt*in (QE2)",
        value: "QE2",
        color: "teal-lighten-3",
    },
    {
        name: "Kauffrau und Kaufmann für Büromanagement (KfB)",
        value: "KFB",
        color: "lime-lighten-3",
    },
    {
        name: "Verwaltungsfachangestellte*r (VFAK)",
        value: "VFAK",
        color: "orange-lighten-3",
    },
] as const;

export function findAusbildungsrichtungColorByValue(value: string): string {
    const ausbildungsrichtung = Ausbildungsrichtung.find(
        (s) => s.value === value
    );
    return ausbildungsrichtung ? ausbildungsrichtung.color : "";
}
