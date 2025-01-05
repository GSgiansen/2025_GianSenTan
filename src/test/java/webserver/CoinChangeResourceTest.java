package webserver;

import io.dropwizard.testing.junit5.DropwizardAppExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testing for the coin change programme
 */
public class CoinChangeResourceTest {

    @RegisterExtension
    static final DropwizardAppExtension<HelloWorldConfiguration> APP = new DropwizardAppExtension<>(
            HelloWorldApplication.class,
            "src/main/resources/config.yml"
    );

    /**
     * Checking for amount for negative inputs less than 0 returns a 400 BAD REQUEST
     */

    @Test
    public void testNegativeAmount() {
        CoinChangeRequest request = new CoinChangeRequest();
        request.setCoins(List.of(new BigDecimal("0.1"), new BigDecimal("1"), new BigDecimal("5")));
        request.setAmount(new BigDecimal("-50"));

        Response response = APP.client().target("http://localhost:8080/coin-change")
                .request()
                .post(Entity.json(request));

        assertThat(response.getStatus()).isEqualTo(400);
        String errorMessage = response.readEntity(String.class);
        assertThat(errorMessage).isEqualTo("Invalid amt: amount must be between 0 and 10000 (inclusive).");
    }

    /**
     * Checking for amount for inputs greater than 1000 returns a 400 BAD REQUEST
     */

    @Test
    public void testAmountGreaterThan10000() {
        CoinChangeRequest request = new CoinChangeRequest();
        request.setCoins(List.of(new BigDecimal("0.1"), new BigDecimal("2"), new BigDecimal("5")));
        request.setAmount(new BigDecimal("15000"));

        Response response = APP.client().target("http://localhost:8080/coin-change")
                .request()
                .post(Entity.json(request));

        assertThat(response.getStatus()).isEqualTo(400);
        String errorMessage = response.readEntity(String.class);
        assertThat(errorMessage).isEqualTo("Invalid amt: amount must be between 0 and 10000 (inclusive).");
    }

    /**
     *
     * Ensures that sending a request with an invalid coin (numeric) returns a 400 BAD REQUEST.
     */
    @Test
    public void testInvalidCoinsNumeric() {
        CoinChangeRequest request = new CoinChangeRequest();
        request.setCoins(List.of(new BigDecimal("0.1"), new BigDecimal("2"), new BigDecimal("999")));  // Invalid coin "999"
        request.setAmount(new BigDecimal("500"));

        Response response = APP.client().target("http://localhost:8080/coin-change")
                .request()
                .post(Entity.json(request));

        assertThat(response.getStatus()).isEqualTo(400);
        String errorMessage = response.readEntity(String.class);
        assertThat(errorMessage).isEqualTo("Invalid coins: 999");
    }

    /**
     *
     * Ensures that sending a request with an invalid coin (String) returns a 400 BAD REQUEST.
     */
    @Test
    public void testInvalidCoinStringMeow() {
        String requestJson = "{"
                + "\"coins\": [\"0.1\", \"2\", \"Meow\"],"
                + "\"amount\": 100"
                + "}";

        Response response = APP.client().target("http://localhost:8080/coin-change")
                .request()
                .post(Entity.json(requestJson));

        assertThat(response.getStatus()).isEqualTo(400);
        String errorMessage = response.readEntity(String.class);

        assertThat(errorMessage).isEqualTo("Invalid input: Meow is not a valid number.");
    }

}
