package com.mycard.demo.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 6, max = 25)
    @NotEmpty(message = "아이디는 필수 항목입니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String userPwd1;

    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
    private String userPwd2;
}
