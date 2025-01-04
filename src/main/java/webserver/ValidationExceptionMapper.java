package webserver;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

    @Provider
    public class ValidationExceptionMapper implements ExceptionMapper<InvalidFormatException> {

        @Override
        public Response toResponse(InvalidFormatException exception) {
            String errorMessage = "Invalid input: " + exception.getValue() + " is not a valid number.";
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
    }


