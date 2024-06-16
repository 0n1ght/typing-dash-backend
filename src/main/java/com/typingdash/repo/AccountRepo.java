package com.typingdash.repo;

import com.typingdash.entity.AccountEntity;
import com.typingdash.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<AccountEntity, Long> {
    boolean existsByEmail(String email);
    Optional<AccountEntity> findByEmail(String email);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM AccountEntity a WHERE a.profileEntity.nickname = :nickname")
    boolean existsByNickname(@Param("nickname") String nick);
    @Query("SELECT a.profileEntity FROM AccountEntity a ORDER BY a.profileEntity.currentSpeed DESC")
    List<ProfileEntity> findTop50ProfilesByOrderByCurrentSpeedDesc();
}
