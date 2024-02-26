package de.muenchen.oss.praktikumsplaner.configuration;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class CustomWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomWebExceptionHandler.class);
    private static final String INVALID_GRANT = "[invalid_grant]";

    @Getter
    @Setter
    private class ErrorResponse {
        int status;
        String error;

        public ErrorResponse(int status, String error) {
            this.status = status;
            this.error = error;
        }
    }

    public CustomWebExceptionHandler(final ErrorAttributes errorAttributes,
                                     final WebProperties webProperties,
                                     final ApplicationContext applicationContext,
                                     final ServerCodecConfigurer serverCodecConfigurer
    ) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {

        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable error = this.getError(request);
        if (error.getMessage().startsWith(INVALID_GRANT)) {
            logger.info("Invalidating session and sending 302.");
            request.exchange().getSession().subscribe(WebSession::invalidate);
            return ServerResponse.status(HttpStatus.FOUND).build();
        }

        if (error instanceof ResponseStatusException rse) {

            ErrorResponse errorResponse = new ErrorResponse(
                    rse.getStatusCode().value(),
                    rse.getMessage()
            );

            return ServerResponse.status(rse.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(errorResponse));
        } else {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.name()
            );
            return ServerResponse.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(errorResponse));
        }
    }
}
