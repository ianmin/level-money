package capitalone.interview.client;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;

/**
 * Created by minchanglong on 1/15/17.
 */
@Component
public class DateConverter {

    private static final Long EPOCH_SECOND_OF_TWENTY_HOURS = 86400L;

    public static String getYearAndMonth(String dateTimeStr) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeStr);
        DecimalFormat monthFormat= new DecimalFormat("00");
        return offsetDateTime.getYear() + "-" + monthFormat.format(offsetDateTime.getMonth().getValue());
    }

    public static Long getEpochSecond(String dateTimeStr) {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeStr);
        return offsetDateTime.toInstant().getEpochSecond();
    }

    public static boolean isWithinTwentyFourHours(Long epochSecondLeft, Long epochSecondRight) {
        return Math.abs(epochSecondLeft - epochSecondRight) <= EPOCH_SECOND_OF_TWENTY_HOURS;
    }
}
