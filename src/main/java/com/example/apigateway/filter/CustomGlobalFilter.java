package com.example.apigateway.filter;

import com.example.apigateway.stream.LogService;
import com.example.apigateway.validator.ApisValidator;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomGlobalFilter implements GlobalFilter, Ordered {


    private final ApisValidator apisValidator;
    private final LogService logService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest req = exchange.getRequest();
        RequestPath path = req.getPath();
       // log.info(exchange.);
        log.info("RequestPAth {}", path.toString());
        URI url = req.getURI();
        String host = url.getHost();
        log.info("host {}", host);
        HttpMethod httpMethod = req.getMethod();

        this.logService.sendLog(RequestDetail.builder()
        .requestPath(path.toString())
        .host(host)
        .httpMethod(httpMethod.name())// TODO http method null check
        .build());

        this.apisValidator.checkApis(exchange);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Data
    @Builder
    @RequiredArgsConstructor
    public static final class RequestDetail implements Serializable {
        private final String requestPath;
        private final String host;
        private final String httpMethod;

    }
}
