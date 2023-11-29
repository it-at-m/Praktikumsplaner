export default class Praktikumsstelle {
    constructor(
        public dienststelle: string,

        public oertlicheAusbilder: string,

        public email: string,

        public taetigkeiten: string,

        public dringlichkeit: string,

        public projektarbeit?: boolean,

        public programmierkenntnisse?: boolean,

        public planstelleVorhanden?: boolean,

        public ausbildungsjahr?: string,

        public studiensemester?: string,

        public ausbildungsrichtung?: string,

        public studienart?: string,

        public referat?: string,

        public namentlicheAnforderung?: string,

        public id?: string
    ) {}
}
