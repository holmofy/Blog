package cn.hff.blog.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author holmofy
 */
@Data
@Entity
@Table(name = "tb_audit_log")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @see User#id
     */
    private Integer userId;

    private String detail;

    @CreatedDate
    private LocalDateTime created;

    public static AuditLog create(int userId, String detail) {
        AuditLog log = new AuditLog();
        log.userId = userId;
        log.detail = detail;
        return log;
    }

}
