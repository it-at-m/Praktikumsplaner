import { useRules } from "@/composables/rules";

describe("rules test", () => {
    const errorMessage = "Error";
    it("tests fileFormatRules return true", () => {
        const validationRules = useRules();

        const txtRule = validationRules.fileFormatRule(
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

        const txtRule = validationRules.fileFormatRule(
            "audio/aac",
            errorMessage
        );
        const txtFile = new File(["foo"], "foo.txt", {
            type: "text/plain",
        });

        expect(txtRule(txtFile)).toBe(errorMessage);

        const nullFile = null;
        let undefinedFile;

        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        expect(txtRule(nullFile)).toBe(errorMessage);
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        expect(txtRule(undefinedFile)).toBe(errorMessage);
    });
});
