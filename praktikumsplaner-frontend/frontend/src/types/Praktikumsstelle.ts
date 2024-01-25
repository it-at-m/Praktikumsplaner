import Nwk from "@/types/Nwk";

export default class Praktikumsstelle {
    constructor(
        public dienststelle: string,

        public oertlicheAusbilder: string,

        public email: string,

        public taetigkeiten: string,

        public dringlichkeit: string,

        public projektarbeit?: boolean,

        public programmierkenntnisse?: string,

        public planstelleVorhanden?: boolean,

        public ausbildungsjahr?: string[],

        public studiensemester?: string[],

        public ausbildungsrichtung?: string,

        public studiengang?: string,

        public referat?: string,

        public namentlicheAnforderung?: string,

        public id?: string,

        public assignedNwk?: Nwk,

        public meldezeitraumID?: string
    ) {}
}
