package com.mycard.demo.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

import com.mycard.demo.privacy.Privacy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(unique = true)
    private String userId;

    @Column
    private String userPwd;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private Privacy privacy;

    @Column(nullable = false)
    private Boolean firstAccess;

    @Builder
    public User(String userId, String userPwd, Privacy privacy, Boolean firstAccess) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.privacy = privacy;
        this.firstAccess = firstAccess;
    }

    public void changeFirstAccess() {
        if (firstAccess) {
            this.firstAccess = false;
        }
    }
}
