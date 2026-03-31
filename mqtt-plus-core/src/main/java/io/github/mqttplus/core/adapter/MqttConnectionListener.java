package io.github.mqttplus.core.adapter;

public interface MqttConnectionListener {

    void onConnected(String brokerId);

    void onConnectionLost(String brokerId, Throwable cause);

    void onDisconnected(String brokerId);
}
