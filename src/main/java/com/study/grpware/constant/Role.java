package com.study.grpware.constant;

public enum Role {
    /**
     * USER, ADMIN 에 "ROLE_" 붙여주는 이유는
     * UserDetails 를 구현한 User 클래스의 User.UserBuilder roles(...) 메서드 내 보면
     * authorities 를 set 할 때 자동으로 ROLE_을 붙여준다.
     * 근데 내가 UserDetails 를 구현한 Member 에서 getAuthorities() 를 할 때
     * ROLE_을 붙여주지 않아 오류가 발생한 것이다. 따라서 Member#getAuthrities() 시
     * ROLE_을 붙여줄 수 있도록 Role 객체에서 "ROLE_"을 붙인 String 을 반환한 것이다.
     */
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority(){
        return this.authority;
    }
}
