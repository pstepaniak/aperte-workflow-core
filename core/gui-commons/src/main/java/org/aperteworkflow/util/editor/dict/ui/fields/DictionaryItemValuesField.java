package org.aperteworkflow.util.editor.dict.ui.fields;

import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import org.aperteworkflow.util.dict.wrappers.DictionaryItemValueWrapper;
import org.aperteworkflow.util.vaadin.ui.date.OptionalDateField;
import org.vaadin.addon.customfield.CustomField;
import pl.net.bluesoft.rnd.util.i18n.I18NSource;

import java.util.*;

import static org.aperteworkflow.util.dict.wrappers.DictionaryItemValueWrapper.*;
import static org.aperteworkflow.util.vaadin.VaadinUtility.*;

public abstract class DictionaryItemValuesField extends CustomField {
	private I18NSource source;
    private Application application;
    private String valueType;

    private VerticalLayout itemsLayout;
    private List<ItemValueForm> forms;

    private List<DictionaryItemValueWrapper> originalValue = new ArrayList<DictionaryItemValueWrapper>();
    private List<DictionaryItemValueWrapper> modifiedValue;
    private Label noValuesLabel;

    public DictionaryItemValuesField(Application application, I18NSource source, String valueType) {
        this.source = source;
        this.application = application;
        this.valueType = valueType;
        initView();
    }

    private void initView() {
        itemsLayout = new VerticalLayout();
        itemsLayout.setMargin(false, false, true, false);
        itemsLayout.setSpacing(true);
        itemsLayout.setWidth("100%");
        noValuesLabel = new Label("<i>" + getMessage("dict.item.novalues") + "</i>", Label.CONTENT_XHTML);
        setCompositionRoot(itemsLayout);
    }

