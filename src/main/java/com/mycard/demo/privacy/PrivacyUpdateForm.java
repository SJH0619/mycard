package com.mycard.demo.privacy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivacyUpdateForm {
    private String profileBase64;
    private String name;
    private String phoneNum;
    private String email;
    private String empLogoBase64;
    private String empName;
    private String empTel;
    private String empFax;
    private String empEmail;
    private String empAddress;
    private String simpleIntro;
}
