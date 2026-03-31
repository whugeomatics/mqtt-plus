package io.github.mqttplus.core.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class MqttHeaders {

    private final Map<String, Object> values;

    public MqttHeaders(Map<String, Object> values) {
        Objects.requireNonNull(values, "values must not be null");
        this.values = Collections.unmodifiableMap(new LinkedHashMap<>(values));
    }

    public static MqttHeaders empty() {
        return new MqttHeaders(Collections.emptyMap());
    }

    public Object get(String key) {
        return values.get(key);
    }

    public String getAsString(String key) {
        Object value = values.get(key);
        return value == null ? null : String.valueOf(value);
    }

    public Map<String, Object> asMap() {
        return values;
    }
}
