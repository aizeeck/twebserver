package org.aizeeck.t.repos;

import org.aizeeck.t.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DeviceRepo extends JpaRepository<Device, Long>, DeviceRepoCustom {

    List<Device> getByTIdOrderByObsDate(String tId);

    @Query("select d " +
            "from Device as d " +
            "where d.tId = :tId " +
            "and d.id = (select max(d.id) from Device d where d.tId = :tId)")
    Device getLastSaved(@Param("tId") String tId);

}
