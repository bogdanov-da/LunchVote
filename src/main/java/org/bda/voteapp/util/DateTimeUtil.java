package org.bda.voteapp.util;

import java.time.*;

public class DateTimeUtil {
    public static Clock clock = Clock.systemDefaultZone();

    public static LocalDateTime atStartOfDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : LocalDateTime.of(1, 1, 1, 0, 0);
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plusDays(1).atStartOfDay() : LocalDateTime.of(3000, 1,
                1, 0, 0);
    }

    public static void setFixedTime(String fixedTime) {
        clock = Clock.fixed(Instant.parse(LocalDate.now() + "T" + fixedTime + ".00Z"), ZoneId.of("UTC"));
    }

    public static boolean isBeforeTime() {
        return LocalTime.now(clock).isBefore(LocalTime.of(11, 0));
    }

    public static void setDefaultTime() {
        clock = Clock.systemDefaultZone();
    }
}
