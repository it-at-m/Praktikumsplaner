export function useRules() {
    function fileTypeRule(format: string, message: string) {
        return (value: File) => (value && value.type == format) || message;
    }

    function maxLengthRule(length: number, message: string) {
        return (value: string) => (value && value.length < length) || message;
    }

    function notEmptyRule(message: string) {
        return (value: string) => (value && value.trim() != "") || message;
    }

    function dateBeforeRule(date: string, message: string) {
        return (value: string) =>
            (value && new Date(value) < new Date(date)) || message;
    }

    function dateAfterRule(date: string, message: string) {
        return (value: string) =>
            (value && new Date(value) > new Date(date)) || message;
    }

    return {
        fileTypeRule,
        maxLengthRule,
        dateBeforeRule,
        dateAfterRule,
        notEmptyRule,
    };
}
