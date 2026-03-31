package io.github.mqttplus.core.interceptor;

import io.github.mqttplus.core.model.MqttContext;

public interface MqttMessageInterceptor {

    void beforeHandle(MqttContext context);

    void afterHandle(MqttContext context);
}