    public ClickListener getAddValueClickListener() {
        return new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                DictionaryItemValueWrapper itemValue = createItemValueWrapper();
                modifiedValue.add(itemValue);
                createValueRow(itemValue);
            }
        };
    }

	protected abstract DictionaryItemValueWrapper createItemValueWrapper();

	public String getMessage(String key) {
        return source.getMessage(key);
    }

    public String getMessage(String key, String defaultValue) {
        return source.getMessage(key, defaultValue);
    }

    private void loadData() {
        itemsLayout.removeAllComponents();
        forms = new ArrayList<ItemValueForm>();
        createModifiedListFromOriginal();
        if (modifiedValue.isEmpty()) {
            itemsLayout.addComponent(noValuesLabel);
        }
        else {
            for (DictionaryItemValueWrapper itemValue : modifiedValue) {
                createValueRow(itemValue);
            }
        }
    }

    private void createModifiedListFromOriginal() {
        modifiedValue = new ArrayList<DictionaryItemValueWrapper>();
        for (DictionaryItemValueWrapper val : originalValue) {
            modifiedValue.add(val.exactCopy());
        }
        Collections.sort(modifiedValue, new Comparator<DictionaryItemValueWrapper>() {
            @Override
            public int compare(DictionaryItemValueWrapper o1, DictionaryItemValueWrapper o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
    }

    private void createValueRow(final DictionaryItemValueWrapper val) {
        VerticalLayout content = new VerticalLayout();
        content.setMargin(false, false, true, false);
        content.setWidth("100%");

        final Panel wrapper = new Panel(content) {{
            setStyleName("bubble");
        }};

        itemsLayout.addComponent(wrapper);

        final ItemValueForm form = new ItemValueForm(val);
        form.setWidth("100%");
        form.addCopyButton(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                DictionaryItemValueWrapper itemValue = val.shallowCopy();
                modifiedValue.add(itemValue);
                createValueRow(itemValue);
            }
        });
        form.addDeleteButton(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                forms.remove(form);
                itemsLayout.removeComponent(wrapper);
                modifiedValue.remove(val);
                if (modifiedValue.isEmpty()) {
                    itemsLayout.addComponent(noValuesLabel);
                }
            }
        });
        forms.add(form);
        content.addComponent(form);

        if (itemsLayout.getComponentIndex(noValuesLabel) != -1) {
            itemsLayout.removeComponent(noValuesLabel);
        }
    }

    @Override
    protected void setInternalValue(Object newValue) {
        if (!(newValue instanceof Collection)) {
            throw new IllegalArgumentException("Unable to handle non-collection values");
        }
        originalValue = new ArrayList<DictionaryItemValueWrapper>((Collection<? extends DictionaryItemValueWrapper>) newValue);
        loadData();
        super.setInternalValue(newValue);
    }

    @Override
    public Object getValue() {
        validateInternal();
        Set<DictionaryItemValueWrapper> value = new HashSet<DictionaryItemValueWrapper>();
        for (DictionaryItemValueWrapper val : modifiedValue) {
            value.add(val);
        }
        return value;
    }

    @Override
    public void validate() throws InvalidValueException {
        validateInternal();
        super.validate();
    }

    public void validateInternal() {
        if (!modifiedValue.isEmpty()) {
            for (ItemValueForm form : forms) {
                form.commit();
            }
            for (DictionaryItemValueWrapper val : modifiedValue) {
                Date startDate = val.getValidStartDate();
                Date endDate = val.getValidEndDate();
                if (endDate != null && startDate != null && endDate.before(startDate)) {
                    throw new InvalidValueException(getMessage("validate.item.val.dates"));
                }
            }
            boolean startDateFullRange = false, endDateFullRange = false;
            for (DictionaryItemValueWrapper val : modifiedValue) {
                startDateFullRange = validateSingleDate(startDateFullRange, val, val.getValidStartDate());
                endDateFullRange = validateSingleDate(endDateFullRange, val, val.getValidEndDate());
            }
        }
    }

    private boolean validateSingleDate(boolean fullRangeFound, DictionaryItemValueWrapper val, Date date) {
        if (date == null) {
            if (fullRangeFound) {
                throw new InvalidValueException(getMessage("validate.item.val.dates"));
            }
            else {
                fullRangeFound = true;
            }
        }
        else {
            for (DictionaryItemValueWrapper otherVal : modifiedValue) {
                if (val != otherVal && otherVal.isValidForDate(date)) {
                    throw new InvalidValueException(getMessage("validate.item.val.dates"));
                }
            }
        }
        return fullRangeFound;
    }

    @Override
    public Class<?> getType() {
        return Set.class;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        for (ItemValueForm form : forms) {
            form.setReadOnly(readOnly);
        }
        super.setReadOnly(readOnly);
    }

    @Override
    public void discard() throws SourceException {
        super.discard();
        createModifiedListFromOriginal();
    }

    private class ItemValueFormFieldFactory extends DefaultFieldFactory {
        @Override
        public Field createField(Item item, Object propertyId, Component uiContext) {
            Field field = null;
            if (propertyId.equals(_VALUE)) {
                TextField textField = new TextField(getMessage("dict.item.value"));
                textField.setNullRepresentation("");
                textField.setRequired(true);
                textField.setRequiredError(getMessage("validate.item.val.empty"));
                if (valueType != null) {
                    if ("int".equalsIgnoreCase(valueType)) {
                        textField.addValidator(new IntegerValidator(source.getMessage("validate.integer")));
                    }
                    else if ("dbl".equalsIgnoreCase(valueType)) {
                        textField.addValidator(new DoubleValidator(source.getMessage("validate.double")));
                    }
                }
                textField.setWidth("100%");
                field = textField;
            }
            else if (propertyId.equals(_VALID_START_DATE) || propertyId.equals(_VALID_END_DATE)) {
                OptionalDateField dateField = new OptionalDateField(source);
                dateField.setCaption(getMessage("dict.item.value.valid." + (propertyId.equals(_VALID_START_DATE) ? "from" : "to")));
                dateField.setDateFormat(SIMPLE_DATE_FORMAT_STRING);
                field = dateField;
            }
            else if (propertyId.equals(_EXTENSIONS)) {
                field = createItemExtensionField(application, source);
            }
            return field;
        }
    }

	protected abstract DictionaryItemExtensionField createItemExtensionField(Application application, I18NSource source);

	private class ItemValueForm extends Form {
        private GridLayout layout;
        private HorizontalLayout buttonLayout;
        private Button deleteButton;
        private Button copyButton;

        private ItemValueForm(DictionaryItemValueWrapper val) {
            layout = new GridLayout(3, 3);
            layout.setSpacing(true);
            layout.setMargin(false, false, false, true);
            layout.setWidth("100%");

            deleteButton = deleteIcon(application);
            deleteButton.setCaption(getMessage("dict.delete.value"));
            deleteButton.setDescription(getMessage("dict.delete.value"));

            copyButton = copyIcon(application);
            copyButton.setCaption(getMessage("dict.copy.row"));
            copyButton.setDescription(getMessage("dict.copy.row"));

            buttonLayout = horizontalLayout(Alignment.MIDDLE_RIGHT, copyButton, deleteButton);
            layout.addComponent(buttonLayout, 2, 0);
            layout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);

            setLayout(layout);
            setValidationVisible(false);
            setValidationVisibleOnCommit(false);
            setWriteThrough(false);
            setImmediate(true);
            setInvalidCommitted(false);
            setFormFieldFactory(new ItemValueFormFieldFactory());
            setItemDataSource(new BeanItem<DictionaryItemValueWrapper>(val));
            setVisibleItemProperties(new String[] { _VALUE, _VALID_START_DATE, _VALID_END_DATE, _EXTENSIONS});
        }

        public void addDeleteButton(ClickListener listener) {
            deleteButton.addListener(listener);
        }

        public void addCopyButton(ClickListener listener) {
            copyButton.addListener(listener);
        }

        @Override
        public void setReadOnly(boolean readOnly) {
            super.setReadOnly(readOnly);
            buttonLayout.setVisible(!readOnly);
        }

        @Override
        protected void attachField(Object propertyId, Field field) {
            if (_VALUE.equals(propertyId)) {
                layout.addComponent(field, 0, 0, 1, 0);
            }
            else if (_VALID_START_DATE.equals(propertyId)) {
                layout.addComponent(field, 0, 1);
            }
            else if (_VALID_END_DATE.equals(propertyId)) {
                layout.addComponent(field, 1, 1);
            }
            else if (_EXTENSIONS.equals(propertyId)) {
                layout.addComponent(field, 0, 2, 2, 2);
            }
            layout.setComponentAlignment(field, Alignment.MIDDLE_LEFT);
        }


    }
}
