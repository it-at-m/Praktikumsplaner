export function useRules() {
    function fileTypeRule(format: string, message: string) {
        return (value: File) => (value && value.type == format) || message;
    }

    function maxLengthRule(length: number, message = "error") {
        return (value: string) =>
            (value != null && value.length < length) || message;
    }

    function notEmptyDateRule(message = "error") {
        return (value: string) => (value && value.trim() != "-") || message;
    }

    function notEmptyRule(message: string) {
        return (value: string) => (value && value.trim() != "") || message;
    }

    function notEmptyRuleAndVisible(visible: boolean, message: string) {
        return (value: string) => {
            return !visible || (value && value.trim() != "") || message;
        };
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
        notEmptyRuleAndVisible,
        emailRule,
    };
}
