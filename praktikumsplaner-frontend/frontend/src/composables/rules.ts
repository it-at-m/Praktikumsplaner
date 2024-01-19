export function useRules() {
    function fileTypeRule(format: string, message = "error") {
        return (value: File | null | undefined) =>
            !value || (value && value.type == format) || message;
    }

    function fileRequiredRule(message = "error") {
        return (value: File | null | undefined) =>
            value != null || value != undefined || message;
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

    function emailRule(message = "error") {
        return (value: string | null | undefined) =>
            (value &&
                /^[A-Z0-9._%+-]{1,64}@[A-Z0-9.-]{1,63}\.[A-Z]{1,63}$/i.test(
                    value
                )) ||
            message;
    }

    function regexRule(regex: RegExp, message = "error") {
        return (value: string | null | undefined) =>
            (value && regex.test(value)) || message;
    }

    return {
        fileTypeRule,
        minLengthRule,
        maxLengthRule,
        notEmptyDateRule,
        notEmptyRule,
        emailRule,
        notEmptyBooleanRule,
        fileRequiredRule,
        regexRule,
    };
}
