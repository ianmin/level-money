package capitalone.interview.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by minchanglong on 1/15/17.
 */
public class TokenTest {

    private Token actualToken;

    @Before
    public void setUp() throws Exception {
        actualToken = new Token(
                "no-error",
                (long) 1110590645,
                "A42DEA88C72B62A9BFEEC58048F8E5AB",
                "finished",
                "active",
                false);
    }

    @Test
    public void testToken() throws IOException {
        String expectedJsonString = "{\"error\": \"no-error\"," +
                " \"uid\": 1110590645," +
                " \"token\": \"A42DEA88C72B62A9BFEEC58048F8E5AB\"," +
                " \"onboarding-stage\": \"finished\"," +
                " \"agg-status\": \"active\"," +
                " \"has-accounts-linked\": false}";
        ObjectMapper mapper = new ObjectMapper();
        Token jsonTokenObj = mapper.readValue(expectedJsonString, Token.class);
        assertEquals(jsonTokenObj, actualToken);

        String expectedStr =
                "Token{error=no-error, uid=1110590645, token=A42DEA88C72B62A9BFEEC58048F8E5AB, " +
                "onboarding-stage=finished, agg-status=active, has-accounts-linked=false}";
        assertEquals(expectedStr, actualToken.toString());
    }
}