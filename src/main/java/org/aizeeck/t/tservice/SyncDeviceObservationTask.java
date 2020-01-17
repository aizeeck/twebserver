package org.aizeeck.t.tservice;

import org.aizeeck.t.domain.CookieValidator;
import org.aizeeck.t.domain.Device;
import org.aizeeck.t.domain.DeviceParserFactory;
import org.aizeeck.t.repos.DeviceRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class SyncDeviceObservationTask implements Runnable {

    private Map<String, String> dbCookies;
    private ScheduledFuture<?> future;
    private DeviceRepo deviceRepo;

    public SyncDeviceObservationTask() {
    }

    public SyncDeviceObservationTask(Map<String, String> dbCookies, DeviceRepo deviceRepo) {
        this.dbCookies = dbCookies;
        this.deviceRepo = deviceRepo;
    }

    @Override
    public void run() {
        if (CookieValidator.validateDbCookies(dbCookies)) {
            DeviceParserFactory deviceParserFactory = new DeviceParserFactory();
            List<Device> devices = deviceParserFactory.parse(dbCookies);
            for (Device d : devices) {
                deviceRepo.save(d);
            }
        } else {
            future.cancel(true);
        }
    }

    public Map<String, String> getDbCookies() {
        return dbCookies;
    }

    public void setDbCookies(Map<String, String> dbCookies) {
        this.dbCookies = dbCookies;
    }

    public ScheduledFuture<?> getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }
}