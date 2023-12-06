import { useRules } from "@/composables/rules";
import { describe } from "vitest";
const errorMessage = "Error";
const validationRules = useRules();
describe("rules fileFormat test", () => {
    it("tests fileFormatRules return true", () => {
        const txtRule = validationRules.fileTypeRule(
            "text/plain",
            errorMessage
        );
        const txtFile = new File(["foo"], "foo.txt", {
            type: "text/plain",
        });

        expect(txtRule(txtFile)).toBe(true);
    });
    const aacRule = validationRules.fileTypeRule("audio/aac", errorMessage);
    it("tests fileFormatRules returns Error when using wrong fileformat", () => {
        const txtFile = new File(["foo"], "foo.txt", {
            type: "text/plain",
        });

        expect(aacRule(txtFile)).toBe(errorMessage);
    });
    it("tests fileFormatRules with null", () => {
        expect(aacRule(null)).toBe(true);
    });
    it("tests fileFormatRules with undefined", () => {
        expect(aacRule(undefined)).toBe(true);
    });
});
describe("rules fileRequired test", () => {
    const fileRule = validationRules.fileRequiredRule(errorMessage);
    it("tests fileRequired return true", () => {
        const txtFile = new File(["foo"], "foo.txt", {
            type: "text/plain",
        });

        expect(fileRule(txtFile)).toBe(true);
    });
    it("tests fileRequired with null", () => {
        expect(fileRule(null)).toBe(errorMessage);
    });
    it("tests fileRequired with undefined", () => {
        expect(fileRule(undefined)).toBe(errorMessage);
    });
});
describe("rules maxLength test", () => {
    const maxLength10Rule = validationRules.maxLengthRule(10, errorMessage);
    it("tests maxLengthRule return true", () => {
        const txt10 = "Lorem ipsu";

        expect(maxLength10Rule(txt10)).toBe(true);
    });
    it("tests maxLengthRule return error when over max length", () => {
        const txt11 = "Lorem ipsum";

        expect(maxLength10Rule(txt11)).toBe(errorMessage);
    });
    it("tests maxLengthRule return error when null", () => {
        expect(maxLength10Rule(null)).toBe(true);
    });
    it("tests maxLengthRule return error when undefined", () => {
        expect(maxLength10Rule(undefined)).toBe(true);
    });
});
describe("rules notEmptyDate test", () => {
    const rule = validationRules.notEmptyDateRule(errorMessage);
    it("tests notEmptyDateRule return true", () => {
        const date = "2023-10-10";

        expect(rule(date)).toBe(true);
    });
    it("tests notEmptyDateRule return error when date is empty", () => {
        const date = " - ";

        expect(rule(date)).toBe(errorMessage);
    });
    it("tests notEmptyDateRule return error when empty", () => {
        const date = "";

        expect(rule(date)).toBe(errorMessage);
    });
    it("tests notEmptyDateRule return error when null", () => {
        expect(rule(null)).toBe(errorMessage);
    });
    it("tests notEmptyDateRule return error when undefined", () => {
        expect(rule(undefined)).toBe(errorMessage);
    });
});
describe("rules notEmpty test", () => {
    const rule = validationRules.notEmptyRule(errorMessage);
    it("tests notEmptyRule return true", () => {
        const text = "Something";

        expect(rule(text)).toBe(true);
    });
    it("tests notEmptyRule return error when empty", () => {
        const text = "";

        expect(rule(text)).toBe(errorMessage);
    });
    it("tests notEmptyRule return error when null", () => {
        expect(rule(null)).toBe(errorMessage);
    });
    it("tests notEmptyRule return error when undefined", () => {
        expect(rule(undefined)).toBe(errorMessage);
    });
});
describe("rules notEmptyBoolean test", () => {
    const rule = validationRules.notEmptyBooleanRule(errorMessage);
    it("tests notEmptyBooleanRule return true", () => {
        const bool = true;

        expect(rule(bool)).toBe(true);
    });
    it("tests notEmptyBooleanRule return true", () => {
        const bool = false;

        expect(rule(bool)).toBe(true);
    });
    it("tests notEmptyBooleanRule return error when null", () => {
        expect(rule(null)).toBe(errorMessage);
    });
    it("tests notEmptyBooleanRule return error when undefined", () => {
        expect(rule(undefined)).toBe(errorMessage);
    });
});
describe("rules email test", () => {
    const rule = validationRules.emailRule(errorMessage);
    it("tests emailRule return true", () => {
        const email = "ich_bin@eine.email";

        expect(rule(email)).toBe(true);
    });
    it("tests emailRule return error when invalid email", () => {
        const email1 = "ich_bin_keine.email";
        const email2 = "@ich_bin_keine.email";
        const email3 = "ich_bin_keine@.email";

        expect(rule(email1)).toBe(errorMessage);
        expect(rule(email2)).toBe(errorMessage);
        expect(rule(email3)).toBe(errorMessage);
    });
    it("tests emailRule return error when null", () => {
        expect(rule(null)).toBe(errorMessage);
    });
    it("tests emailRule return error when undefined", () => {
        expect(rule(undefined)).toBe(errorMessage);
    });
});
