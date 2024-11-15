package agricole.simulatore.mutuoCard.config;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    private static final Logger log = LoggerFactory.getLogger(WebClientConfig.class);

    HttpClient httpClient = HttpClient.create()
        .secure(sslSpec -> sslSpec.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));

    @Bean
    public WebClient.Builder webClientbuilder() {
        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .filter(logRequest())
            .filter(logResponse());
    }

    // Metodo per il filtro di logging delle richieste
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                .forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
            return Mono.just(clientRequest);
        });
    }

    // Metodo per il filtro di logging delle risposte
    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response status code: {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }
}