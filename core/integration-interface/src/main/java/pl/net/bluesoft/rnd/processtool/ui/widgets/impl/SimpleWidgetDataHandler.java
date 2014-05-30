package pl.net.bluesoft.rnd.processtool.ui.widgets.impl;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import pl.net.bluesoft.rnd.processtool.model.IAttributesConsumer;
import pl.net.bluesoft.rnd.processtool.model.IAttributesProvider;
import pl.net.bluesoft.rnd.processtool.model.ProcessInstance;
import pl.net.bluesoft.rnd.processtool.ui.widgets.*;

/**
 * Simple data handler mapping given data to simple attributes
 *
 * @author mpawlak@bluesoft.net.pl
 */
public class SimpleWidgetDataHandler implements IWidgetDataHandler {
    private static final String TYPE_SIMPLE = "simple";
    private static final String TYPE_SIMPLE_LARGE = "large";

    private IKeysToIgnoreProvider keysToIgnoreProvider = null;

    @Override
    public Collection<HandlingResult> handleWidgetData(IAttributesConsumer consumer, WidgetData data) {
        ProcessInstance process = consumer.getProcessInstance();

        ProcessInstance rootProcess = null;
        if (process != null) {
            rootProcess = process.getRootProcessInstance();
            if (rootProcess == null)
                rootProcess = process;
        }

        Collection<HandlingResult> results = new LinkedList<HandlingResult>();

        Collection<WidgetDataEntry> dataEntries = data.getEntriesByType(TYPE_SIMPLE);
        dataEntries.addAll(data.getEntriesByType(TYPE_SIMPLE_LARGE));

        for (WidgetDataEntry widgetData : dataEntries) {
            String key = widgetData.getKey();
            String type = widgetData.getType();

            boolean saveToRoot = widgetData.getSaveToRoot();
            IAttributesConsumer consumerToSave = null;
            ProcessInstance processToSave = saveToRoot ? rootProcess : process;
            if (processToSave == null)
                consumerToSave = consumer;
            else
                consumerToSave = processToSave;

            if (keysToIgnoreProvider == null || !keysToIgnoreProvider.getKeysToIgnore().contains(key)) {
                String oldValue = getOldValue(consumerToSave, widgetData);
                if (oldValue == null) {
                    oldValue = "";
                }

                String newValue = widgetData.getValue();
	            logChanges(results, key, oldValue, newValue);
            }

            setNewValue(consumerToSave, widgetData);
        }
        return results;
    }

	protected void logChanges(Collection<HandlingResult> results, String key, String oldValue, String newValue) {
		//for audit logging
	}


	private String getOldValue(IAttributesProvider process, WidgetDataEntry data) {
        if (TYPE_SIMPLE.equals(data.getType()))
            return process.getSimpleAttributeValue(data.getKey());
        else if (TYPE_SIMPLE_LARGE.equals(data.getType()))
            return process.getSimpleLargeAttributeValue(data.getKey());

        return null;
    }

	private void setNewValue(IAttributesConsumer process, WidgetDataEntry data) {
        String escapedData = data.getValue();

        if (TYPE_SIMPLE.equals(data.getType()))
            process.setSimpleAttribute(data.getKey(), escapedData);
        else if (TYPE_SIMPLE_LARGE.equals(data.getType()))
            process.setSimpleLargeAttribute(data.getKey(), escapedData);
    }


    public void setKeysToIgnore(IKeysToIgnoreProvider provider) {
        this.keysToIgnoreProvider = provider;
    }

}
