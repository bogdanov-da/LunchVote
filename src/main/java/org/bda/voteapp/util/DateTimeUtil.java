package org.bda.voteapp.util;

import java.time.*;

public class DateTimeUtil {
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);

    public static LocalDateTime atStartOfDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plusDays(1).atStartOfDay() : MAX_DATE;
    }

    public static LocalTime setLocalTime(String time) {
        Clock clock = Clock.fixed(Instant.parse(LocalDate.now() + "T" + time + ".00Z"), ZoneId.of("UTC"));
        return LocalTime.now(clock);
    }
}
