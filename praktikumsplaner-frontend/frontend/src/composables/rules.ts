export function useRules() {
    function fileFormatRule(format: string, message: string) {
        return (value: File) => (value && value.type == format) || message;
    }

    return { fileFormatRule };
}
