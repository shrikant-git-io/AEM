/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.guides.wknd.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Activate;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service=Servlet.class,
           property={
                   Constants.SERVICE_DESCRIPTION + "=Path based Servlet",
                   "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                   "sling.servlet.resourceTypes="+ "cq:Page", // applies to all pages
                   "sling.servlet.extensions=" + "testpath"
           })
//http://localhost:4502/content/wknd/en.testpath
//http://localhost:4502/content/wknd.testpath
@Designate(ocd = PathBasedServletConfig.class)//dynamic configuration
public class PathBasedServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    
    private static final Logger log = LoggerFactory.getLogger(PathBasedServlet.class);
    
    private String url;
    @Activate
    protected void activate(PathBasedServletConfig pathBasedServletConfig) {
    	
    	log.info("####PathBasedServlet Activate :");
    	url = pathBasedServletConfig.getUrl();
    	log.info("#### Url set to : "+ url);
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        final Resource resource = req.getResource();
        resp.setContentType("text/plain");
        resp.getWriter().write("Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE));
        
    }
}
