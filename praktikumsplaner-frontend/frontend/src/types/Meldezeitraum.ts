export default class Meldezeitraum {
    constructor(
        public zeitraumName: string,
        public startZeitpunkt?: string,
        public endZeitpunkt?: string
    ) {}
}
