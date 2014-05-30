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
import pl.net.bluesoft.rnd.pt.dict.global.controller.bean.DictionaryItemDTO;
import pl.net.bluesoft.rnd.util.i18n.I18NSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * Created by pkuciapski on 2014-05-30.
 */
@OsgiController(name = "dictionaryeditorcontroller")
public class DictionaryEditorController implements IOsgiWebController {
    @Autowired
    ProcessToolRegistry registry;

    @ControllerMethod(action = "getDictionaryItems")
    public GenericResultBean getDictionaryItems(final OsgiWebRequest invocation) {
        JQueryDataTable dataTable = JQueryDataTableUtil.analyzeRequest(invocation.getRequest().getParameterMap());
        JQueryDataTableColumn sortColumn = dataTable.getFirstSortingColumn();

        ProcessDictionaryDAO dao = registry.getDataRegistry().getProcessDictionaryDAO(invocation.getProcessToolContext().getHibernateSession());

        ProcessDBDictionary dictionary = dao.fetchDictionary("complaint_type");
        Collection<DictionaryItemDTO> dtos = createDTOList(dictionary.getItems(), invocation.getProcessToolRequestContext().getMessageSource());

        DataPagingBean<DictionaryItemDTO> dataPagingBean =
                new DataPagingBean<DictionaryItemDTO>(dtos, dtos.size(), dataTable.getEcho());

        return dataPagingBean;
    }

    private Collection<DictionaryItemDTO> createDTOList(Map<String, ProcessDBDictionaryItem> items, I18NSource messageSource) {
        Collection<DictionaryItemDTO> dtos = new ArrayList<DictionaryItemDTO>();
        for (Map.Entry<String, ProcessDBDictionaryItem> item: items.entrySet()) {
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
}
