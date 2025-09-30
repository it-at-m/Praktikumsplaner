export default class Nwk {
    constructor(
        public id: string,
        public vorname: string,
        public nachname: string,
        public jahrgang: string,
        public vorlesungstage: string[],
        public isActive: boolean,
        public studiengang?: string,
        public ausbildungsrichtung?: string
    ) {}
}

// Used in NwkCards to determine if the details button should be shown
export function hasDetails(nwk: Nwk): boolean {
    return nwk.vorlesungstage.length > 0;
}
