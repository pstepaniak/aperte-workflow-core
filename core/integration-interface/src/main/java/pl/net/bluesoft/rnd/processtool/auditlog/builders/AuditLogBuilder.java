package pl.net.bluesoft.rnd.processtool.auditlog.builders;

import pl.net.bluesoft.rnd.processtool.auditlog.model.AuditLog;
import pl.net.bluesoft.rnd.processtool.model.PersistentEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * User: POlszewski
 * Date: 2014-06-12
 */
public interface AuditLogBuilder {
	void addSimple(String key, String oldValue, String newValue);
	<T extends PersistentEntity> void addPre(Collection<T> entries);
	<T extends PersistentEntity> void addPost(Collection<T> entries);

	List<AuditLog> toAuditLogs();

	AuditLogBuilder NULL = new AuditLogBuilder() {
		@Override
		public void addSimple(String key, String oldValue, String newValue) {}

		@Override
		public <T extends PersistentEntity> void addPre(Collection<T> entries) {}

		@Override
		public <T extends PersistentEntity> void addPost(Collection<T> entries) {}

		@Override
		public List<AuditLog> toAuditLogs() {
			return Collections.emptyList();
		}
	};
}
