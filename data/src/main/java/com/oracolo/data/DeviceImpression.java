package com.oracolo.data;

import java.util.Objects;

public class DeviceImpression {

    private final int deviceId;

    private final long numberOfImpressions;


    public DeviceImpression(int deviceId, long numberOfImpressions) {
        this.deviceId = deviceId;
        this.numberOfImpressions = numberOfImpressions;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public long getNumberOfImpressions() {
        return numberOfImpressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceImpression)) return false;
        DeviceImpression that = (DeviceImpression) o;
        return deviceId == that.deviceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId);
    }

    @Override
    public String toString() {
        return "DeviceImpression{" +
                "deviceId=" + deviceId +
                ", numberOfImpressions=" + numberOfImpressions +
                '}';
    }
}
