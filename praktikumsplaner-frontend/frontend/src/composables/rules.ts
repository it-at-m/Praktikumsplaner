export function useRules() {
    function fileTypeRule(format: string, message: string) {
        return (value: File) => (value && value.type == format) || message;
    }

    function maxLengthRule(length: number, message = "error") {
        return (value: string) =>
            (value != null && value.length < length) || message;
    }

    function notEmptyRule(message = "error") {
        return (value: string) => (value && value.trim() != "") || message;
    }

    function notEmptyDateRule(message = "error") {
        return (value: string) => (value && value.trim() != "-") || message;
    }

    return {
        fileTypeRule,
        maxLengthRule,
        notEmptyRule,
        notEmptyDateRule,
    };
}
