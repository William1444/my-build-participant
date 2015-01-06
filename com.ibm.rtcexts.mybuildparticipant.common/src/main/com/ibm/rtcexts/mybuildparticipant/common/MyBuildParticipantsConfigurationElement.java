package com.ibm.rtcexts.mybuildparticipant.common;

import com.ibm.team.build.common.model.BuildPhase;

public abstract interface MyBuildParticipantsConfigurationElement {

	  public static final BuildPhase BUILD_PHASE = BuildPhase.PRE_BUILD;
	  
	  public static final java.lang.String PROPERTY_1 = "com.ibm.rtcexts.mybuildparticipant.common.buildConfigProperty1";
	  
	  public static final java.lang.String PROPERTY_2 = "com.ibm.rtcexts.mybuildparticipant.common.buildConfigProperty2";
	  
	  public static final java.lang.String ELEMENT_ID = "com.ibm.rtcexts.mybuildparticipant.buildconfigelement";
	  
	  public static final java.lang.String NAME = "My new build participant";
		  	
}
