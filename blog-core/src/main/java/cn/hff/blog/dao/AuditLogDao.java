package cn.hff.blog.dao;

import cn.hff.blog.common.BaseJpaRepository;
import cn.hff.blog.entity.AuditLog;

/**
 * @author holmofy
 */
public interface AuditLogDao extends BaseJpaRepository<AuditLog, Long> {
}
