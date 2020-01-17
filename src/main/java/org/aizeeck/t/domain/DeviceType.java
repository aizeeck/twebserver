package org.aizeeck.t.domain;

/**
 * Created by aizeeck on 9/9/18.
 */

public enum DeviceType {
    WATERHEATER(1440),
    HEATER(1500);

    private final int value;

    DeviceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
