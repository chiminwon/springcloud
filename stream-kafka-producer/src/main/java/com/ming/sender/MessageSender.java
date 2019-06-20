package com.ming.sender;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

@EnableBinding(value = {Source.class})
public class MessageSender {

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "2000"))//单位：秒
    public MessageSource timeMessageSource() {
        return () -> new GenericMessage<>("{\"name\": \"allen\",\"age\":30}");
    }
}
