import type { BildungsrichtungKey } from "@/types/Bildungsrichtung.ts";

import Ausbilder from "@/types/Ausbilder.ts";
import Nwk from "@/types/Nwk";

export default class Praktikumsstelle {
  constructor(
    public dienststelle?: string,

    public richtung?: BildungsrichtungKey,
    public taetigkeiten?: string,
    public dringlichkeit?: string,
    public projektarbeit?: boolean,
    public planstelleVorhanden?: boolean,
    public namentlicheAnforderung?: string,

    public programmierkenntnisse?: boolean,
    public ausbildungsjahr?: string[],
    public studiensemester?: string[],
    public wuensche?: string,

    public ausbilder: Ausbilder[] = [Ausbilder.empty()],

    public id?: string,
    public assignedNwk?: Nwk,
    public meldezeitraumID?: string
  ) {}
}
