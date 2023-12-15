export default class GermanWeekdayMapper {
    private germanWeekdayMap = {
        MONDAY: "Montag",
        TUESDAY: "Dienstag",
        WEDNESDAY: "Mittwoch",
        THURSDAY: "Donnerstag",
        FRIDAY: "Freitag",
        SATURDAY: "Samstag",
        SUNDAY: "Sonntag",
    };

    private dayOrder = [
        "MONDAY",
        "TUESDAY",
        "WEDNESDAY",
        "THURSDAY",
        "FRIDAY",
        "SATURDAY",
        "SUNDAY",
    ];

    public getGermanDays(daysString: string[]): string[] {
        const sortedDays = daysString.sort(
            (a, b) => this.dayOrder.indexOf(a) - this.dayOrder.indexOf(b)
        );
        sortedDays.forEach((day) => {
            day.trim().toUpperCase();
        });

        return sortedDays.map(
            (day) =>
                this.germanWeekdayMap[
                    day as keyof typeof this.germanWeekdayMap
                ] || day
        );
    }
}
