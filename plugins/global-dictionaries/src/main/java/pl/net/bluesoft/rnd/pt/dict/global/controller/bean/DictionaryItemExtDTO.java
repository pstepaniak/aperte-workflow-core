package pl.net.bluesoft.rnd.pt.dict.global.controller.bean;

import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionaryItemExtension;
import pl.net.bluesoft.rnd.util.i18n.I18NSource;

/**
 * Created by pkuciapski on 2014-06-02.
 */
public class DictionaryItemExtDTO {
    private Long id;
    private String key;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static DictionaryItemExtDTO createFrom(ProcessDBDictionaryItemExtension ext, I18NSource messageSource) {
        final DictionaryItemExtDTO dto = new DictionaryItemExtDTO();
        dto.setId(ext.getId());
        dto.setKey(ext.getName());
        dto.setValue(ext.getValue());
        return dto;
    }
}
