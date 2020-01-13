package com.adobe.aem.guides.wknd.core.servlets;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public @interface PathBasedServletConfig {
	
	@AttributeDefinition(name = "Test feed url")
	public String getUrl();

}
