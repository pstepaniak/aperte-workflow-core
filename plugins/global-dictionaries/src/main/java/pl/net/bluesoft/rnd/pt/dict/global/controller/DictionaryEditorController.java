package pl.net.bluesoft.rnd.pt.dict.global.controller;

import org.aperteworkflow.ui.help.datatable.JQueryDataTable;
import org.aperteworkflow.ui.help.datatable.JQueryDataTableColumn;
import org.aperteworkflow.ui.help.datatable.JQueryDataTableUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.net.bluesoft.rnd.processtool.ProcessToolContext;
import pl.net.bluesoft.rnd.processtool.dao.ProcessDictionaryDAO;
import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionary;
import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionaryItem;
import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionaryItemValue;
import pl.net.bluesoft.rnd.processtool.plugins.ProcessToolRegistry;
import pl.net.bluesoft.rnd.processtool.web.controller.ControllerMethod;
import pl.net.bluesoft.rnd.processtool.web.controller.IOsgiWebController;
import pl.net.bluesoft.rnd.processtool.web.controller.OsgiController;
import pl.net.bluesoft.rnd.processtool.web.controller.OsgiWebRequest;
import pl.net.bluesoft.rnd.processtool.web.domain.DataPagingBean;
import pl.net.bluesoft.rnd.processtool.web.domain.GenericResultBean;
import pl.net.bluesoft.rnd.processtool.web.domain.IProcessToolRequestContext;
import pl.net.bluesoft.rnd.pt.dict.global.controller.bean.DictionaryDTO;
import pl.net.bluesoft.rnd.pt.dict.global.controller.bean.DictionaryItemDTO;
import pl.net.bluesoft.rnd.pt.dict.global.controller.bean.DictionaryItemValueDTO;
import pl.net.bluesoft.rnd.util.i18n.I18NSource;
import pl.net.bluesoft.util.lang.FormatUtil;

import java.net.URLDecoder;
import java.util.*;


/**
 * Created by pkuciapski on 2014-05-30.
 */
@OsgiController(name = "dictionaryeditorcontroller")
public class DictionaryEditorController implements IOsgiWebController {
    @Autowired
    ProcessToolRegistry registry;

    protected static final ObjectMapper mapper = new ObjectMapper();

    @ControllerMethod(action = "getDictionaryItems")
    public GenericResultBean getDictionaryItems(final OsgiWebRequest invocation) throws Exception {
        JQueryDataTable dataTable = JQueryDataTableUtil.analyzeRequest(invocation.getRequest().getParameterMap());
        JQueryDataTableColumn sortColumn = dataTable.getFirstSortingColumn();
        String dictId = invocation.getRequest().getParameter("dictId");
        Collection<DictionaryItemDTO> dtos = Collections.emptyList();
        ;
        if (dictId != null) {
            dictId = URLDecoder.decode(dictId, "UTF-8");
            ProcessDictionaryDAO dao = registry.getDataRegistry().getProcessDictionaryDAO(invocation.getProcessToolContext().getHibernateSession());

            ProcessDBDictionary dictionary = dao.fetchDictionary(dictId);
            if (dictionary != null)
                dtos = createItemDTOList(dictionary.getItems(), invocation.getProcessToolRequestContext().getMessageSource());
        }
        DataPagingBean<DictionaryItemDTO> dataPagingBean =
                new DataPagingBean<DictionaryItemDTO>(dtos, dtos.size(), dataTable.getEcho());

        return dataPagingBean;
    }

    @ControllerMethod(action = "getAllDictionaries")
    public GenericResultBean getAllDictionaries(final OsgiWebRequest invocation) {
        GenericResultBean result = new GenericResultBean();
        ProcessDictionaryDAO dao = registry.getDataRegistry().getProcessDictionaryDAO(invocation.getProcessToolContext().getHibernateSession());

        List<ProcessDBDictionary> dictionary = dao.fetchAllDictionaries();
        Collection<DictionaryDTO> dtos = createDTOList(dictionary, invocation.getProcessToolRequestContext().getMessageSource());
        result.setData(dtos);

        return result;
    }

    private Collection<DictionaryDTO> createDTOList(List<ProcessDBDictionary> dictionaries, I18NSource messageSource) {
        Collection<DictionaryDTO> dtos = new ArrayList<DictionaryDTO>();
        for (ProcessDBDictionary dict : dictionaries) {
            DictionaryDTO dto = new DictionaryDTO();
            dto.setId(String.valueOf(dict.getDictionaryId()));
            dto.setName(dict.getName(messageSource.getLocale()));
            if (dto.getName() == null || "".equals(dto.getName())) {
                dto.setName(dict.getDefaultName());
            }
            dtos.add(dto);
        }
        return dtos;
    }

    private Collection<DictionaryItemDTO> createItemDTOList(Map<String, ProcessDBDictionaryItem> items, I18NSource messageSource) {
        Collection<DictionaryItemDTO> dtos = new ArrayList<DictionaryItemDTO>();
        for (Map.Entry<String, ProcessDBDictionaryItem> item : items.entrySet()) {
            DictionaryItemDTO dto = DictionaryItemDTO.createFrom(item.getValue(), messageSource);
            dtos.add(dto);
        }
        return dtos;
    }

