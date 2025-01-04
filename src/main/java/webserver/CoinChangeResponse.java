package webserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
public class CoinChangeResponse {
    @JsonProperty
    private List<BigDecimal> combination;

    public CoinChangeResponse(List<BigDecimal> combination) {
        this.combination = combination;
    }

    public List<BigDecimal> getCombination() {
        return combination;
    }
}
