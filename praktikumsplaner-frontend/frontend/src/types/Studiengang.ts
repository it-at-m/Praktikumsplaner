export const Studiengang = [
    {
        name: "Wirtschaftsinformatik (BWI)",
        value: "BWI",
        color: "pink lighten-4",
    },
    { name: "Informatik (B.Sc.)", value: "BSC", color: "green lighten-3" },
    {
        name: "Verwaltungsinformatik (VI)",
        value: "VI",
        color: "deep-purple lighten-3",
    },
] as const;

export function findStudiengangColorByValue(value: string): string {
    const studiengang = Studiengang.find((s) => s.value === value);
    return studiengang ? studiengang.color : "";
}
