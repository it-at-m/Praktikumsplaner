export default class NwkCreate {
    constructor(
        public vorname: string,
        public nachname: string,
        public jahrgang: string,
        public vorlesungstage: string[],
        public studiengang?: string,
        public ausbildungsrichtung?: string
    ) {}
}

// Used in NwkCards to determine if the details button should be shown
export function hasDetails(nwk: NwkCreate): boolean {
    return nwk.vorlesungstage.length > 0;
}
