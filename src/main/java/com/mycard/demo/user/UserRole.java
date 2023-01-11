package com.mycard.demo.user;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
