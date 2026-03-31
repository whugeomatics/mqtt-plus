package io.github.mqttplus.test;

import io.github.mqttplus.core.router.MqttMessageRouter;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnableMqttPlusTestTest {

    @Test
    void shouldRegisterMqttTestTemplateBean() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            MqttTestTemplate template = context.getBean(MqttTestTemplate.class);
            assertNotNull(template);
            template.simulateIncoming("primary", "devices/1/status", "ok");
        }
    }

    @Configuration
    @EnableMqttPlusTest
    static class TestConfig {

        @Bean
        MqttMessageRouter mqttMessageRouter() {
            return (brokerId, topic, payload, headers) -> {
            };
        }
    }
}