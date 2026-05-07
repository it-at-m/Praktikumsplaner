import Nwk from "@/types/Nwk";

export type Praktikumsart = "STUDIUM" | "AUSBILDUNG";

export default class Praktikumsstelle {
  constructor(
    public dienststelle?: string,

    public oertlicheAusbilder?: string,

    public erwFuehrungszeugnisVorhanden?: boolean,

    public email?: string,

    public taetigkeiten?: string,

    public dringlichkeit?: string,

    // Only for AUSBILDUNG
    public projektarbeit?: boolean,

    // For STUDIUM: required; For AUSBILDUNG: optional
    public programmierkenntnisse?: string,

    public planstelleVorhanden?: boolean,

    // Only for AUSBILDUNG
    public ausbildungsjahr?: string[],

    // Only for STUDIUM
    public studiensemester?: string[],

    // Unified Richtung (covers former studiengang/ausbildungsrichtung)
    public richtung?: string,

    // Derived from richtung on server; fallback: derive on client via mapping
    public art?: Praktikumsart,

    public wuensche?: string,

    public namentlicheAnforderung?: string,

    public id?: string,

    public assignedNwk?: Nwk,

    public meldezeitraumID?: string,

    // Only for AUSBILDUNG
    public minderjaehrigMoeglich?: boolean
  ) {}
}
