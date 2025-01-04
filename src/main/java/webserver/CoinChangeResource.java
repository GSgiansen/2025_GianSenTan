package webserver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/coin-change")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoinChangeResource {
//0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000
    public static boolean checkValidCoins(List<BigDecimal> lst) {
        HashSet<BigDecimal> map = new HashSet<>(List.of(
                new BigDecimal("0.01"), new BigDecimal("0.05"), new BigDecimal("0.1"),
                new BigDecimal("0.2"), new BigDecimal("0.5"), new BigDecimal("1"),
                new BigDecimal("2"), new BigDecimal("5"),
                new BigDecimal("10"), new BigDecimal("50"),
                new BigDecimal("100"), new BigDecimal("1000")));

        for (BigDecimal coin : lst) {
            if (coin == null || !map.contains(coin)) throw new RuntimeException("Invalid coins: " + coin);
        }
        return true;
    }

    public static boolean checkValidAmount(BigDecimal amt) {
        if (amt.compareTo(BigDecimal.ZERO) < 0 || amt.compareTo(new BigDecimal("10000")) > 0)
            throw new RuntimeException("Invalid amt, amount must be between 0 and 10000");
        return true;
    }

    @POST
    public Response calculateCoinChange(CoinChangeRequest request) {
        // do the checks for the request
        try {
            //1. coins must be within the range wanted
            checkValidCoins(request.getCoins());
            //2. amount must be within accepted range of 0 to 10000
            checkValidAmount(request.getAmount());

            List<BigDecimal> combination = coinChange(
                    request.getCoins().toArray(new BigDecimal[0]),
                    request.getAmount()
            );

            // handle if null case
            if (combination == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No valid combination possible.").build();
            }

            return Response.ok(new CoinChangeResponse(combination)).build();

        } catch (RuntimeException e) {
            // catch validation errors and return 400 response
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }



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
