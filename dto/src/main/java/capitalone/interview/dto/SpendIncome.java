package capitalone.interview.dto;

import com.google.common.base.Objects;
import javafx.util.converter.CurrencyStringConverter;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by minchanglong on 1/15/17.
 */
public class SpendIncome {

    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

    private static final double RATE = 20000.00;

    private String spendStr;

    private long spendLong;

    private String incomeStr;

    private long incomeLong;

    public SpendIncome() {
        this.spendLong = 0;
        this.incomeLong = 0;
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
    public String toString() {
        return "SpendIncome{" +
                "spendStr='" + spendStr + '\'' +
                ", incomeStr='" + incomeStr + '\'' +
                '}';
    }
}
