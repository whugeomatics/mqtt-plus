package io.github.mqttplus.core.model;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public final class MqttListenerDefinition {

    private final String beanName;
    private final Object bean;
    private final Method method;
    private final String broker;
    private final List<String> topics;
    private final int qos;
    private final Class<?> payloadType;

    public MqttListenerDefinition(String beanName,
                                  Object bean,
                                  Method method,
                                  String broker,
                                  List<String> topics,
                                  int qos,
                                  Class<?> payloadType) {
        this.beanName = requireText(beanName, "beanName");
        this.bean = Objects.requireNonNull(bean, "bean must not be null");
        this.method = Objects.requireNonNull(method, "method must not be null");
        this.broker = requireText(broker, "broker");
        this.topics = List.copyOf(Objects.requireNonNull(topics, "topics must not be null"));
        if (this.topics.isEmpty()) {
            throw new IllegalArgumentException("topics must not be empty");
        }
        this.qos = qos;
        this.payloadType = Objects.requireNonNull(payloadType, "payloadType must not be null");
    }

    public String getBeanName() {
        return beanName;
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }

    public String getBroker() {
        return broker;
    }

    public List<String> getTopics() {
        return topics;
    }

    public int getQos() {
        return qos;
    }

    public Class<?> getPayloadType() {
        return payloadType;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
