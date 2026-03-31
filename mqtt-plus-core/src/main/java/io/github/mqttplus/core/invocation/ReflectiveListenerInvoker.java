package io.github.mqttplus.core.invocation;

import io.github.mqttplus.core.model.MqttContext;
import io.github.mqttplus.core.model.MqttListenerDefinition;

import java.lang.reflect.InvocationTargetException;

public final class ReflectiveListenerInvoker implements ListenerInvoker {

    @Override
    public void invoke(MqttListenerDefinition definition, Object payload, MqttContext context) throws Exception {
        try {
            definition.getMethod().setAccessible(true);
            if (definition.getMethod().getParameterCount() == 0) {
                definition.getMethod().invoke(definition.getBean());
                return;
            }
            definition.getMethod().invoke(definition.getBean(), payload);
        } catch (InvocationTargetException ex) {
            Throwable target = ex.getTargetException();
            if (target instanceof Exception exception) {
                throw exception;
            }
            throw ex;
        }
    }
}
