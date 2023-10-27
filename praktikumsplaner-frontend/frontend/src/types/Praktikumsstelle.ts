export default class Praktikumsstelle {
    constructor(
        public dienststelle: string,

        public oertlicheAusbilder: string,

        public email: string,

        public taetigkeiten: string,

        public dringlichkeit: string,

        public projektarbeit?: string,

        public programmierkenntnisse?: string,

        public ausbildungsjahr?: string,

        public studiensemester?: string,

        public ausbildungsrichtung?: string,

        public studienart?: string,

        public referat?: string,

        public namentlicheAnforderung?: string
    ) {}
}
