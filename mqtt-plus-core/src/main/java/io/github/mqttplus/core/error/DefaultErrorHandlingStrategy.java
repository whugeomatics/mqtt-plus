package io.github.mqttplus.core.error;

import io.github.mqttplus.core.model.MqttContext;
import io.github.mqttplus.core.model.MqttListenerDefinition;

public final class DefaultErrorHandlingStrategy implements ErrorHandlingStrategy {

    @Override
    public ErrorAction onError(MqttListenerDefinition definition, MqttContext context, Throwable error) {
        return ErrorAction.ACKNOWLEDGE;
    }
}
