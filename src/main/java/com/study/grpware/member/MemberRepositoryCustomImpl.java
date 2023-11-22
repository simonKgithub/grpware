package com.study.grpware.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.grpware.member.QMember;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression isEmailEmpty(String email){
        if (StringUtils.isEmptyOrWhitespace(email)) {
            return QMember.member.email.isEmpty();
        } else {
            return QMember.member.email.isNotEmpty();
        }
    }
    private BooleanExpression isMemberNameEmpty(String memberName){
        if (StringUtils.isEmptyOrWhitespace(memberName)) {
            return QMember.member.memberName.isEmpty();
        } else {
            return QMember.member.memberName.isNotEmpty();
        }
    }

    @Override
    public Member getEachAnswer(MemberDto memberDto) {
//        if (isEmailEmpty(memberDto.getEmail())) {
//
//        }
        return null;
    }
}
