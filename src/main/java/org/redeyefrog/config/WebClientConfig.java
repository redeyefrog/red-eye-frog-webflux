package org.redeyefrog.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Value("${mock.api.url}")
    String apiUrl;

    @Bean
    WebClient webClient(ClientHttpConnector clientHttpConnector) {
        return WebClient.builder()
                        .baseUrl(apiUrl)
                        .clientConnector(clientHttpConnector)
                        .defaultHeaders(httpHeaders -> httpHeaders.addAll(headers()))
                        .build();
    }

    @Bean
    ClientHttpConnector clientHttpConnector(HttpClient httpClient) {
        return new ReactorClientHttpConnector(httpClient);
    }

    @Bean
    HttpClient httpClient(SslContext sslContext) {
        return HttpClient.create()
//                         .create(connectionProvider())
                         // read and write timeout
                         .doOnConnected(connection ->
                                 connection.addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
                                           .addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS))
                         )
                         // connection timeout
                         .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                         // response timeout
                         .responseTimeout(Duration.ofSeconds(30))
                         .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext))
                         .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
    }

    ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("frog-pool")
                                 .maxConnections(500)
                                 .maxIdleTime(Duration.ofSeconds(20))
                                 .maxLifeTime(Duration.ofSeconds(60))
                                 .pendingAcquireTimeout(Duration.ofSeconds(60))
                                 .evictInBackground(Duration.ofSeconds(120))
                                 .build();
    }

    @Bean
    SslContext sslContext() throws SSLException {
        return SslContextBuilder.forClient()
                                .startTls(true)
                                .trustManager(InsecureTrustManagerFactory.INSTANCE) // Never use this TrustManagerFactory in production.
                                .build();
    }

    private TrustManager trustManager() {
        return new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

        };
    }

    private MultiValueMap<String, String> headers() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
