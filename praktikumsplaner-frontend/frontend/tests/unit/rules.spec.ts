import { useRules } from "@/composables/rules";
import { describe } from "vitest";
const errorMessage = "Error";
describe("rules fileFormat test", () => {
    it("tests fileFormatRules return true", () => {
        const validationRules = useRules();

        const txtRule = validationRules.fileTypeRule(
            "text/plain",
            errorMessage
        );
        const txtFile = new File(["foo"], "foo.txt", {
            type: "text/plain",
        });

        expect(txtRule(txtFile)).toBe(true);
    });
    it("tests fileFormatRules returns Error when using wrong fileformat", () => {
        const validationRules = useRules();

        const txtRule = validationRules.fileTypeRule("audio/aac", errorMessage);
        const txtFile = new File(["foo"], "foo.txt", {
            type: "text/plain",
        });

        expect(txtRule(txtFile)).toBe(errorMessage);
    });
    it("tests fileFormatRules with null", () => {
        const validationRules = useRules();

        const txtRule = validationRules.fileTypeRule("audio/aac", errorMessage);

        const nullFile = null;

        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        expect(txtRule(nullFile)).toBe(errorMessage);
    });
    it("tests fileFormatRules with undefined", () => {
        const validationRules = useRules();

        const txtRule = validationRules.fileTypeRule("audio/aac", errorMessage);

        let undefined: undefined;

        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        expect(txtRule(undefined)).toBe(errorMessage);
    });
});
describe("rules maxLength test", () => {
    it("tests maxLengthRule return true", () => {
        const validationRules = useRules();

        const txtRule = validationRules.maxLengthRule(10, errorMessage);
        const txt10 = "Lorem ipsu";

        expect(txtRule(txt10)).toBe(true);
    });
    it("tests maxLengthRule return error", () => {
        const validationRules = useRules();

        const txtRule = validationRules.maxLengthRule(10, errorMessage);
        const txt11 = "Lorem ipsum";

        expect(txtRule(txt11)).toBe(errorMessage);
    });
});
