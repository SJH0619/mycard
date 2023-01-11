package com.mycard.demo.privacy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 이건 단순히 model.addattribute()를 위해 사용하는 DTO객체입니다. 즉 출력만을 위한 DTO객체이니 UpdateForm이랑 다르게 생각해주십시오...
*/
public class PrivacyDTO {
    private String profileImgPath;
    private String name;
    private String phoneNum;
    private String email;
    private String empLogoImgPath;
    private String empName;
    private String empTel;
    private String empFax;
    private String empEmail;
    private String empAddress;
    private String simpleIntro;
}
