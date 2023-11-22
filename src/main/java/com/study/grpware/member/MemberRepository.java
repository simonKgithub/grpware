package com.study.grpware.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    Member findByMemberNameAndMemberNumber(String memberName, String memberNumber);

    Member findByEmailAndMemberNumber(String email, String memberNumber);
}
