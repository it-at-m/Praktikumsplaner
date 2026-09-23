import type { BildungsrichtungKey } from "@/types/Bildungsrichtung";

import {
  findBildungsrichtung,
  isAusbildung,
  isStudium,
} from "@/types/Bildungsrichtung";

export default class Nwk {
  constructor(
    public id: string,
    public vorname: string,
    public nachname: string,
    public jahrgang: string,
    public vorlesungstage: string[],
    public isActive: boolean,
    public richtung: BildungsrichtungKey
  ) {}

  calculateLehrjahr() {
    if (!isAusbildung(findBildungsrichtung(this.richtung))) return -1;

    let lehrjahr: number;
    const startYear: number = +this.jahrgang.substring(0, 2) + 2000;
    const now = new Date();
    const currentYear: number = now.getFullYear();
    lehrjahr = currentYear - startYear;
    // next from September
    if (now.getMonth() >= 8) {
      lehrjahr += 1;
    }
    return lehrjahr;
  }

  calculateSemester() {
    if (!isStudium(findBildungsrichtung(this.richtung))) return -1;
    let semester: number;
    const startYear: number = +this.jahrgang.substring(0, 2) + 2000;
    const now = new Date();
    const currentYear: number = now.getFullYear();
    const difference = currentYear - startYear;
    semester = difference * 2;
    // next from September
    if (now.getMonth() >= 8) {
      semester += 1;
    }
    // previous before March
    if (now.getMonth() < 2) {
      semester -= 1;
    }
    return semester;
  }
}

// Used in NwkCards to determine if the details button should be shown
export function hasDetails(nwk: Nwk): boolean {
  return nwk.vorlesungstage.length > 0;
}
