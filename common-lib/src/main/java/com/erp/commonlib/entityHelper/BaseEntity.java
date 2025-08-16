package com.erp.commonlib.entityHelper;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @Column(name = "created_by")
    private long createdBy;
    @Column(name = "created_date", updatable = false, nullable = false)
    private ZonedDateTime createdDate = ZonedDateTime.now(ZoneOffset.UTC);

    @PrePersist
    protected void onCreate() {
        this.createdDate = ZonedDateTime.now(ZoneOffset.UTC);
    }
}
