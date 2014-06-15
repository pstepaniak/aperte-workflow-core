package pl.net.bluesoft.rnd.processtool.auditlog.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Rafał Surowiecki
 * Date: 27.05.14
 * Time: 16:18
 */
public class AuditedProperty implements Comparable<AuditedProperty> {
	public static final String EMPTY = "";

	private String messageKey;
	private String name;
	private String value;
	private String dictKey;
	private boolean useDict = false;
	private Map<String, String> additionalAttributes = new HashMap<String, String>();

	public AuditedProperty() {
	}

	public AuditedProperty(String messageKey, String name, String value) {
		this.messageKey = nvl(messageKey, EMPTY);
		this.name = nvl(name, EMPTY);
		this.value = nvl(value, EMPTY);
	}

	public AuditedProperty(String messageKey, String name, String value, String dictKey) {
		this(messageKey, name, value);
		if (dictKey != null) {
			this.dictKey = dictKey;
			useDict = true;
		}
	}

	private String nvl(String first, String second) {
		return first != null ? first : second;
	}


	public String getMessageKey() {
		return messageKey;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public boolean isUseDict() {
		return useDict;
	}

	public void setUseDict(boolean useDict) {
		this.useDict = useDict;
	}

	public Map<String, String> getAdditionalAttributes() {
		return additionalAttributes;
	}

	public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
		this.additionalAttributes = additionalAttributes;
	}

	public String getAdditionalAttribute(String key) {
		return additionalAttributes.get(key);
	}

	public void setAdditionalAttribute(String key, String value) {
		additionalAttributes.put(key, value);
	}

	@Override
	public int compareTo(AuditedProperty property) {
		return name.compareTo(property.name);
	}

	@Override
	public String toString() {
		return '{' +
				"messageKey='" + messageKey + '\'' +
				", name='" + name + '\'' +
				", value='" + value + '\'' +
				", dictKey='" + dictKey + '\'' +
				", useDict=" + useDict +
				", additionalAttributes='" + additionalAttributes + '\'' +
				'}';
	}
}
