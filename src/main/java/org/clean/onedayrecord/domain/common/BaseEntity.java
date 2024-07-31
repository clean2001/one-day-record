package org.clean.onedayrecord.domain.common;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @CreationTimestamp // DB에는 current_timestamp가 생성되지 않음
    private LocalDateTime createdTime; // 카멜 케이스 사용시 DB에는 스네이크 케이스로 생성된다.

    @UpdateTimestamp
    private LocalDateTime updateTime; // 값을 수정할 때마다 값이 계속 갱신되어야함
}
