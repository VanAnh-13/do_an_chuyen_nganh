package com.example.talentbridge.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@JsonPropertyOrder({"createDate", "updateDate", "createdBy", "modifiedBy"})
@Data
public class BaseEntity{
    @CreatedDate
    @Column(name = "create_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss a", timezone = "GMT+7")
    Instant createAt;

    @LastModifiedDate
    @Column(name = "update_at")
    @JsonFormat(pattern = "yyyy-MM-dd:HH:mm:ss a", timezone = "GMT+7")
    Instant updateAt;

    @CreatedBy
    @Column(name = "create_by", updatable = false)
    String createBY;

    @LastModifiedBy
    @Column(name = "modified_by")
    String modifiedBy;
}
