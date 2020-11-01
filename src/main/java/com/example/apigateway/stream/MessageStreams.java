package com.example.apigateway.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageStreams {

    String GATEWAY_LOG_OUTPUT = "gateway-log-outbound";
    String GATEWAY_LOG_TOPIC="gateway-log-topic";

    @Output(GATEWAY_LOG_OUTPUT)
    MessageChannel outboundGatewayLog();
}
