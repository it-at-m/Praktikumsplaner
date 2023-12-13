export default class Meldezeitraum {
    constructor(
        public zeitraumName: string,
        public startZeitpunkt?: string,
        public endZeitpunkt?: string,
        public id?: string
    ) {}

    get isStartBeforeEnd(): boolean {
        return (
            !this.startZeitpunkt ||
            !this.endZeitpunkt ||
            new Date(this.startZeitpunkt) < new Date(this.endZeitpunkt)
        );
    }
}
