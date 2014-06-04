package pl.net.bluesoft.rnd.pt.dict.global.controller.bean;

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
}
