package pl.net.bluesoft.rnd.processtool.auditlog.builders;

import pl.net.bluesoft.rnd.processtool.auditlog.definition.*;
import pl.net.bluesoft.rnd.processtool.auditlog.model.AuditLog;
import pl.net.bluesoft.rnd.processtool.auditlog.model.AuditedProperty;
import pl.net.bluesoft.rnd.processtool.model.AbstractPersistentEntity;
import pl.net.bluesoft.rnd.processtool.model.PersistentEntity;
import pl.net.bluesoft.util.lang.Pair;

import java.util.*;

/**
 * User: POlszewski
 * Date: 2014-06-13
 */
public class DefaultAuditLogBuilder implements AuditLogBuilder {
	private final AuditLogDefinition definition;

	private final Map<Pair<String, Object>, AuditLog> map = new HashMap<Pair<String, Object>, AuditLog>();
	private final List<AuditLog> list = new ArrayList<AuditLog>();


	public DefaultAuditLogBuilder(AuditLogDefinition definition) {
		this.definition = definition;
	}

	@Override
	public void addSimple(String key, String oldValue, String newValue) {
		AuditLogGroup group = definition.findGroup(key);

		if (group != null) {
			SimpleAuditConfig auditConfig = group.getAuditConfig(key);
			AuditLog auditLog = getAuditLog(group.getGroupKey(), null);
			String messageKey = getMessageKey(group, key);

			auditLog.addPre(messageKey, key, oldValue, auditConfig.dictKey);
			auditLog.addPost(messageKey, key, newValue, auditConfig.dictKey);
		}
	}

	@Override
	public <T extends PersistentEntity> void addPre(Collection<T> entities) {
		add(LogTarget.PRE, entities);
	}

	@Override
	public <T extends PersistentEntity> void addPost(Collection<T> entries) {
		add(LogTarget.POST, entries);
	}

	private enum LogTarget {
		PRE, POST
	}

	private <T extends PersistentEntity> void add(final LogTarget logTarget, Collection<T> entities) {
		if (entities == null || entities.isEmpty()) {
			return;
		}

		for (T entity : entities) {
			final AuditLogGroup group = definition.findGroup(entity.getClass());

			if (group != null) {
				AuditedEntityHandler<T> handler = group.getAuditedEntityHandler(entity.getClass());
				final AuditLog auditLog = getAuditLog(group.getGroupKey(), entity);

				handler.auditLog(entity, new AuditedEntityCallback() {
					@Override
					public void add(String name, String value) {
						add(name, value, null);
					}

					@Override
					public void add(String name, String value, String dictKey) {
						String messageKey = getMessageKey(group, name);
						AuditedProperty property = new AuditedProperty(messageKey, name, value, dictKey);

						if (logTarget == LogTarget.PRE) {
							auditLog.addPre(property);
						}
						else {
							auditLog.addPost(property);
						}
					}
				});
			}
		}
	}

	private static String getMessageKey(AuditLogGroup group, String name) {
		return group.getMessageKey() + '.' + name;
	}

	private AuditLog getAuditLog(String groupKey, Object object) {
		Object objectId = object instanceof PersistentEntity ? ((AbstractPersistentEntity)object).getId() : object;
		Pair<String, Object> key = new Pair<String, Object>(groupKey, objectId);

		AuditLog enityLog = map.get(key);

		if(enityLog == null){
			enityLog = new AuditLog(groupKey);
			map.put(key, enityLog);
			list.add(enityLog);
		}
		return enityLog;
	}

	@Override
	public List<AuditLog> toAuditLogs() {
		List<AuditLog> result = new ArrayList<AuditLog>(list.size());

		for (AuditLog enityLog : list) {
			if (enityLog.isDifferent()) {
				result.add(enityLog);
			}
		}
		return result;
	}
}
