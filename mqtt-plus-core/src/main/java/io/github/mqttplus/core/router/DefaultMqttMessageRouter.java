package io.github.mqttplus.core.router;

import io.github.mqttplus.core.converter.PayloadConverter;
import io.github.mqttplus.core.error.ErrorAction;
import io.github.mqttplus.core.error.ErrorActionAggregator;
import io.github.mqttplus.core.error.ErrorHandlingStrategy;
import io.github.mqttplus.core.interceptor.MqttMessageInterceptor;
import io.github.mqttplus.core.invocation.ListenerInvoker;
import io.github.mqttplus.core.model.MqttContext;
import io.github.mqttplus.core.model.MqttHeaders;
import io.github.mqttplus.core.model.MqttListenerDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DefaultMqttMessageRouter implements MqttMessageRouter {

    private final MqttListenerRegistry listenerRegistry;
    private final List<PayloadConverter> payloadConverters;
    private final List<MqttMessageInterceptor> interceptors;
    private final ListenerInvoker listenerInvoker;
    private final ErrorHandlingStrategy errorHandlingStrategy;
    private final ErrorActionAggregator errorActionAggregator;

    public DefaultMqttMessageRouter(MqttListenerRegistry listenerRegistry,
                                    List<PayloadConverter> payloadConverters,
                                    List<MqttMessageInterceptor> interceptors,
                                    ListenerInvoker listenerInvoker,
                                    ErrorHandlingStrategy errorHandlingStrategy,
                                    ErrorActionAggregator errorActionAggregator) {
        this.listenerRegistry = Objects.requireNonNull(listenerRegistry, "listenerRegistry must not be null");
        this.payloadConverters = List.copyOf(Objects.requireNonNull(payloadConverters, "payloadConverters must not be null"));
        this.interceptors = List.copyOf(Objects.requireNonNull(interceptors, "interceptors must not be null"));
        this.listenerInvoker = Objects.requireNonNull(listenerInvoker, "listenerInvoker must not be null");
        this.errorHandlingStrategy = Objects.requireNonNull(errorHandlingStrategy, "errorHandlingStrategy must not be null");
        this.errorActionAggregator = Objects.requireNonNull(errorActionAggregator, "errorActionAggregator must not be null");
    }

    @Override
    public void route(String brokerId, String topic, byte[] payload, MqttHeaders headers) {
        List<MqttListenerDefinition> matches = listenerRegistry.resolve(brokerId, topic);
        List<ErrorAction> actions = new ArrayList<>();
        for (MqttListenerDefinition definition : matches) {
            MqttContext context = new MqttContext(brokerId, topic, payload, headers);
            try {
                for (MqttMessageInterceptor interceptor : interceptors) {
                    interceptor.beforeHandle(context);
                }
                Object convertedPayload = convertPayload(payload, definition.getPayloadType());
                listenerInvoker.invoke(definition, convertedPayload, context);
                actions.add(ErrorAction.ACKNOWLEDGE);
            } catch (Exception ex) {
                actions.add(errorHandlingStrategy.onError(definition, context, ex));
            } finally {
                for (MqttMessageInterceptor interceptor : interceptors) {
                    interceptor.afterHandle(context);
                }
            }
        }
        errorActionAggregator.aggregate(actions);
    }

    private Object convertPayload(byte[] payload, Class<?> targetType) {
        if (byte[].class.equals(targetType)) {
            return payload.clone();
        }
        for (PayloadConverter converter : payloadConverters) {
            if (converter.supports(targetType)) {
                return converter.convert(payload, targetType);
            }
        }
        throw new IllegalStateException("No payload converter available for type: " + targetType.getName());
    }
}
