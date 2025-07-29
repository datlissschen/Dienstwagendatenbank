package project.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class HelperMethods {
    public static long convertToUnixTime(String timeString){
        // format: 2024-08-03T19:17:0
        String[] split = timeString.split("T");
        String[] zeitStringJahre = split[0].split("-");
        int year = Integer.parseInt(zeitStringJahre[0]);
        int month = Integer.parseInt(zeitStringJahre[1]);
        int day = Integer.parseInt(zeitStringJahre[2]);

        String[] zeitStringTage = split[1].split(":");
        int hour = Integer.parseInt(zeitStringTage[0]);
        int minute = Integer.parseInt(zeitStringTage[1]);
        int second = Integer.parseInt(zeitStringTage[2]);

        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        return dateTime.atZone(ZoneId.of("Europe/Berlin")).toEpochSecond();
    }
}
