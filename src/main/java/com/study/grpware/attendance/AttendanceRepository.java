package com.study.grpware.attendance;

import com.study.grpware.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    AttendanceEntity findByRepYn(boolean repYn);

    @Query("SELECT e FROM AttendanceEntity e WHERE e.member = :member AND e.regDate LIKE :today and e.repYn = :repYn")
    Optional<AttendanceEntity> findByMemberAndRegDateAndRepYn(@Param("member") Member member, @Param("today") String today, @Param("repYn") boolean repYn);
}
