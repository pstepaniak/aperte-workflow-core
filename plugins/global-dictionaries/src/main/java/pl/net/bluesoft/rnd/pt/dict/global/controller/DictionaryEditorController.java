package pl.net.bluesoft.rnd.pt.dict.global.controller;

import org.aperteworkflow.ui.help.datatable.JQueryDataTable;
import org.aperteworkflow.ui.help.datatable.JQueryDataTableColumn;
import org.aperteworkflow.ui.help.datatable.JQueryDataTableUtil;
import pl.net.bluesoft.rnd.processtool.web.controller.ControllerMethod;
import pl.net.bluesoft.rnd.processtool.web.controller.IOsgiWebController;
import pl.net.bluesoft.rnd.processtool.web.controller.OsgiController;
import pl.net.bluesoft.rnd.processtool.web.controller.OsgiWebRequest;
import pl.net.bluesoft.rnd.processtool.web.domain.DataPagingBean;
import pl.net.bluesoft.rnd.processtool.web.domain.GenericResultBean;
import pl.net.bluesoft.rnd.pt.dict.global.controller.bean.DictionaryItemDTO;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by pkuciapski on 2014-05-30.
 */
@OsgiController(name = "dictionaryeditorcontroller")
public class DictionaryEditorController implements IOsgiWebController {
    @ControllerMethod(action = "getDictionaryItems")
    public GenericResultBean getDictionaryItems(final OsgiWebRequest invocation) {
        JQueryDataTable dataTable = JQueryDataTableUtil.analyzeRequest(invocation.getRequest().getParameterMap());
        JQueryDataTableColumn sortColumn = dataTable.getFirstSortingColumn();

        Collection<DictionaryItemDTO> dtos = new ArrayList<DictionaryItemDTO>();
        DictionaryItemDTO dto = new DictionaryItemDTO();
        dto.setKey("key");
        dto.setDescription("desc");
        dto.setId("1");
        dtos.add(dto);

        DataPagingBean<DictionaryItemDTO> dataPagingBean =
                new DataPagingBean<DictionaryItemDTO>(dtos, dtos.size(), dataTable.getEcho());

        return dataPagingBean;
    }
}
