package com.study.grpware.announce;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {
    List<AnnouncementEntity> findAllByOrderByRegDateDesc();

    @Query("SELECT e FROM AnnouncementEntity e WHERE :yyyymmdd between e.startDate and e.endDate")
    List<AnnouncementEntity> findAllByStartAndEndDate(@Param("yyyymmdd") String yyyymmdd);
}