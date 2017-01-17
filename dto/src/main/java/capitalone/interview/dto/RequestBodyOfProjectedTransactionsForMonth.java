package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by minchanglong on 1/16/17.
 */
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestBodyOfProjectedTransactionsForMonth extends Credential{

    private long year;

    private long month;

    @Autowired
    public RequestBodyOfProjectedTransactionsForMonth() {
        super();
    }

    public void setYear(long year) {
        this.year = year;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getYear() {
        return year;
    }

    public long getMonth() {
        return month;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this)
                .add("year", getYear())
                .add("month",getMonth())
                .add("args", super.getArgs()).toString();
    }
}
