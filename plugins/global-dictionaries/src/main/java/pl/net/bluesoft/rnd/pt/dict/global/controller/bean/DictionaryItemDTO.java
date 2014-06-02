package pl.net.bluesoft.rnd.pt.dict.global.controller.bean;

import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionaryItem;

/**
 * Created by pkuciapski on 2014-05-30.
 */
public class DictionaryItemDTO {
    private String id;
    private String key;
    private String description;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProcessDBDictionaryItem toProcessDBDictionaryItem(final String languageCode) {
        ProcessDBDictionaryItem item = new ProcessDBDictionaryItem();
        item.setKey(this.getKey());
        item.setDescription(languageCode, this.getDescription());
        return item;
    }
}
