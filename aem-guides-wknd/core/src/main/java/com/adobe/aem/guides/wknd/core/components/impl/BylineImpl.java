package com.adobe.aem.guides.wknd.core.components.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.factory.ModelFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;

import com.adobe.aem.guides.wknd.core.components.Byline;
import com.adobe.cq.wcm.core.components.models.Image;

@Model(
		// The adaptable parameter specifies that this model can be adapted by the
		// request.Byline used as data-sly-use in HTL adapts the current SlingHttpServletRequest.
		adaptables = { SlingHttpServletRequest.class },
		// The adapters parameter allows the implementation class to be registered under
		// the Byline interface. This allows the HTL script to call the Sling Model via
		// the interface
		adapters = { Byline.class },
		// The resourceType points to the Byline component resource type (created
		// earlier) and helps to resolve the correct model if there are multiple
		// implementations.
		resourceType = { BylineImpl.RESOURCE_TYPE }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BylineImpl implements Byline {
	// binding with actual structure in JCR
	protected static final String RESOURCE_TYPE = "wknd/components/content/byline";

	// To get the current request the @Self annotation can be used to inject the
	// adaptable (which is defined in the @Model(..) as
	// SlingHttpServletRequest.class , into a Java class field.
	@Self
	private SlingHttpServletRequest request;

	// Since Sling Models are Java POJO's, and not OSGi Services, the usual OSGi
	// injection annotations, like @Reference , cannot be used.Instead , Sling
	// Models provide a special @OSGiService annotation that provides similar
	// functionality.
	@OSGiService
	private ModelFactory modelFactory;

	@ValueMapValue
	private String name;

	@ValueMapValue
	private List<String> occupations;

	private Image image;
	// it is invoked after the class is instantiated and all annotated Java fields are injected.
	@PostConstruct
	private void init() {
		image = modelFactory.getModelFromWrappedRequest(request, request.getResource(), Image.class);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<String> getOccupations() {
		if (occupations != null) {
			Collections.sort(occupations);
			return occupations;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public boolean isEmpty() {
		final Image image = getImage();

		if (StringUtils.isBlank(name)) {
			// Name is missing, but required
			return true;
		} else if (occupations == null || occupations.isEmpty()) {
			// At least one occupation is required
			return true;
		} else if (image == null || StringUtils.isBlank(image.getSrc())) {
			// A valid image is required
			return true;
		} else {
			// Everything is populated, so this component is not considered empty
			return false;
		}
	}

	/**
	 * @return the Image Sling Model of this resource, or null if the resource
	 *         cannot create a valid Image Sling Model.
	 */
	private Image getImage() {
		return image;
	}
}