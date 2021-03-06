package pl.net.bluesoft.rnd.processtool.ui.dict.request;

import pl.net.bluesoft.rnd.processtool.model.dict.db.ProcessDBDictionaryItem;
import pl.net.bluesoft.rnd.processtool.ui.request.IActionRequest;

/**
 * Action: show selected item
 * 
 * @author mpawlak@bluesoft.net.pl
 *
 */
public class EditDictionaryItemActionRequest implements IActionRequest 
{
	private ProcessDBDictionaryItem itemToShow;

	public EditDictionaryItemActionRequest(ProcessDBDictionaryItem itemToDelete) 
	{
		this.itemToShow = itemToDelete;
	}
	
	public ProcessDBDictionaryItem getItemToShow()
	{
		return this.itemToShow;
	}
	

}
