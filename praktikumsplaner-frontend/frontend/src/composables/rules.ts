export function useRules() {
    function fileTypeRule(format: string, message: string) {
        return (value: File) => (value && value.type == format) || message;
    }

    function maxLengthRule(length: number, message: string) {
        return (value: string) =>
            (value != null && value.length < length) || message;
    }

    function notEmptyRule(message: string) {
        return (value: string) => (value && value.trim() != "") || message;
    }

    function notEmptyDateRule(message: string) {
        return (value: string) => (value && value.trim() != "-") || message;
    }

    function dateBeforeRule(date?: string, message?: string) {
        return (value: string) => {
            if (date == undefined) return true;
            console.log("DateBeforeRule: Date=" + date + "; Value=" + value);
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

    function dateAfterRule(date?: string, message?: string) {
        return (value: string) => {
            if (date == undefined) return true;
            console.log("DateAfterRule: Date=" + date + "; Value=" + value);
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
