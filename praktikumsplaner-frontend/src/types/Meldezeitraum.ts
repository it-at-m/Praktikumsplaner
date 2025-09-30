import Zeitraum from "@/types/Zeitraum";

export default class Meldezeitraum {
    constructor(
        public zeitraumName: string,
        public zeitraum: Zeitraum,
        public id?: string
    ) {}
}
