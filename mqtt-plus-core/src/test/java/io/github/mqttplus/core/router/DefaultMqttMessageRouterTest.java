package io.github.mqttplus.core.router;

import io.github.mqttplus.core.converter.PayloadConverter;
import io.github.mqttplus.core.error.ErrorAction;
import io.github.mqttplus.core.error.ErrorActionAggregator;
import io.github.mqttplus.core.error.ErrorHandlingStrategy;
import io.github.mqttplus.core.interceptor.MqttMessageInterceptor;
import io.github.mqttplus.core.invocation.ReflectiveListenerInvoker;
import io.github.mqttplus.core.model.MqttHeaders;
import io.github.mqttplus.core.model.MqttListenerDefinition;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultMqttMessageRouterTest {

    @Test
    void shouldInvokeMatchedListenerWithConvertedPayloadAndInterceptorCallbacks() throws Exception {
        MqttListenerRegistry registry = new MqttListenerRegistry();
        RecordingHandler handler = new RecordingHandler();
        Method method = RecordingHandler.class.getDeclaredMethod("handle", String.class);
        registry.register(new MqttListenerDefinition("handler", handler, method, "primary", List.of("devices/+/status"), 1, String.class));
        List<String> interceptorEvents = new ArrayList<>();
        DefaultMqttMessageRouter router = new DefaultMqttMessageRouter(
                registry,
                List.of(new StringPayloadConverter()),
                List.of(new RecordingInterceptor(interceptorEvents)),
                new ReflectiveListenerInvoker(),
                (definition, context, error) -> ErrorAction.RETRY,
                new ErrorActionAggregator()
        );

        router.route("primary", "devices/1/status", "online".getBytes(StandardCharsets.UTF_8), new MqttHeaders(Map.of("qos", 1)));

        assertEquals("online", handler.lastPayload);
        assertEquals(List.of("before:devices/1/status", "after:devices/1/status"), interceptorEvents);
    }

    @Test
    void shouldDelegateErrorsToErrorHandlingStrategy() throws Exception {
        MqttListenerRegistry registry = new MqttListenerRegistry();
        FailingHandler handler = new FailingHandler();
        Method method = FailingHandler.class.getDeclaredMethod("handle", String.class);
        registry.register(new MqttListenerDefinition("handler", handler, method, "primary", List.of("devices/#"), 1, String.class));
        AtomicInteger errorCount = new AtomicInteger();
        ErrorHandlingStrategy strategy = (definition, context, error) -> {
            errorCount.incrementAndGet();
            return ErrorAction.DEAD_LETTER;
        };
        DefaultMqttMessageRouter router = new DefaultMqttMessageRouter(
                registry,
                List.of(new StringPayloadConverter()),
                List.of(),
                new ReflectiveListenerInvoker(),
                strategy,
                new ErrorActionAggregator()
        );

        router.route("primary", "devices/1/status", "offline".getBytes(StandardCharsets.UTF_8), MqttHeaders.empty());

        assertEquals(1, errorCount.get());
    }

    private static final class RecordingHandler {
        private String lastPayload;

        void handle(String payload) {
            this.lastPayload = payload;
        }
    }

    private static final class FailingHandler {
        void handle(String payload) {
            throw new IllegalStateException("boom");
        }
    }

    private static final class StringPayloadConverter implements PayloadConverter {
        @Override
        public boolean supports(Class<?> targetType) {
            return String.class.equals(targetType);
        }

        @Override
        public Object convert(byte[] payload, Class<?> targetType) {
            return new String(payload, StandardCharsets.UTF_8);
        }
    }

    private static final class RecordingInterceptor implements MqttMessageInterceptor {
        private final List<String> events;

        private RecordingInterceptor(List<String> events) {
            this.events = events;
        }

        @Override
        public void beforeHandle(io.github.mqttplus.core.model.MqttContext context) {
            events.add("before:" + context.getTopic());
        }

        @Override
        public void afterHandle(io.github.mqttplus.core.model.MqttContext context) {
            events.add("after:" + context.getTopic());
        }
    }
}
