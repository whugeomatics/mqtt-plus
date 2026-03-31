package io.github.mqttplus.spring.invocation;

import io.github.mqttplus.core.annotation.MqttTopic;
import io.github.mqttplus.core.model.MqttHeaders;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public final class MqttListenerMethodArgumentResolver {

    public Object[] resolveArguments(Method method, Object payload, byte[] rawPayload, String topic, MqttHeaders headers) {
        Parameter[] parameters = method.getParameters();
        Object[] arguments = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(MqttTopic.class)) {
                arguments[i] = topic;
            } else if (MqttHeaders.class.equals(parameter.getType())) {
                arguments[i] = headers;
            } else if (byte[].class.equals(parameter.getType())) {
                arguments[i] = rawPayload.clone();
            } else {
                arguments[i] = payload;
            }
        }
        return arguments;
    }
}
