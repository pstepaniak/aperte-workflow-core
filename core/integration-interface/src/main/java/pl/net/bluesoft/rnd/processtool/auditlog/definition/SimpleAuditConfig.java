package pl.net.bluesoft.rnd.processtool.auditlog.definition;

import pl.net.bluesoft.rnd.processtool.model.IAttributesProvider;

public class SimpleAuditConfig {
	public final String attributeName;
	public final DictResolver dictResolver;

	public SimpleAuditConfig(String attributeName) {
		this(attributeName, null);
	}

	public SimpleAuditConfig(String attributeName, DictResolver dictResolver) {
		this.attributeName = attributeName;
		this.dictResolver = dictResolver;
	}

	public String getDictKey(IAttributesProvider attributesProvider) {
		return dictResolver != null ? dictResolver.getDictKey(attributesProvider) : null;
	}
}