export function useRules() {
    function fileTypeRule(acceptedType: string, errorMessage: string) {
        return (fileList: File[] | null | undefined) => {
            if (!fileList || fileList.length === 0) {
                return true;
            }
            const file = fileList[0];
            if (!file) return true;
            return file.type === acceptedType || errorMessage;
        };
    }

    function fileRequiredRule(message = "Eine Datei ist erforderlich.") {
        return (value: File[] | null | undefined) => {
            return (
                (value !== null && value !== undefined && value.length > 0) ||
                message
            );
        };
    }

    function minLengthRule(length: number, message = "error") {
        return (value: string | null | undefined) =>
            !value ||
            ((value || value?.trim() == "") && value.length >= length) ||
            message;
    }

    function maxLengthRule(length: number, message = "error") {
        return (value: string | null | undefined) =>
            !value ||
            ((value || value?.trim() == "") && value.length <= length) ||
            message;
    }

    function notEmptyDateRule(message = "error") {
        return (value: string | null | undefined) =>
            (value && value.trim() != "-") || message;
    }

    function notEmptyRule(message = "error") {
        return (value: string | null | undefined) =>
            (value && value.trim() != "") || message;
    }

    function notEmptyBooleanRule(message = "error") {
        return (value: boolean | null | undefined) =>
            value != null || value != undefined || message;
    }

    function regexRule(regex: RegExp, message = "error") {
        return (value: string | null | undefined) =>
            (value && regex.test(value)) || message;
    }

    function notEmptyArrayRule(message = "error") {
        return (value: never[] | null | undefined) =>
            (value && value.length > 0) || message;
    }

    return {
        fileTypeRule,
        minLengthRule,
        maxLengthRule,
        notEmptyDateRule,
        notEmptyRule,
        notEmptyBooleanRule,
        fileRequiredRule,
        regexRule,
        notEmptyArrayRule,
    };
}
