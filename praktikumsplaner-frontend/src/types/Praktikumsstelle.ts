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

  static clone(stelle: Praktikumsstelle): Praktikumsstelle {
    return new Praktikumsstelle(
      stelle.dienststelle,
      stelle.richtung,
      stelle.taetigkeiten,
      stelle.dringlichkeit,
      stelle.projektarbeit,
      stelle.planstelleVorhanden,
      stelle.namentlicheAnforderung,
      stelle.programmierkenntnisse,
      stelle.ausbildungsjahr ? [...stelle.ausbildungsjahr] : undefined,
      stelle.studiensemester ? [...stelle.studiensemester] : undefined,
      stelle.wuensche,
      stelle.ausbilder.map(
        (ausbilder) =>
          new Ausbilder(
            ausbilder.name,
            ausbilder.email,
            ausbilder.erwFuehrungszeugnisVorhanden,
            ausbilder.minderjaehrigMoeglich
          )
      ),
      stelle.id,
      stelle.assignedNwk,
      stelle.meldezeitraumID
    );
  }
}
