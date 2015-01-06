package com.ibm.rtcexts.mybuildparticipant.client;

/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2014. All Rights Reserved.
 * 
 *******************************************************************************/

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.ibm.rtcexts.mybuildparticipant.common.MyBuildParticipantsConfigurationElement;

/**  
 * An editor for the Ant build configuration element.
 */
public class BuildIdAndLabelPreConfigurationElementEditor extends BasicConfigurationElementEditor {

  private static final String ERROR_PROPERTY_REQUIRED = "Property is required";
  
  private static final String PROPERTY_1_AND_2_CONFIGURATION_SECTION_LABEL = "Build Definition ID and Label";
  private static final String PROPERTY_1_CONFIG_LABEL = "property 1";
  private static final String PROPERTY_2_CONFIG_LABEL = "property 2";
  
  private static final String PROPERTY_1_AND_2_SECTION_DESCRIPTION = "The section containing my properties 1 and 2";
  private static final String GENERIC_PROPERTY_DESCRIPTION = "A property required for build.";
  
  protected Text fBuildDefinitionIdText;
  protected Text fBuildLabelText;

  //editor constructor
  public BuildIdAndLabelPreConfigurationElementEditor(String elementId, String title) {
    super(elementId, title);
  }

  @Override
  public void createContent(Composite parent, FormToolkit toolkit) {
    parent.setLayout(new TableWrapLayout());
    Section section = createSection(parent, PROPERTY_1_AND_2_CONFIGURATION_SECTION_LABEL, 
        PROPERTY_1_AND_2_SECTION_DESCRIPTION, false);
    Composite composite = (Composite) section.getClient();
    createBuildDefinitionIdWidgets(composite);
    createBuildLabelWidgets(composite);
  }

  private void createBuildDefinitionIdWidgets(Composite parent) {
    fBuildDefinitionIdText = createConfigPropertyTextField(parent, 
        MyBuildParticipantsConfigurationElement.PROPERTY_1,PROPERTY_1_CONFIG_LABEL, 
        GENERIC_PROPERTY_DESCRIPTION, false);
  }
  
  private void createBuildLabelWidgets(Composite parent) {
    createSpacer(parent, FIELD_SPACING, 2);
    fBuildLabelText = createConfigPropertyTextField(parent,
        MyBuildParticipantsConfigurationElement.PROPERTY_2,PROPERTY_2_CONFIG_LABEL,
    	GENERIC_PROPERTY_DESCRIPTION, false);
  }

  @Override
  public boolean validate() {
    boolean isValid = true;

    if (fBuildDefinitionIdText.getText().trim().equals("")) { 
      addErrorMessageForRequiredField(fBuildDefinitionIdText,ERROR_PROPERTY_REQUIRED,
          fBuildDefinitionIdText);
      isValid = false;
    } else {
      removeMessage(fBuildDefinitionIdText, fBuildDefinitionIdText);
    } 
    if (fBuildLabelText.getText().trim().equals("")) {
      addErrorMessageForRequiredField(fBuildLabelText,ERROR_PROPERTY_REQUIRED, 
          fBuildLabelText);
      isValid = false;
    } else {
      removeMessage(fBuildLabelText, fBuildLabelText);
    }
    setPageStatusIndicator(!isValid, false);
    return isValid;
  }

  @Override
  public Control getFocusControl() {
    return fBuildDefinitionIdText;
  }

}
