export const Ausbildungsrichtung = [
    {
        name: "Fachinformatiker Systemintegration (FISI)",
        value: "FISI",
        color: "light-blue-lighten-4",
    },
] as const;

export function findAusbildungsrichtungColorByValue(value: string): string {
    const ausbildungsrichtung = Ausbildungsrichtung.find(
        (s) => s.value === value
    );
    return ausbildungsrichtung ? ausbildungsrichtung.color : "";
}
