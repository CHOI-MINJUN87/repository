package com.lezhin.t.entity.base;


import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@MappedSuperclass
public class BaseEntity {

    @CreatedBy
    private String regId;

    @CreatedDate
    private LocalDateTime regTs;

    @LastModifiedBy
    private String modId;

    @LastModifiedDate
    private LocalDateTime modTs;
}
