package pl.net.bluesoft.rnd.processtool.ui.widgets;

import java.util.Collection;

import pl.net.bluesoft.rnd.processtool.model.IAttributesConsumer;

/**
 * Widget data handler
 *
 * @author mpawlak@bluesoft.net.pl
 *
 */
public interface IWidgetDataHandler
{
    /** Handle widget data change */
    Collection<HandlingResult> handleWidgetData(IAttributesConsumer consumer, WidgetData data);

}
