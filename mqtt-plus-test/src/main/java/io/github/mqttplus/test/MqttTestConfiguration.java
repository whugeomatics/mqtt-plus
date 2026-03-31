package io.github.mqttplus.test;

import io.github.mqttplus.core.router.MqttMessageRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttTestConfiguration {

    @Bean
    public MqttTestTemplate mqttTestTemplate(MqttMessageRouter messageRouter) {
        return new MqttTestTemplate(messageRouter);
    }
}