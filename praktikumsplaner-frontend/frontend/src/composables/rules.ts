export function useRules() {
    function fileTypeRule(format: string, message: string) {
        return (value: File) => (value && value.type == format) || message;
    }

    function maxLengthRule(length: number, message = "error") {
        return (value: string | null) =>
            (value != null && value.length <= length) || message;
    }

    function notEmptyDateRule(message = "error") {
        return (value: string) => (value && value.trim() != "-") || message;
    }

    function notEmptyRule(message: string) {
        return (value: string) => (value && value.trim() != "") || message;
    }

    function notEmptyBooleanRule(message: string) {
        return (value: boolean | undefined) => value !== undefined || message;
    }

    function emailRule() {
        return (v: string) =>
            /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(v) ||
            "keine Valide E-Mail!";
    }

    return {
        fileTypeRule,
        maxLengthRule,
        notEmptyDateRule,
        notEmptyRule,
        emailRule,
        notEmptyBooleanRule,
    };
}
