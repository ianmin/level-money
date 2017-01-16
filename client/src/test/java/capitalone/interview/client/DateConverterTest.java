package capitalone.interview.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by minchanglong on 1/16/17.
 */
public class DateConverterTest {
    private DateConverter dateConverter;

    @Before
    public void setUp() throws Exception {
        String dateTime = "2014-10-07T17:29:00.000Z";
        dateConverter = new DateConverter();
        dateConverter.setDateTimeStr(dateTime);
    }

    @Test
    public void getYearAndMonth() throws Exception {
        assertEquals( "2014-10", dateConverter.getYearAndMonth());
    }

}