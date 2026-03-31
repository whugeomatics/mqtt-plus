package io.github.mqttplus.core.model;

public final class ThreadPoolConfig {

    public static final int DEFAULT_CORE_SIZE = 2;
    public static final int DEFAULT_MAX_SIZE = 8;
    public static final int DEFAULT_QUEUE_CAPACITY = 1024;
    public static final String DEFAULT_REJECTED_POLICY = "CALLER_RUNS";

    private final int coreSize;
    private final int maxSize;
    private final int queueCapacity;
    private final String rejectedPolicy;

    private ThreadPoolConfig(Builder builder) {
        this.coreSize = builder.coreSize;
        this.maxSize = builder.maxSize;
        this.queueCapacity = builder.queueCapacity;
        this.rejectedPolicy = builder.rejectedPolicy;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getCoreSize() {
        return coreSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public String getRejectedPolicy() {
        return rejectedPolicy;
    }

    public static final class Builder {

        private int coreSize = DEFAULT_CORE_SIZE;
        private int maxSize = DEFAULT_MAX_SIZE;
        private int queueCapacity = DEFAULT_QUEUE_CAPACITY;
        private String rejectedPolicy = DEFAULT_REJECTED_POLICY;

        public Builder coreSize(int coreSize) {
            this.coreSize = coreSize;
            return this;
        }

        public Builder maxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public Builder queueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
            return this;
        }

        public Builder rejectedPolicy(String rejectedPolicy) {
            this.rejectedPolicy = rejectedPolicy;
            return this;
        }

        public ThreadPoolConfig build() {
            if (coreSize <= 0) {
                throw new IllegalArgumentException("coreSize must be greater than 0");
            }
            if (maxSize < coreSize) {
                throw new IllegalArgumentException("maxSize must be greater than or equal to coreSize");
            }
            if (queueCapacity < 0) {
                throw new IllegalArgumentException("queueCapacity must not be negative");
            }
            if (rejectedPolicy == null || rejectedPolicy.isBlank()) {
                throw new IllegalArgumentException("rejectedPolicy must not be blank");
            }
            return new ThreadPoolConfig(this);
        }
    }
}
