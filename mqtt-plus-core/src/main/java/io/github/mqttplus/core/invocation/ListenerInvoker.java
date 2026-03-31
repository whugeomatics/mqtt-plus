package io.github.mqttplus.core.invocation;

import io.github.mqttplus.core.model.MqttContext;
import io.github.mqttplus.core.model.MqttListenerDefinition;

public interface ListenerInvoker {

    void invoke(MqttListenerDefinition definition, Object payload, MqttContext context) throws Exception;
}
