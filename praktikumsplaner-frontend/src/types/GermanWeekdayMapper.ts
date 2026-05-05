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
  private germanWeekdayShortMap = {
    MONDAY: "Mo",
    TUESDAY: "Di",
    WEDNESDAY: "Mi",
    THURSDAY: "Do",
    FRIDAY: "Fr",
    SATURDAY: "Sa",
    SUNDAY: "So",
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

  public normalizeAndSort(daysString: string[]): string[] {
    const normalized = daysString.map((d) => d.trim().toUpperCase());
    return normalized.sort(
      (a, b) => this.dayOrder.indexOf(a) - this.dayOrder.indexOf(b)
    );
  }

  public getGermanDays(daysString: string[]): string[] {
    return this.normalizeAndSort(daysString).map(
      (day) =>
        this.germanWeekdayMap[day as keyof typeof this.germanWeekdayMap] || day
    );
  }

  public getGermanShortDays(daysString: string[]): string[] {
    return this.normalizeAndSort(daysString).map(
      (day) =>
        this.germanWeekdayShortMap[
          day as keyof typeof this.germanWeekdayShortMap
        ] || day
    );
  }
}
