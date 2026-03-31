package io.github.mqttplus.core.adapter;

import java.util.Collection;
import java.util.Optional;

public interface MqttClientAdapterRegistry {

    void register(MqttClientAdapter adapter);

    Optional<MqttClientAdapter> find(String brokerId);

    Collection<MqttClientAdapter> getAll();

    void remove(String brokerId);
}
