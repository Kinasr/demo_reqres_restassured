package models;

import lombok.Data;
import org.testng.asserts.SoftAssert;

import java.util.Optional;
import java.util.function.Consumer;

public @Data
class VerifyRecord{
    private final String message;
    private Boolean condition;
    private final Consumer<SoftAssert> consumer;

    public VerifyRecord(String message, boolean condition, Consumer<SoftAssert> consumer) {
        this.message = message;
        this.condition = condition;
        this.consumer = consumer;
    }

    public VerifyRecord(String message, Consumer<SoftAssert> consumer) {
        this.message = message;
        this.consumer = consumer;
    }

    public Optional<Boolean> condition() {
        return Optional.ofNullable(this.condition);
    }
}
