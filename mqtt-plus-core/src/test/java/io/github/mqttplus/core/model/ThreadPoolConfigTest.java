package io.github.mqttplus.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ThreadPoolConfigTest {

    @Test
    void shouldUseDefaultsWhenNoOverridesProvided() {
        ThreadPoolConfig config = ThreadPoolConfig.builder().build();

        assertEquals(ThreadPoolConfig.DEFAULT_CORE_SIZE, config.getCoreSize());
        assertEquals(ThreadPoolConfig.DEFAULT_MAX_SIZE, config.getMaxSize());
        assertEquals(ThreadPoolConfig.DEFAULT_QUEUE_CAPACITY, config.getQueueCapacity());
        assertEquals(ThreadPoolConfig.DEFAULT_REJECTED_POLICY, config.getRejectedPolicy());
    }

    @Test
    void shouldRejectMaxSizeSmallerThanCoreSize() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ThreadPoolConfig.builder().coreSize(4).maxSize(2).build());

        assertEquals("maxSize must be greater than or equal to coreSize", exception.getMessage());
    }
}
