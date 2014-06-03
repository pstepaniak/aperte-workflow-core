package pl.net.bluesoft.rnd.pt.dict.global.controller.bean;

import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionaryItem;
import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionaryItemValue;
import pl.net.bluesoft.rnd.util.i18n.I18NSource;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by pkuciapski on 2014-05-30.
 */
public class DictionaryItemDTO {
    private String id;
    private String key;
    private String description;

    private Collection<DictionaryItemValueDTO> values = new ArrayList<DictionaryItemValueDTO>();

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
        if (this.getId() != null)
            item.setId(Long.valueOf(this.getId()));
        updateItem(item, languageCode);
        return item;
    }

    public void updateItem(ProcessDBDictionaryItem item, String languageCode) {
        item.setKey(this.getKey());
        item.setDescription(languageCode, this.getDescription());
        for (DictionaryItemValueDTO valueDTO : this.getValues()) {
            ProcessDBDictionaryItemValue value = null;
            if (valueDTO.getId() != null)
                value = getValueById(item, Long.valueOf(valueDTO.getId()));
            if (value == null)
                item.addValue(valueDTO.toProcessDBDictionaryItemValue(languageCode));
            else if (valueDTO.getToDelete())
                item.removeValue(value);
            else
                valueDTO.updateValue(value, languageCode);
        }
    }

    private ProcessDBDictionaryItemValue getValueById(ProcessDBDictionaryItem item, Long id) {
        for (ProcessDBDictionaryItemValue value : item.getValues()) {
            if (value.getId() != null && value.getId().equals(id))
                return value;
        }
        return null;
    }

    public Collection<DictionaryItemValueDTO> getValues() {
        return values;
    }

    public void setValues(Collection<DictionaryItemValueDTO> values) {
        this.values = values;
    }

    public static DictionaryItemDTO createFrom(ProcessDBDictionaryItem item, I18NSource messageSource) {
        DictionaryItemDTO dto = new DictionaryItemDTO();
        dto.setId(String.valueOf(item.getId()));
        dto.setKey(item.getKey());
        dto.setDescription(item.getDescription(messageSource.getLocale()));
        if (dto.getDescription() == null || "".equals(dto.getDescription()))
            dto.setDescription(item.getDefaultDescription());
        for (ProcessDBDictionaryItemValue value : item.getValues()) {
            DictionaryItemValueDTO valueDTO = DictionaryItemValueDTO.createFrom(value, messageSource);
            dto.getValues().add(valueDTO);
        }
        return dto;
    }

}
