export function useRules() {
    function fileTypeRule(format: string, message: string) {
        return (value: File) => (value && value.type == format) || message;
    }

    function notEmptyRule(message: string) {
        return (value: string) => (value && value.trim() != "") || message;
    }

    function notEmptyRuleAndVisible(visible: boolean, message: string) {
        return (value: string) => {
            return !visible || (value && value.trim() != "") || message;
        };
    }

    return { fileTypeRule, notEmptyRule, notEmptyRuleAndVisible };
}
