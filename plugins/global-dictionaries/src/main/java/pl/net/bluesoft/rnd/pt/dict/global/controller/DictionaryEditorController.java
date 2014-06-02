package pl.net.bluesoft.rnd.pt.dict.global.controller;

import org.aperteworkflow.ui.help.datatable.JQueryDataTable;
import org.aperteworkflow.ui.help.datatable.JQueryDataTableColumn;
import org.aperteworkflow.ui.help.datatable.JQueryDataTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import pl.net.bluesoft.rnd.processtool.dao.ProcessDictionaryDAO;
import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionary;
import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionaryItem;
import pl.net.bluesoft.rnd.processtool.plugins.ProcessToolRegistry;
import pl.net.bluesoft.rnd.processtool.web.controller.ControllerMethod;
import pl.net.bluesoft.rnd.processtool.web.controller.IOsgiWebController;
import pl.net.bluesoft.rnd.processtool.web.controller.OsgiController;
import pl.net.bluesoft.rnd.processtool.web.controller.OsgiWebRequest;
import pl.net.bluesoft.rnd.processtool.web.domain.DataPagingBean;
import pl.net.bluesoft.rnd.processtool.web.domain.GenericResultBean;
import pl.net.bluesoft.rnd.pt.dict.global.controller.bean.DictionaryDTO;
import pl.net.bluesoft.rnd.pt.dict.global.controller.bean.DictionaryItemDTO;
import pl.net.bluesoft.rnd.util.i18n.I18NSource;

import java.net.URLDecoder;
import java.util.*;


/**
 * Created by pkuciapski on 2014-05-30.
 */
@OsgiController(name = "dictionaryeditorcontroller")
public class DictionaryEditorController implements IOsgiWebController {
    @Autowired
    ProcessToolRegistry registry;

    @ControllerMethod(action = "getDictionaryItems")
    public GenericResultBean getDictionaryItems(final OsgiWebRequest invocation) throws Exception {
        JQueryDataTable dataTable = JQueryDataTableUtil.analyzeRequest(invocation.getRequest().getParameterMap());
        JQueryDataTableColumn sortColumn = dataTable.getFirstSortingColumn();
        String dictId = invocation.getRequest().getParameter("dictId");
        Collection<DictionaryItemDTO> dtos = Collections.emptyList();;
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
            DictionaryItemDTO dto = new DictionaryItemDTO();
            dto.setId(String.valueOf(item.getValue().getId()));
            dto.setKey(item.getValue().getKey());
            dto.setDescription(item.getValue().getDescription(messageSource.getLocale()));
            if (dto.getDescription() == null || "".equals(dto.getDescription()))
                dto.setDescription(item.getValue().getDefaultDescription());
            dtos.add(dto);
        }
        return dtos;
    }

    @ControllerMethod(action = "deleteDictionaryItem")
    public GenericResultBean deleteDictionaryItem(final OsgiWebRequest invocation) {
        GenericResultBean result = new GenericResultBean();
        ProcessDictionaryDAO dao = registry.getDataRegistry().getProcessDictionaryDAO(invocation.getProcessToolContext().getHibernateSession());

        // todo delete the item

        return result;
    }
}
