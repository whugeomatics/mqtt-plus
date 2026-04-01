package io.github.mqttplus.starter.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mqttplus.core.adapter.MqttClientAdapterRegistry;
import io.github.mqttplus.core.router.MqttMessageRouter;
import io.github.mqttplus.paho.PahoMqttClientAdapterFactory;
import io.github.mqttplus.starter.properties.MqttPlusProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class MqttPlusAutoConfigurationIT {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(MqttPlusAutoConfiguration.class))
            .withBean(ObjectMapper.class, ObjectMapper::new)
            .withBean(PahoMqttClientAdapterFactory.class, PahoMqttClientAdapterFactory::new);

    @Test
    void shouldBindBrokerPropertiesAndRegisterAdapter() {
        contextRunner
                .withPropertyValues(
                        "mqtt-plus.brokers.primary.host=127.0.0.1",
                        "mqtt-plus.brokers.primary.port=1883",
                        "mqtt-plus.brokers.primary.client-id=runner-primary",
                        "mqtt-plus.brokers.primary.keep-alive-interval=45")
                .run(context -> {
                    assertThat(context).hasSingleBean(MqttPlusProperties.class);
                    assertThat(context).hasSingleBean(MqttMessageRouter.class);
                    assertThat(context).hasSingleBean(MqttClientAdapterRegistry.class);

                    MqttPlusProperties properties = context.getBean(MqttPlusProperties.class);
                    assertThat(properties.getBrokers()).containsKey("primary");
                    assertThat(properties.getBrokers().get("primary").getClientId()).isEqualTo("runner-primary");
                    assertThat(properties.getBrokers().get("primary").getKeepAliveInterval()).isEqualTo(45);

                    MqttClientAdapterRegistry registry = context.getBean(MqttClientAdapterRegistry.class);
                    assertThat(registry.find("primary")).isPresent();
                });
    }

    @Test
    void shouldFailFastWhenAdapterFactoryIsMissing() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(MqttPlusAutoConfiguration.class))
                .withBean(ObjectMapper.class, ObjectMapper::new)
                .withPropertyValues(
                        "mqtt-plus.brokers.primary.host=127.0.0.1",
                        "mqtt-plus.brokers.primary.client-id=runner-primary",
                        "mqtt-plus.brokers.primary.mqtt-version=5.0")
                .run(context -> {
                    assertThat(context.getStartupFailure()).isNotNull();
                    assertThat(context.getStartupFailure())
                            .hasMessageContaining("No MQTT adapter factory registered for version: 5.0");
                });
    }
}