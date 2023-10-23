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

    describe("DateBeforeRule Tests", () => {
        it("tests dateBeforeRule with correct Dates", () => {
            const validationRules = useRules();
            const dateBeforeRule = validationRules.dateBeforeRule("2020-10-10", errorMessage);
            let valueToCheck: string = "09.10.2020";

            expect(dateBeforeRule(valueToCheck)).toBe(true);
        });
        it("tests dateBeforeRule with same Date", () => {
            const validationRules = useRules();
            const dateBeforeRule = validationRules.dateBeforeRule("2020-10-10", errorMessage);
            let valueToCheck: string = "10.10.2020";

            expect(dateBeforeRule(valueToCheck)).toBe(errorMessage);
        });
        it("tests dateBeforeRule with incorrect Dates", () => {
            const validationRules = useRules();
            const dateBeforeRule = validationRules.dateBeforeRule("2020-10-10", errorMessage);
            let valueToCheck: string = "11.10.2020";

            expect(dateBeforeRule(valueToCheck)).toBe(errorMessage);
        });
        it("tests dateBeforeRule with undefined", () => {
            const validationRules = useRules();
            const dateBeforeRule = validationRules.dateBeforeRule(undefined, errorMessage);
            let valueToCheck: string = "09.10.2020";

            expect(dateBeforeRule(valueToCheck)).toBe(true);
        });
        it("tests dateBeforeRule with Dates with a bad format", () => {
            const validationRules = useRules();
            const dateBeforeRule = validationRules.dateBeforeRule("2020-10-10", errorMessage);
            let valueToCheck: string = "09.10.20";

            expect(dateBeforeRule(valueToCheck)).toBe(errorMessage);
        });
    });

    describe("DateAfterRule Tests", () => {
        it("tests dateAfterRule with correct Dates", () => {
            const validationRules = useRules();
            const dateAfterRule = validationRules.dateAfterRule("2020-10-10", errorMessage);
            let valueToCheck: string = "11.10.2020";

            expect(dateAfterRule(valueToCheck)).toBe(true);
        });
        it("tests dateAfterRule with same Date", () => {
            const validationRules = useRules();
            const dateAfterRule = validationRules.dateAfterRule("2020-10-10", errorMessage);
            let valueToCheck: string = "10.10.2020";

            expect(dateAfterRule(valueToCheck)).toBe(errorMessage);
        });
        it("tests dateAfterRule with incorrect Dates", () => {
            const validationRules = useRules();
            const dateAfterRule = validationRules.dateAfterRule("2020-10-10", errorMessage);
            let valueToCheck: string = "09.10.2020";

            expect(dateAfterRule(valueToCheck)).toBe(errorMessage);
        });
        it("tests dateAfterRule with undefined", () => {
            const validationRules = useRules();
            const dateAfterRule = validationRules.dateAfterRule(undefined, errorMessage);
            let valueToCheck: string = "11.10.2020";

            expect(dateAfterRule(valueToCheck)).toBe(true);
        });
        it("tests dateAfterRule with Dates with a bad format", () => {
            const validationRules = useRules();
            const dateAfterRule = validationRules.dateAfterRule("2020-10-10", errorMessage);
            let valueToCheck: string = "11.10.20";

            expect(dateAfterRule(valueToCheck)).toBe(errorMessage);
        });
    });
});
