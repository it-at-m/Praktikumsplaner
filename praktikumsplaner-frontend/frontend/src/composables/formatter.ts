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

    function formatStringDate(date: string | undefined): string {
        if (!date) return "";
        const parts = date.split("-");
        return parts[2] + "." + parts[1] + "." + parts[0];
    }

    return { startingCharUpperCase, formatDate, formatStringDate };
}
