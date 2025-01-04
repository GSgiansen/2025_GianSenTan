package webserver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/coin-change")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoinChangeResource {

    @POST
    public Response calculateCoinChange(CoinChangeRequest request) {
        List<BigDecimal> combination = coinChange(
                request.getCoins().toArray(new BigDecimal[0]),
                request.getAmount()
        );
        // handle if null case
        if (combination == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No valid combination possible.").build();
        }

        return Response.ok(new CoinChangeResponse(combination)).build();
    }
    public static List<BigDecimal> coinChange(BigDecimal[] coins, BigDecimal amount) {
        List<BigDecimal> combination = new ArrayList<>();
        // using one list in place
        if (backtrack(coins, amount, combination)) {
            Collections.reverse(combination);
            return combination;
        }
        return null;
    }

    public static boolean backtrack(BigDecimal[] coins, BigDecimal remaining, List<BigDecimal> combination) {
        if (remaining.compareTo(BigDecimal.ZERO) == 0) return true;
        if (remaining.compareTo(BigDecimal.ZERO) == -1) return false;

        for (int i = coins.length - 1; i >= 0; i--) {
            combination.add(coins[i]);
            if (backtrack(coins, remaining.subtract(coins[i]), combination)) return true;

            combination.remove(combination.size() - 1);

        }
        return false;
    }

}
