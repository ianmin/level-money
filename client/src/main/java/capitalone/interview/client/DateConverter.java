package capitalone.interview.client;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;

/**
 * Created by minchanglong on 1/15/17.
 */
@Component
public class DateConverter {

    private String dateTimeStr;

    private OffsetDateTime offsetDateTime;

    public void setDateTimeStr(String dateTimeStr) {
        this.dateTimeStr = dateTimeStr;
        convertDateTime();
    }

    private void convertDateTime() {
        this.offsetDateTime = OffsetDateTime.parse(dateTimeStr);
    }

    public String getYearAndMonth() {
        DecimalFormat monthFormat= new DecimalFormat("00");
        return offsetDateTime.getYear() + "-" + monthFormat.format(offsetDateTime.getMonth().getValue());
    }
}
