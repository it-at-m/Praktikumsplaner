export default class Zeitraum {
    constructor(
        public startZeitpunkt?: string,
        public endZeitpunkt?: string
    ) {}

    get isStartBeforeEnd(): boolean {
        return (
            !this.startZeitpunkt ||
            !this.endZeitpunkt ||
            new Date(this.startZeitpunkt) < new Date(this.endZeitpunkt)
        );
    }
}
