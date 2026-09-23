import { afterEach, describe, expect, it, vi } from "vitest";

import { BildungsrichtungKey } from "../../src/types/Bildungsrichtung";
import Nwk from "../../src/types/Nwk";

describe("Nwk", () => {
  afterEach(() => {
    vi.useRealTimers();
  });

  it("calculateSemester before March threshold", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2027, 1, 28));

    const nwk = new Nwk(
      undefined,
      undefined,
      undefined,
      "26/29",
      undefined,
      true,
      BildungsrichtungKey.BSC
    );

    expect(nwk.calculateSemester()).toBe(1);
  });

  it("calculateSemester March threshold", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2027, 2, 30));

    const nwk = new Nwk(
      undefined,
      undefined,
      undefined,
      "26/29",
      undefined,
      true,
      BildungsrichtungKey.BSC
    );

    expect(nwk.calculateSemester()).toBe(2);
  });

  it("calculateSemester before September threshold", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2027, 7, 30));

    const nwk = new Nwk(
      undefined,
      undefined,
      undefined,
      "26/29",
      undefined,
      true,
      BildungsrichtungKey.BSC
    );

    expect(nwk.calculateSemester()).toBe(2);
  });

  it("calculateSemester September threshold", () => {
    vi.useFakeTimers();
    vi.setSystemTime(new Date(2027, 9, 1));

    const nwk = new Nwk(
      undefined,
      undefined,
      undefined,
      "26/29",
      undefined,
      true,
      BildungsrichtungKey.BSC
    );

    expect(nwk.calculateSemester()).toBe(3);
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
      BildungsrichtungKey.FISI
    );

    expect(nwk.calculateLehrjahr()).toBe(2);
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
      BildungsrichtungKey.FISI
    );

    expect(nwk.calculateLehrjahr()).toBe(3);
  });
});
