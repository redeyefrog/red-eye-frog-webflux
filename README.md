# Spring WebFlux

### Logging WebClient Calls
* Spring Boot 2.2.6
```java
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        TcpClient tcpClient = TcpClient.create()
                                       .doOnConnected(conn ->
                                               conn.addHandler(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                                   .addHandler(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                       )
                                       .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        HttpClient httpClient = HttpClient.from(tcpClient)
                                          .tcpConfiguration(tcp -> tcp.bootstrap(bootstrap -> BootstrapHandlers.updateLogSupport(bootstrap, new CustomLoggingHandler(HttpClient.class))));
        WebClient.builder()
                 .clientConnector(new ReactorClientHttpConnector(httpClient))
                 .build();
    }

}

public class CustomLoggingHandler extends LoggingHandler {

    public CustomLoggingHandler(Class<?> clazz) {
        super(clazz);
    }

    @Override
    protected String format(ChannelHandlerContext ctx, String eventName, Object arg) {
        if (arg instanceof ByteBuf msg) {
            return msg.toString(StandardCharsets.UTF_8);
        }
        return super.format(ctx, eventName, arg);
    }

}
```
* Spring Boot 2.4.0
```java
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                                          // doOnConnected and responseTimeout choose one to use
                                          .doOnConnected(conn ->
                                                  conn.addHandler(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                                      .addHandler(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                          )
                                          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                                          .responseTimeout(Duration.ofMillis(5000))
                                          .wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        return WebClient.builder()
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }

}
```

### Reference
1. Guide
   * [Spring Reactive Guide](https://www.baeldung.com/spring-reactive-guide)
2. WebFlux
   * [Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
   * [Spring WebFlux Filters](https://www.baeldung.com/spring-webflux-filters)
   * [Log Request/Response Body in Spring WebFlux with Kotlin](https://www.baeldung.com/kotlin/spring-webflux-log-request-response-body)
   * [Handling Errors in Spring WebFlux](https://www.baeldung.com/spring-webflux-errors)
   * [Validation for Functional Endpoints in Spring 6](https://www.baeldung.com/spring-functional-endpoints-validation)
   * [Difference Between Flux and Mono](https://www.baeldung.com/java-reactor-flux-vs-mono)
   * [Project Reactor: map() vs flatMap()](https://www.baeldung.com/java-reactor-map-flatmap)
3. WebClient
   * [WebClient](https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html)
   * [Spring 5 WebClient](https://www.baeldung.com/spring-5-webclient)
   * [Set a Timeout in Spring 5 Webflux WebClient](https://www.baeldung.com/spring-webflux-timeout)
   * [Logging Spring WebClient Calls](https://www.baeldung.com/spring-log-webclient-calls)
   * [Spring WebClient Requests with Parameters](https://www.baeldung.com/webflux-webclient-parameters)
   * [Reactor Netty Reference Guide Connection Pool](https://projectreactor.io/docs/netty/snapshot/reference/index.html#_connection_pool_2)
   * [how to log Spring 5 WebClient call](https://stackoverflow.com/questions/46154994/how-to-log-spring-5-webclient-call "how to log Spring 5 WebClient call")