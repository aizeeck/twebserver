package org.aizeeck.t.repos;

import org.aizeeck.t.domain.TUserCookie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CookieRepo extends JpaRepository<TUserCookie, Long> {
    TUserCookie findByName(String name);

    List<TUserCookie> findByOwnerUsernameAndActiveIsTrue(String email);

    @Transactional
    @Modifying
    @Query("UPDATE TUserCookie c SET c.active = false WHERE c.owner.id = :owner_id")
    int updateCookieActivity(@Param("owner_id") Long address);
}
