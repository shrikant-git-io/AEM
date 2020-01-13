package com.adobe.aem.guides.wknd.core.components.impl;

import java.util.List;

import javax.servlet.Servlet;

import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
public class ReferenceTestComponent {

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private Resource resource;

	@Reference(cardinality = ReferenceCardinality.MULTIPLE)
	private List<Servlet> servlets;

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, target = "(sling.servlet.methods=POST)")
	private List<Servlet> postServlets;

	private static final Logger log = LoggerFactory.getLogger(ReferenceTestComponent.class);

	@Activate
	protected void activate() {

		log.info("Optional Reference: {}", resource);
		log.info("Servlets Registered: {}", servlets.size());
		log.info("POST Servlets Registered: {}", postServlets.size());
	}
}
