package pl.net.bluesoft.rnd.processtool.auditlog.model;

import pl.net.bluesoft.util.lang.Lang;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: Rafał Surowiecki
 * Date: 27.05.14
 * Time: 16:16
 */
public class AuditLog {
	private String groupKey;
	private SortedSet<AuditedProperty> pre = new TreeSet<AuditedProperty>();
	private SortedSet<AuditedProperty> post = new TreeSet<AuditedProperty>();

	public AuditLog(String groupKey) {
		this.groupKey = groupKey;
	}

	public AuditLog() {
		this("");
	}

	public void addPre(AuditedProperty prop){
		pre.add(prop);
	}

	public void addPre(String messageKey, String name, String value, String dictKey) {
		addPre(new AuditedProperty(messageKey, name, value, dictKey));
	}

	public void addPre(String messageKey, String name, String value) {
		addPre(new AuditedProperty(messageKey, name, value));
	}

	public void addPost(AuditedProperty prop){
		post.add(prop);
	}

	public void addPost(String messageKey, String name, String value, String dictKey) {
		addPost(new AuditedProperty(messageKey, name, value, dictKey));
	}

	public void addPost(String messageKey, String name, String value) {
		addPost(new AuditedProperty(messageKey, name, value));
	}

	public boolean isDifferent() {
		if(pre.size() == post.size()) {
			Iterator<AuditedProperty> preIterator = pre.iterator();
			Iterator<AuditedProperty> postIterator = post.iterator();

			while (preIterator.hasNext()){
				if(!Lang.equals(preIterator.next().getValue(), postIterator.next().getValue())) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public SortedSet<AuditedProperty> getPre() {
		return pre;
	}

	public void setPre(SortedSet<AuditedProperty> pre) {
		this.pre = pre;
	}

	public SortedSet<AuditedProperty> getPost() {
		return post;
	}

	public void setPost(SortedSet<AuditedProperty> post) {
		this.post = post;
	}
}
