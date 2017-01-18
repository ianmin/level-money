package capitalone.interview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by minchanglong on 1/14/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private String error;

    public Error(){}

    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Error{" +
                "error='" + error + '\'' +
                '}';
    }
}
