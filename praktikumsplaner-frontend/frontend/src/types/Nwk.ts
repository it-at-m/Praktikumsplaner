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
