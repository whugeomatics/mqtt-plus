package io.github.mqttplus.core.error;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorActionAggregatorTest {

    @Test
    void shouldReturnAcknowledgeWhenNoActionsExist() {
        ErrorActionAggregator aggregator = new ErrorActionAggregator();

        assertEquals(ErrorAction.ACKNOWLEDGE, aggregator.aggregate(List.of()));
    }

    @Test
    void shouldReturnStrictestActionByOrdinal() {
        ErrorActionAggregator aggregator = new ErrorActionAggregator();

        assertEquals(ErrorAction.RETRY, aggregator.aggregate(List.of(ErrorAction.ACKNOWLEDGE, ErrorAction.RETRY)));
        assertEquals(ErrorAction.DEAD_LETTER, aggregator.aggregate(List.of(ErrorAction.MANUAL_ACK, ErrorAction.DEAD_LETTER)));
    }
}
