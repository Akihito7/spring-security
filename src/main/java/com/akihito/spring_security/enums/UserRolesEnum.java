package com.akihito.spring_security.enums;

public enum UserRolesEnum {
    Admin("admin"),
    User("admin");

    private String role;

    UserRolesEnum(String role) {
        this.role = role;
    }
    }
