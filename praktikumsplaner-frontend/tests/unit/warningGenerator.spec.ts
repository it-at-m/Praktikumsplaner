import { afterEach, describe, expect, it, vi } from "vitest";

import { useWarnings } from "../../src/composables/warningGenerator";
import { BildungsrichtungKey } from "../../src/types/Bildungsrichtung";
import Nwk from "../../src/types/Nwk";
import Praktikumsstelle from "../../src/types/Praktikumsstelle";

const warnings = useWarnings();

function createNwk(
  richtung: BildungsrichtungKey,
  jahrgang = "26/29",
  vorname = "Max",
  nachname = "Mustermann"
): Nwk {
  return new Nwk("nwk-id", vorname, nachname, jahrgang, [], true, richtung);
}

function createStelle(
  richtung: BildungsrichtungKey,
  options: {
    ausbildungsjahr?: string[];
    assignedNwk?: Nwk;
    dringlichkeit?: string;
    namentlicheAnforderung?: string;
    studiensemester?: string[];
  } = {}
): Praktikumsstelle {
  return new Praktikumsstelle(
    "Rathaus",
    richtung,
    undefined,
    options.dringlichkeit,
    undefined,
    undefined,
    options.namentlicheAnforderung,
    undefined,
    options.ausbildungsjahr,
    options.studiensemester,
    undefined,
    undefined,
    "stelle-id",
    options.assignedNwk
  );
}

describe("warningGenerator", () => {
  afterEach(() => {
    vi.useRealTimers();
  });

  describe("getBeforeAssignmentWarnings", () => {
    it("give an apprenticeship to a study position then warns about the education type", () => {
      const result = warnings.getBeforeAssignmentWarnings(
        createStelle(BildungsrichtungKey.BSC),
        createNwk(BildungsrichtungKey.FISI)
      );

      expect(result[0].message).toContain("obwohl er/sie Auszubildende/r ist?");
    });

    it("give a student to an apprenticeship position then warns about the education type", () => {
      const result = warnings.getBeforeAssignmentWarnings(
        createStelle(BildungsrichtungKey.FISI),
        createNwk(BildungsrichtungKey.BSC)
      );

      expect(result[0].message).toContain("obwohl er/sie Student*in ist?");
    });

    it("give a student to a different study direction then warns about the direction", () => {
      const result = warnings.getBeforeAssignmentWarnings(
        createStelle(BildungsrichtungKey.BSC),
        createNwk(BildungsrichtungKey.BWI)
      );

      expect(result[0].message).toContain("BWI Student*in auf eine BSC Stelle");
    });

    it("give an apprentice to a different apprenticeship direction then warns about the direction", () => {
      const result = warnings.getBeforeAssignmentWarnings(
        createStelle(BildungsrichtungKey.KFB),
        createNwk(BildungsrichtungKey.FISI)
      );

      expect(result[0].message).toContain(
        "FISI Auszubildende/n auf eine KFB Stelle"
      );
    });

    it("give a named position to another person then warns about the requirement", () => {
      const result = warnings.getBeforeAssignmentWarnings(
        createStelle(BildungsrichtungKey.FISI, {
          namentlicheAnforderung: "Erika Musterfrau",
        }),
        createNwk(BildungsrichtungKey.FISI)
      );

      expect(result[0].message).toContain(
        "obwohl explizit Erika Musterfrau angefordert wurde?"
      );
    });

    it("give a student the wrong semester then formats the semester warning", () => {
      vi.useFakeTimers();
      vi.setSystemTime(new Date(2027, 3, 1));

      const result = warnings.getBeforeAssignmentWarnings(
        createStelle(BildungsrichtungKey.BSC, {
          studiensemester: ["SEMESTER1"],
        }),
        createNwk(BildungsrichtungKey.BSC)
      );

      expect(result[0].message).toContain(
        "Student*in im 2. Semester auf diese Stelle"
      );
      expect(result[0].message).toContain("im 1. Semester erwartet wird.");
    });

    it("give an apprentice the wrong training year then warns about the year", () => {
      vi.useFakeTimers();
      vi.setSystemTime(new Date(2026, 9, 1));

      const result = warnings.getBeforeAssignmentWarnings(
        createStelle(BildungsrichtungKey.FISI, {
          ausbildungsjahr: ["JAHR2"],
        }),
        createNwk(BildungsrichtungKey.FISI, "24/27")
      );

      expect(result[0].message).toContain(
        "Auszubildende/n im 3. Lehrjahr auf diese Stelle"
      );
      expect(result[0].message).toContain("im 2. Lehrjahr");
    });

    it("give a matching assignment then returns no warnings", () => {
      const result = warnings.getBeforeAssignmentWarnings(
        createStelle(BildungsrichtungKey.FISI),
        createNwk(BildungsrichtungKey.FISI)
      );

      expect(result).toEqual([]);
    });
  });

  describe("getAfterAssignmentWarnings", () => {
    it("check unplanned people and urgent unnamed positions then returns both warnings", () => {
      const result = warnings.getAfterAssignmentWarnings(
        [
          createStelle(BildungsrichtungKey.FISI, {
            dringlichkeit: "DRINGEND",
            namentlicheAnforderung: "Erika Musterfrau",
          }),
        ],
        [createNwk(BildungsrichtungKey.FISI)]
      );

      expect(result.map(({ title, message }) => ({ title, message }))).toEqual([
        {
          title: "NWK",
          message: "Die NWK Max Mustermann ist nicht verplant.",
        },
        {
          title: "Dringlichkeit",
          message:
            "Der Praktikumsstelle Rathaus der Richtung FISI ist keine NWK zugewiesen, die Dringlichkeit ist jedoch mit DRINGEND angegeben.",
        },
        {
          title: "Namentliche Anforderung",
          message:
            "Der Praktikumsstelle Rathaus der Richtung FISI ist keine NWK zugewiesen, es liegt jedoch eine namentliche Anforderung für Erika Musterfrau vor.",
        },
      ]);
    });

    it("check a non-urgent assigned position then returns no position warning", () => {
      const result = warnings.getAfterAssignmentWarnings(
        [
          createStelle(BildungsrichtungKey.FISI, {
            assignedNwk: createNwk(BildungsrichtungKey.FISI),
            dringlichkeit: "normal",
          }),
        ],
        []
      );

      expect(result).toEqual([]);
    });
  });
});
