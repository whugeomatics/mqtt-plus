package io.github.mqttplus.core.converter;

public interface PayloadConverter {

    boolean supports(Class<?> targetType);

    Object convert(byte[] payload, Class<?> targetType);
}
