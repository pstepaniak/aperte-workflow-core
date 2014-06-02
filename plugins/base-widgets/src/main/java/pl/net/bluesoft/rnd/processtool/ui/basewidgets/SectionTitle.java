package pl.net.bluesoft.rnd.processtool.ui.basewidgets;

import pl.net.bluesoft.rnd.processtool.plugins.IBundleResourceProvider;
import pl.net.bluesoft.rnd.processtool.ui.widgets.ProcessHtmlWidget;
import pl.net.bluesoft.rnd.processtool.ui.widgets.annotations.*;
import pl.net.bluesoft.rnd.processtool.ui.widgets.impl.SimpleWidgetDataHandler;
import pl.net.bluesoft.rnd.processtool.web.widgets.impl.FileWidgetContentProvider;

/**
 * Created by lukasz on 6/2/14.
 */
@AperteDoc(humanNameKey = "widget.section_title_html.name", descriptionKey = "widget.section_title_html.description")
@WidgetGroup("base-widgets")
@AliasName(name = "SectionTitle", type = WidgetType.Html)
public class SectionTitle extends ProcessHtmlWidget {

    @AutoWiredProperty
    private String title;

    public SectionTitle(IBundleResourceProvider bundleResourceProvider)
    {
        addDataHandler(new SimpleWidgetDataHandler());
        setContentProvider(new FileWidgetContentProvider("section_title.html", bundleResourceProvider));
    }
}