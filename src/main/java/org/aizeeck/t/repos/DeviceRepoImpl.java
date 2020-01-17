package org.aizeeck.t.repos;

import org.aizeeck.t.domain.Device;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class DeviceRepoImpl implements DeviceRepoCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Device> getDeviceHourly(String tId) {
        StoredProcedureQuery getHourlyConsumption =
                em.createNamedStoredProcedureQuery("getDeviceHourly")
                .setParameter("tId", tId);
        return getHourlyConsumption.getResultList();
    }

    @Override
    public List<Device> getDeviceDaily(String tId) {
        StoredProcedureQuery getDailyConsumption =
                em.createNamedStoredProcedureQuery("getDeviceDaily")
                        .setParameter("tId", tId);
        return getDailyConsumption.getResultList();
    }

    @Override
    public List<Device> getAllDeviceDaily() {
        StoredProcedureQuery getDailyConsumption =
                em.createNamedStoredProcedureQuery("getAllDeviceDaily");
        return getDailyConsumption.getResultList();
    }

    @Override
    public List<Device> getAllDeviceMonthly(int startDay) {
        StoredProcedureQuery getDailyConsumption =
                em.createNamedStoredProcedureQuery("getAllDeviceMonthly")
                        .setParameter("startDay", startDay);;
        return getDailyConsumption.getResultList();
    }
}
