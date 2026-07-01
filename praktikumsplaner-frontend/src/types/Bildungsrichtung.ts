export enum Bildungsart {
  AUSBILDUNG = "AUSBILDUNG",
  STUDIUM = "STUDIUM",
}

export enum BildungsrichtungKey {
  // Ausbildung
  FISI = "FISI",
  QE2 = "QE2",
  KFB = "KFB",
  VFAK = "VFAK",
  // Studium
  BWI = "BWI",
  BSC = "BSC",
  VI = "VI",
  LLB = "LLB",
  PUMA = "PUMA",
  QE3 = "QE3",
}

interface Bildungsrichtung {
  value: BildungsrichtungKey;
  art: Bildungsart;
  name: string;
  color: string;
}

export const Ausbildungsrichtungen: Bildungsrichtung[] = [
  {
    value: BildungsrichtungKey.FISI,
    art: Bildungsart.AUSBILDUNG,
    name: "Fachinformatiker Systemintegration (FISI)",
    color: "light-blue-lighten-3",
  },
  {
    value: BildungsrichtungKey.QE2,
    art: Bildungsart.AUSBILDUNG,
    name: "Verwaltungswirt*in (QE2)",
    color: "teal-lighten-3",
  },
  {
    value: BildungsrichtungKey.KFB,
    art: Bildungsart.AUSBILDUNG,
    name: "Kauffrau und Kaufmann für Büromanagement (KfB)",
    color: "lime-lighten-3",
  },
  {
    value: BildungsrichtungKey.VFAK,
    art: Bildungsart.AUSBILDUNG,
    name: "Verwaltungsfachangestellte*r (VFAK)",
    color: "orange-lighten-3",
  },
];

export const Studienrichtungen: Bildungsrichtung[] = [
  {
    value: BildungsrichtungKey.BWI,
    art: Bildungsart.STUDIUM,
    name: "Wirtschaftsinformatik (BWI)",
    color: "pink-lighten-4",
  },
  {
    value: BildungsrichtungKey.BSC,
    art: Bildungsart.STUDIUM,
    name: "Informatik (B.Sc.)",
    color: "green-lighten-3",
  },
  {
    value: BildungsrichtungKey.VI,
    art: Bildungsart.STUDIUM,
    name: "Verwaltungsinformatik (VI)",
    color: "deep-purple-lighten-4",
  },
  {
    value: BildungsrichtungKey.LLB,
    art: Bildungsart.STUDIUM,
    name: "Öffentliches Recht - Bachelor of Laws (LL.B.)",
    color: "blue-lighten-3",
  },
  {
    value: BildungsrichtungKey.PUMA,
    art: Bildungsart.STUDIUM,
    name: "Public Management (Puma)/Public Sector (PS)",
    color: "red-lighten-3",
  },
  {
    value: BildungsrichtungKey.QE3,
    art: Bildungsart.STUDIUM,
    name: "Diplom - Verwaltungswirt*in (QE3)",
    color: "indigo-lighten-3",
  },
];

export const Bildungsrichtungen: Bildungsrichtung[] = [
  ...Ausbildungsrichtungen,
  ...Studienrichtungen,
];

export function isAusbildung(bildungsrichtung: Bildungsrichtung | undefined): boolean {
  return bildungsrichtung?.art == Bildungsart.AUSBILDUNG
}

export function isStudium(bildungsrichtung: Bildungsrichtung | undefined): boolean {
  return bildungsrichtung?.art == Bildungsart.STUDIUM;
}

export function findBildungsrichtung(
  value: string
): Bildungsrichtung | undefined {
  return Bildungsrichtungen.find((i) => i.value == value);
}

export function findBildungsrichtungColorByValue(value: string): string {
  return findBildungsrichtung(value)?.color ?? "";
}
