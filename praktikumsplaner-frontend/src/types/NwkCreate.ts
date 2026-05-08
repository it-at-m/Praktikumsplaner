import type { BildungsrichtungKey } from "@/types/Bildungsrichtung.ts";

export default class NwkCreate {
  constructor(
    public vorname: string,
    public nachname: string,
    public jahrgang: string,
    public vorlesungstage: string[],
    public richtung?: BildungsrichtungKey
  ) {}
}
