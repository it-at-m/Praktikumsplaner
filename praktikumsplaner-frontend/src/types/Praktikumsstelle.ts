import type { BildungsrichtungKey } from "@/types/Bildungsrichtung.ts";

import Nwk from "@/types/Nwk";

export default class Praktikumsstelle {
  constructor(
    public dienststelle?: string,

    public richtung?: BildungsrichtungKey,

    public oertlicheAusbilder?: string,

    public erwFuehrungszeugnisVorhanden?: boolean,

    public email?: string,

    public taetigkeiten?: string,

    public dringlichkeit?: string,

    public projektarbeit?: boolean,

    public programmierkenntnisse?: boolean,

    public planstelleVorhanden?: boolean,

    public ausbildungsjahr?: string[],

    public studiensemester?: string[],

    public wuensche?: string,

    public namentlicheAnforderung?: string,

    public id?: string,

    public assignedNwk?: Nwk,

    public meldezeitraumID?: string,

    public minderjaehrigMoeglich?: boolean
  ) {}
}