    @ControllerMethod(action = "deleteDictionaryItem")
    public GenericResultBean deleteDictionaryItem(final OsgiWebRequest invocation) throws Exception {
        GenericResultBean result = new GenericResultBean();
        ProcessDictionaryDAO dao = registry.getDataRegistry().getProcessDictionaryDAO(invocation.getProcessToolContext().getHibernateSession());
        String dictId = invocation.getRequest().getParameter("dictId");
        String itemJson = invocation.getRequest().getParameter("item");
        try {
            DictionaryItemDTO dto = mapper.readValue(itemJson, DictionaryItemDTO.class);
            ProcessDBDictionary dictionary = getDictionary(dictId, invocation.getProcessToolContext());
            removeItem(dictionary, dto);
            dao.updateDictionary(dictionary);
        } catch (Exception e) {
            result.addError("deleteDictionaryItem", e.getMessage());
        }

        return result;
    }

    private void removeItem(ProcessDBDictionary dictionary, DictionaryItemDTO itemDTO) {
        dictionary.removeItem(itemDTO.getKey());
    }

    @ControllerMethod(action = "getItemValues")
    public GenericResultBean getItemValues(final OsgiWebRequest invocation) throws Exception {
        JQueryDataTable dataTable = JQueryDataTableUtil.analyzeRequest(invocation.getRequest().getParameterMap());
        JQueryDataTableColumn sortColumn = dataTable.getFirstSortingColumn();
        String dictId = invocation.getRequest().getParameter("dictId");
        String itemId = invocation.getRequest().getParameter("itemId");
        Collection<DictionaryItemValueDTO> dtos = Collections.emptyList();
        if ("undefined".equals(itemId))
            itemId = null;

        if (dictId != null) {
            // todo get the item from dao
            ProcessDBDictionary dictionary = getDictionary(dictId, invocation.getProcessToolContext());
            if (dictionary != null) {
                ProcessDBDictionaryItem item = getItemById(dictionary.getItems(), itemId);
                if (item != null) {
                    dtos = createItemValueDTOList(item.getValues(), invocation.getProcessToolRequestContext().getMessageSource());
                }
            }
        }
        DataPagingBean<DictionaryItemValueDTO> dataPagingBean =
                new DataPagingBean<DictionaryItemValueDTO>(dtos, dtos.size(), dataTable.getEcho());

        return dataPagingBean;
    }

    private ProcessDBDictionary getDictionary(String dictId, ProcessToolContext context) throws Exception {
        dictId = URLDecoder.decode(dictId, "UTF-8");
        ProcessDictionaryDAO dao = registry.getDataRegistry().getProcessDictionaryDAO(context.getHibernateSession());
        // todo get the item from dao
        return dao.fetchDictionary(dictId);
    }

    private Collection<DictionaryItemValueDTO> createItemValueDTOList(Set<ProcessDBDictionaryItemValue> values, I18NSource messageSource) {
        Collection<DictionaryItemValueDTO> dtos = new ArrayList<DictionaryItemValueDTO>();
        for (ProcessDBDictionaryItemValue value : values) {
            DictionaryItemValueDTO dto = DictionaryItemValueDTO.createFrom(value, messageSource);
            dtos.add(dto);
        }
        return dtos;
    }

    private ProcessDBDictionaryItem getItemById(Map<String, ProcessDBDictionaryItem> items, String itemId) {
        if (itemId == null)
            return null;
        for (ProcessDBDictionaryItem item : items.values()) {
            if (item.getId().equals(Long.valueOf(itemId)))
                return item;
        }
        return null;
    }


    @ControllerMethod(action = "saveDictionaryItem")
    public GenericResultBean saveDictionaryItem(final OsgiWebRequest invocation) throws Exception {
        GenericResultBean result = new GenericResultBean();
        String dictId = invocation.getRequest().getParameter("dictId");
        String item = invocation.getRequest().getParameter("item");
        ProcessDictionaryDAO dao = registry.getDataRegistry().getProcessDictionaryDAO(invocation.getProcessToolContext().getHibernateSession());
        // todo save the item
        try {
            DictionaryItemDTO dto = mapper.readValue(item, DictionaryItemDTO.class);
            ProcessDBDictionary dictionary = getDictionary(dictId, invocation.getProcessToolContext());
            if (dto.getId() == null)
                dictionary.addItem(dto.toProcessDBDictionaryItem(invocation.getProcessToolRequestContext().getMessageSource().getLocale().getLanguage()));
            else
                updateItem(dictionary, dto, invocation.getProcessToolRequestContext().getMessageSource());
            dao.updateDictionary(dictionary);
        } catch (Exception e) {
            result.addError("saveDictionaryItem", e.getMessage());
        }
        return result;
    }

    private void updateItem(ProcessDBDictionary dictionary, DictionaryItemDTO dto, I18NSource messageSource) {
        for (ProcessDBDictionaryItem item : dictionary.getItems().values()) {
            if (item.getId() != null && item.getId().equals(Long.valueOf(dto.getId()))) {
                dictionary.removeItem(item.getKey());
                dto.updateItem(item, messageSource.getLocale().getLanguage());
                dictionary.addItem(item);
                break;
            }
        }
    }
}
