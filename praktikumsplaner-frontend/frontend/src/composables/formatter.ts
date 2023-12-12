import moment from "moment";

export function useFormatter() {
    function startingCharUpperCase(text: string): string {
        return text
            ? text.toLowerCase().replace(/^\w/, (c) => c.toUpperCase())
            : "";
    }

    function formatDate(date: Date): string {
        return date ? moment(date).format("L") : "";
    }

    function formatDateFromString(date: string): string {
        return date ? moment(date).format("DD.MM.yyyy") : "";
    }

    return { startingCharUpperCase, formatDate, formatDateFromString };
}
