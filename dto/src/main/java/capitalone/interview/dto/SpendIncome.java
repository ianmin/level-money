package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by minchanglong on 1/15/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpendIncome {

    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

    private static final double RATE = 20000.00;

    @JsonProperty("spent")
    private String spendStr;

    @JsonIgnore
    private long spendLong;

    @JsonProperty("income")
    private String incomeStr;

    @JsonIgnore
    private long incomeLong;

    public SpendIncome() {
        this.spendLong = 0;
        this.incomeLong = 0;
        this.spendStr = numberFormat.format(spendLong);
        this.incomeStr = numberFormat.format(incomeLong);
    }

    public void setSpendLong(long spendLong) {
        this.spendLong = spendLong;
        this.spendStr = numberFormat.format(spendLong / RATE);
    }

    public void setIncomeLong(long incomeLong) {
        this.incomeLong = incomeLong;
        this.incomeStr = numberFormat.format(incomeLong / RATE);

    }

    public Long getSpendLong() {
        return spendLong;
    }

    public Long getIncomeLong() {
        return incomeLong;
    }

    public String getSpendStr() {
        return spendStr;
    }

    public String getIncomeStr() {
        return incomeStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpendIncome that = (SpendIncome) o;
        return spendLong == that.spendLong &&
                incomeLong == that.incomeLong;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(spendLong, incomeLong);
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this)
                .add("spend", getSpendStr())
                .add("income", getIncomeStr()).toString();
    }
}
