package pl.net.bluesoft.rnd.processtool.auditlog.definition;

import org.apache.commons.lang3.StringUtils;

public class SimpleAuditConfig {
	public final String attributeName;
	public final String dictKey;
	public final boolean useDict;

	public SimpleAuditConfig(String attributeName) {
		this(attributeName, null);
	}

	public SimpleAuditConfig(String attributeName, String dictKey) {
		this(attributeName, dictKey, StringUtils.isNotEmpty(dictKey));
	}

	private SimpleAuditConfig(String attributeName, String dictKey, boolean useDict) {
		this.attributeName = attributeName;
		this.dictKey = dictKey;
		this.useDict = useDict;
	}
}