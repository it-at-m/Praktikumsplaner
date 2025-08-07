import { describe, expect, it } from "vitest";

import GermanWeekdayMapper from "@/types/GermanWeekdayMapper";

describe("germanWeekdayMapper.ts", () => {
    it("returns the correct german weekday for a given english weekdays", () => {
        const englishWeekdays = ["MONDAY", "TUESDAY"];
        const germanWeekday = new GermanWeekdayMapper().getGermanDays(
            englishWeekdays
        );
        expect(germanWeekday).toEqual(["Montag", "Dienstag"]);
    });
});

describe("germanWeekdayMapper.ts", () => {
    it("returns the correct german weekday(correct order) for a given english weekdays(mixed order)", () => {
        const englishWeekdays = ["MONDAY", "FRIDAY", "TUESDAY", "THURSDAY"];
        const germanWeekday = new GermanWeekdayMapper().getGermanDays(
            englishWeekdays
        );
        expect(germanWeekday).toEqual([
            "Montag",
            "Dienstag",
            "Donnerstag",
            "Freitag",
        ]);
    });
});
