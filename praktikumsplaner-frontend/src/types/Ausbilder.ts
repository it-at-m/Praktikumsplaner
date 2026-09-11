export default class Ausbilder {
  constructor(
    public name: string,
    public email: string,
    public erwFuehrungszeugnisVorhanden: boolean | undefined,
    public minderjaehrigMoeglich: boolean | undefined
  ) {}
  static empty(): Ausbilder {
    return new Ausbilder("", "", undefined, undefined);
  }
}
