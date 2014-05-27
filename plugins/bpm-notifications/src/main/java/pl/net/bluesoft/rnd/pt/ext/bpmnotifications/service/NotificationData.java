package pl.net.bluesoft.rnd.pt.ext.bpmnotifications.service;

import pl.net.bluesoft.rnd.processtool.model.UserData;
import pl.net.bluesoft.rnd.pt.ext.bpmnotifications.model.BpmAttachment;

import java.util.List;

/**
 * Notification DTO
 * 
 * @author mpawlak@bluesoft.net.pl
 *
 */
public class NotificationData 
{
	private UserData recipient;
	private String profileName;
	private TemplateData templateData;
	private List<BpmAttachment> attachments;
	private String source;

	public TemplateData getTemplateData() {
		return templateData;
	}

	public NotificationData setTemplateData(TemplateData templateData) {
		this.templateData = templateData;
		
		return this;
	}

	public UserData getRecipient() {
		return recipient;
	}
	public NotificationData setRecipient(UserData recipient) {
		this.recipient = recipient;
		
		return this;
	}
	
	public String getProfileName() {
		return profileName;
	}

	public NotificationData setProfileName(String profileName) {
		this.profileName = profileName;
		
		return this;
	}

	public void setAttachments(List<BpmAttachment> attachments) {
		this.attachments = attachments;
	}

	public List<BpmAttachment> getAttachments() {
		return attachments;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
