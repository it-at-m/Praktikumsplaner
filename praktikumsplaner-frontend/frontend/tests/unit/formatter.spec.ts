import { describe } from "vitest";
import { useFormatter } from "@/composables/formatter";
const formatter = useFormatter();
describe("formatter startingCharUpperCase test", () => {
    it("tests startingCharUpperCase to be true", () => {
        const text = "lorem ipsum";
        expect(formatter.startingCharUpperCase(text)).toBe("Lorem ipsum");
    });
    it("tests startingCharUpperCase to be true when empty", () => {
        const text = "";
        expect(formatter.startingCharUpperCase(text)).toBe("");
    });
    it("tests startingCharUpperCase to be true when null", () => {
        expect(formatter.startingCharUpperCase(null)).toBe("");
    });
    it("tests startingCharUpperCase to be true when undefined", () => {
        expect(formatter.startingCharUpperCase(undefined)).toBe("");
    });
});
describe("formatter formatDate test", () => {
    it("tests formatDate to be true", () => {
        const date = new Date("2021-05-01");
        // actual is 05/01/2021
        expect(formatter.formatDate(date)).toBe("05/01/2021");
    });
    it("tests formatDate to throw Invalid date when empty", () => {
        const date = new Date("");
        expect(formatter.formatDate(date)).toBe("Invalid date");
    });
    it("tests formatDate to be true when null", () => {
        expect(formatter.formatDate(null)).toBe("");
    });
    it("tests formatDate to be true when undefined", () => {
        expect(formatter.formatDate(undefined)).toBe("");
    });
});
describe("formatter formatStringDate test", () => {
    it("tests formatStringDate to be true", () => {
        const date = "2021-05-01";
        expect(formatter.formatStringDate(date)).toBe("01.05.2021");
    });
    it("tests formatStringDate to be true when empty", () => {
        const date = "";
        expect(formatter.formatStringDate(date)).toBe("");
    });
    it("tests formatStringDate to be true when null", () => {
        expect(formatter.formatStringDate(null)).toBe("");
    });
    it("tests formatStringDate to be true when undefined", () => {
        expect(formatter.formatStringDate(undefined)).toBe("");
    });
});
