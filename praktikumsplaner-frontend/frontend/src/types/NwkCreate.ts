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
