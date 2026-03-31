package io.github.mqttplus.core.error;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public final class ErrorActionAggregator {

    public ErrorAction aggregate(Collection<ErrorAction> actions) {
        Objects.requireNonNull(actions, "actions must not be null");
        if (actions.isEmpty()) {
            return ErrorAction.ACKNOWLEDGE;
        }
        return actions.stream().max(Comparator.comparingInt(Enum::ordinal)).orElse(ErrorAction.ACKNOWLEDGE);
    }
}
