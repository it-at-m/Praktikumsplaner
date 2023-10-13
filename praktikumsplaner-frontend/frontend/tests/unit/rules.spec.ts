import { useRules } from "@/composables/rules";
import { describe } from "vitest";

describe("rules test", () => {
    const errorMessage = "Error";
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
