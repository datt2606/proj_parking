package com.example.cellphones.model.base;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass // các thuộc tính của lớp có thể ánh xạ vào cơ sở dữ liệu
@Data
@EntityListeners(AuditingEntityListener.class) // Lắng nghe các sự kiện tạo và cập nhật
public abstract class BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE) // ấn thời gian là Date
    @CreatedDate
    @Column(updatable = false) // Đảm bảo createdAt chỉ có thể tạo và không thể cập nhật
    private Date createdAt;

    @Temporal(TemporalType.DATE)
    @LastModifiedBy
    private Date updatedAt;
}

