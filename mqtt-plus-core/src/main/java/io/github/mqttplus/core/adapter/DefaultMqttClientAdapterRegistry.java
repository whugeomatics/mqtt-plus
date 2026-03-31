package io.github.mqttplus.core.adapter;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class DefaultMqttClientAdapterRegistry implements MqttClientAdapterRegistry {

    private final Map<String, MqttClientAdapter> adapters = Collections.synchronizedMap(new LinkedHashMap<>());

    @Override
    public void register(MqttClientAdapter adapter) {
        Objects.requireNonNull(adapter, "adapter must not be null");
        adapters.put(adapter.getBrokerId(), adapter);
    }

    @Override
    public Optional<MqttClientAdapter> find(String brokerId) {
        return Optional.ofNullable(adapters.get(brokerId));
    }

    @Override
    public Collection<MqttClientAdapter> getAll() {
        synchronized (adapters) {
            return List.copyOf(adapters.values());
        }
    }

    @Override
    public void remove(String brokerId) {
        adapters.remove(brokerId);
    }
}
