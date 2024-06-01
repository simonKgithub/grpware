package com.study.grpware.attendanceMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceMemberRepository extends JpaRepository<AttendanceMember, AttendanceMemberId> {
}
