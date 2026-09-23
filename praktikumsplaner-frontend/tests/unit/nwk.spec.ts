import { afterEach, describe, expect, it, vi } from "vitest";

import { BildungsrichtungKey } from "../../src/types/Bildungsrichtung";
import Nwk from "../../src/types/Nwk";

describe("Nwk", () => {
  afterEach(() => {
    vi.useRealTimers();
  });

  it("calculateSemester October threshold", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2026, 9, 1));

    const nwk = new Nwk(
      undefined,
      undefined,
      undefined,
      "26/29",
      undefined,
      true,
      BildungsrichtungKey.PUMA
    );

    expect(nwk.calculateSemester()).toBe(1);
  });

  it("calculateSemester April threshold", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2027, 3, 1));

    const nwk = new Nwk(
      undefined,
      undefined,
      undefined,
      "26/29",
      undefined,
      true,
      BildungsrichtungKey.PUMA
    );

    expect(nwk.calculateSemester()).toBe(2);
  });

  it("calculateLehrjahr October threshold", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2026, 9, 1));

    const nwk = new Nwk(
      undefined,
      undefined,
      undefined,
      "24/27",
      undefined,
      true,
      BildungsrichtungKey.KFB
    );

    expect(nwk.calculateLehrjahr()).toBe(3);
  });

  it("calculateLehrjahr before October threshold", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2026, 7, 31));

    const nwk = new Nwk(
      undefined,
      undefined,
      undefined,
      "24/27",
      undefined,
      true,
      BildungsrichtungKey.KFB
    );

    expect(nwk.calculateLehrjahr()).toBe(2);
  });
});
