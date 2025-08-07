package com.careerthon.reactpractice.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass // Timestamped 엔티티를 상속하는 객체에 자동으로 timestamped 필드의 컬럼 부여.
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
}
