package org.aizeeck.t.repos;

import org.aizeeck.t.domain.Device;

import java.util.List;

public interface DeviceRepoCustom {
    List<Device> getDeviceHourly(String tId);
    List<Device> getDeviceDaily(String tId);
    List<Device> getAllDeviceDaily();
}
