package payroll.enums;

import java.util.Arrays;

public enum OrderStatus {

    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OrderStatus fromValue(String value) {
        return Arrays.stream(values())
            .filter(s -> s.value.equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown OrderStatus value: " + value));
    }
}
