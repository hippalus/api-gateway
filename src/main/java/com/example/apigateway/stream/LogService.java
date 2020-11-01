package com.example.apigateway.stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import static com.example.apigateway.filter.CustomGlobalFilter.*;
import static com.example.apigateway.stream.MessageStreams.GATEWAY_LOG_TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {

   private final MessageStreams messageStreams;

    @SendTo(value = GATEWAY_LOG_TOPIC)
    public void sendLog(final RequestDetail requestDetail) {
        log.info("Sending log {}", requestDetail);

     messageStreams.outboundGatewayLog().send(MessageBuilder
                .withPayload(requestDetail)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
}
