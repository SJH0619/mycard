package com.mycard.demo.subscrib;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Subscrib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relationSeq;

    @Column
    @NotNull
    private String subUserId;

    @Column
    @NotNull
    private String pubUserId;

    @Builder
    public Subscrib(String subUserId, String pubUserId) {
        this.subUserId = subUserId;
        this.pubUserId = pubUserId;
    }
}
