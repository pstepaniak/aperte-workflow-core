package pl.net.bluesoft.rnd.pt.dict.global.controller.bean;

/**
 * Created by pkuciapski on 2014-06-02.
 */
public class DictionaryItemValueDTO {
    private String id;
    private String value;
    private String dateFrom;
    private String dateTo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
