package webserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class CoinChangeRequest {
    @JsonProperty
    private List<BigDecimal> coins;

    @JsonProperty
    private BigDecimal amount;

    public List<BigDecimal> getCoins() {
        return coins;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
