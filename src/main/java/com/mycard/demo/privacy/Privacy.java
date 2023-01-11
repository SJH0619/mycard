package com.mycard.demo.privacy;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

import com.mycard.demo.user.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Privacy {
    @Id
    @Column(name = "userSeq")
    private Long userSeq;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String profileImgPath;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String name;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String phoneNum;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String email;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String empLogoImgPath;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String empName;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String empTel;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String empFax;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String empEmail;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String empAddress;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userSeq")
    private User user;

    @Convert(converter = EmptyStringToNullConverter.class)
    @Column
    private String simpleIntro;

    @Builder
    public Privacy(String profileImgPath, String name,
                String phoneNum, String email,
                String empLogoImgPath, String empName,
                String empTel, String empFax,
                String empEmail, String empAddress,
                User user, String simpleIntro) {
        this.profileImgPath = profileImgPath;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.empLogoImgPath = empLogoImgPath;
        this.empName = empName;
        this.empTel = empTel;
        this.empFax = empFax;
        this.empEmail = empEmail;
        this.empAddress = empAddress;
        this.user = user;
        this.simpleIntro = simpleIntro;
    }

    public void updateProfileImgPath(String profileImgPath) {
        this.profileImgPath = profileImgPath;
    }
    public void updateName(String name) {
        this.name = name;
    }
    public void updatePhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public void updateEmail(String email) {
        this.email = email;
    }
    public void updateEmpLogoImgPath(String empLogoImgPath) {
        this.empLogoImgPath = empLogoImgPath;
    }
    public void updateEmpName(String empName) {
        this.empName = empName;
    }
    public void updateEmpTel(String empTel) {
        this.empTel = empTel;
    }
    public void updateEmpFax(String empFax) {
        this.empFax = empFax;
    }
    public void updateEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }
    public void updateEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }
    public void updateSimpleIntro(String simpleIntro) {
        this.simpleIntro = simpleIntro;
    }
}
