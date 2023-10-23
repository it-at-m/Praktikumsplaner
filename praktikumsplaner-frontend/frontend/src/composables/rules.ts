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

    function dateBeforeRule(date?: string, message = "error") {
        return (value: string) => {
            if (date == undefined) return true;
            const dateComponents = value.split(".");
            const dateAsISOString =
                dateComponents[2] +
                "-" +
                dateComponents[1] +
                "-" +
                dateComponents[0];
            return (
                (value && new Date(dateAsISOString) < new Date(date)) || message
            );
        };
    }

    function dateAfterRule(date?: string, message = "error") {
        return (value: string) => {
            if (date == undefined) return true;
            const dateComponents = value.split(".");
            const dateAsISOString =
                dateComponents[2] +
                "-" +
                dateComponents[1] +
                "-" +
                dateComponents[0];
            return (
                (value && new Date(dateAsISOString) > new Date(date)) || message
            );
        };
    }

    return {
        fileTypeRule,
        maxLengthRule,
        dateBeforeRule,
        dateAfterRule,
        notEmptyRule,
        notEmptyDateRule,
    };
}
