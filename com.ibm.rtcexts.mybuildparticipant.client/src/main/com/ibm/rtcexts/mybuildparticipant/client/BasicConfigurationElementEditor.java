package com.ibm.rtcexts.mybuildparticipant.client;

/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010, 2011. All Rights Reserved.
 * 
 * Note to U.S. Government Users Restricted Rights:
 * Use, duplication or disclosure restricted by GSA ADP Schedule
 * Contract with IBM Corp. 
 *******************************************************************************/

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.ibm.team.build.common.model.IBuildDefinition;
import com.ibm.team.build.ui.editors.builddefinition.AbstractConfigurationElementEditor;

/**
 * Basic configuration element editor with some helpers for creating form fields.
 * 
 * @since 0.8
 */
public abstract class BasicConfigurationElementEditor extends AbstractConfigurationElementEditor {

    public static final int FIELD_SPACING = 10;

    protected IBuildDefinition fWorkingCopy;

    public BasicConfigurationElementEditor(String elementId, String title) {
        super(elementId, title);
    }

    public void setWorkingCopy(IBuildDefinition definition) {
        fWorkingCopy = definition;
    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {
        super.createFormContent(managedForm);
        getManagedForm().reflow(false);
    }
    
    //Creates a section with the given text and description with 2 columns. 
    protected Section createSection(Composite parent, String text, String description, boolean expandable) {
        return createSection(parent, text, description, expandable, 2);
    }
    
    
    // Creates a section with the given text and description. An inner composite
    protected Section createSection(Composite parent, String text, String description, boolean expandable,
            int numColumns) {
        FormToolkit toolkit = getManagedForm().getToolkit();

        int style = ExpandableComposite.TITLE_BAR | Section.DESCRIPTION;
        if (expandable) {
            style |= ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED;
        }
        Section section = toolkit.createSection(parent, style);
        section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB));
        section.setText(text);
        if (description != null) {
            section.setDescription(description);
        }

        Composite composite = toolkit.createComposite(section);
        TableWrapLayout layout = new TableWrapLayout();
        layout.numColumns = numColumns;
        layout.topMargin = 12;
        layout.horizontalSpacing = 10;
        composite.setLayout(layout);
        
        section.setClient(composite);
        
        return section;
    }

    //Creates a labeled text field for the specified string property.
    protected Text createConfigPropertyTextField(Composite parent, final String propertyName, String label, String description,
            final boolean validateOnModify) {
        
        // the editor id is the configuration element id
        final String elementId = getId();
        String value = fWorkingCopy.getConfigurationPropertyValue(elementId, propertyName, ""); //$NON-NLS-1$
        final Text text = createLabeledText(parent, label, description, value);
        text.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                if (validateOnModify) {
                    validate();
                }
                fWorkingCopy.setConfigurationProperty(elementId, propertyName, text.getText().trim());
                setDirty(true);
            }
        });
        return text;
    }

    protected void createSpacer(Composite parent, int height, int horizontalSpan) {
        Label label = getManagedForm().getToolkit().createLabel(parent, ""); //$NON-NLS-1$
        TableWrapData tableWrapData = new TableWrapData(TableWrapData.FILL, TableWrapData.MIDDLE, 1, horizontalSpan);
        tableWrapData.heightHint = height;
        label.setLayoutData(tableWrapData);
    }

    protected Text createLabeledText(Composite parent, String labelText, String description, String textValue) {
        FormToolkit toolkit = getManagedForm().getToolkit();

        // Label
        Label label = toolkit.createLabel(parent, labelText);
        label.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.MIDDLE));

        // Text field
        Text text = toolkit.createText(parent, textValue);
        TableWrapData layoutData = new TableWrapData(TableWrapData.FILL_GRAB);
        layoutData.maxWidth = IDialogConstants.ENTRY_FIELD_WIDTH;
        text.setLayoutData(layoutData);

        // Description under text field
        createSpacer(parent, SWT.DEFAULT, 1);
        Label descriptionLabel = toolkit.createLabel(parent, description, SWT.WRAP);
        descriptionLabel.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
        descriptionLabel.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY));

        return text;
    }

}
