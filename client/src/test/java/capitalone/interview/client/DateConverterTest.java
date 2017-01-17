package capitalone.interview.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by minchanglong on 1/16/17.
 */
public class DateConverterTest {
    @Test
    public void getYearAndMonth() throws Exception {
        assertEquals( "2014-10", DateConverter.getYearAndMonth("2014-10-07T17:29:00.000Z"));
    }
}