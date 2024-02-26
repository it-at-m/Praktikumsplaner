package de.muenchen.oss.praktikumsplaner.configuration;

import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class CustomWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomWebExceptionHandler.class);
    private static final String INVALID_GRANT = "[invalid_grant]";

    public CustomWebExceptionHandler(final ErrorAttributes errorAttributes,
            final WebProperties webProperties,
            final ServerProperties serverProperties,
            final ApplicationContext applicationContext,
            final ObjectProvider<ViewResolver> viewResolvers,
            final ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, webProperties.getResources(), serverProperties.getError(), applicationContext);
        this.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()));
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        final Throwable error = this.getError(request);
        if (isInvalidGrant(error)) {
            logger.info("Invalidieren der WebSession und Antwort mit 302 (FOUND).");
            request.exchange().getSession().subscribe(WebSession::invalidate);
            return ServerResponse.status(HttpStatus.FOUND).build();
        }
        return super.renderErrorResponse(request);
    }

    private boolean isInvalidGrant(Throwable error) {
        return error != null && error.getMessage() != null && error.getMessage().startsWith(INVALID_GRANT);
    }

}
