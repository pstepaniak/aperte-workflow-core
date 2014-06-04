package pl.net.bluesoft.rnd.pt.dict.global.controller.bean;

import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionaryI18N;
import pl.net.bluesoft.rnd.util.i18n.I18NSource;

/**
 * Created by pkuciapski on 2014-06-04.
 */
public class DictionaryI18NDTO {
    private Long id;
    private String languageCode;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static DictionaryI18NDTO createFrom(ProcessDBDictionaryI18N i18n, I18NSource messageSource) {
        final DictionaryI18NDTO dto = new DictionaryI18NDTO();
        dto.setId(i18n.getId());
        dto.setLanguageCode(i18n.getLanguageCode());
        dto.setText(i18n.getText());
        return dto;
    }

    public ProcessDBDictionaryI18N toProcessDBDictionaryI18N(String languageCode) {
        final ProcessDBDictionaryI18N i18n = new ProcessDBDictionaryI18N();
        i18n.setId(this.getId());
        i18n.setLanguageCode(this.getLanguageCode());
        i18n.setText(this.getText());
        return i18n;
    }
}
