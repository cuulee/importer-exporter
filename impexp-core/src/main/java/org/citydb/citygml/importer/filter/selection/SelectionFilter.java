package org.citydb.citygml.importer.filter.selection;

import org.citydb.citygml.importer.filter.selection.comparison.LikeFilter;
import org.citydb.citygml.importer.filter.selection.id.ResourceIdFilter;
import org.citydb.citygml.importer.filter.selection.spatial.SimpleBBOXFilter;
import org.citydb.query.filter.FilterException;
import org.citygml4j.model.citygml.core.AbstractCityObject;

public class SelectionFilter {
	private ResourceIdFilter resourceIdFilter;
	private LikeFilter gmlNameFilter;
	private SimpleBBOXFilter bboxFilter;

	public ResourceIdFilter getResourceIdFilter() {
		return resourceIdFilter;
	}

	public boolean isSetResourceIdFilter() {
		return resourceIdFilter != null;
	}

	public void setResourceIdFilter(ResourceIdFilter resourceIdFilter) {
		this.resourceIdFilter = resourceIdFilter;
	}

	public LikeFilter getGmlNameFilter() {
		return gmlNameFilter;
	}

	public boolean isSetGmlNameFilter() {
		return gmlNameFilter != null;
	}

	public void setGmlNameFilter(LikeFilter gmlNameFilter) {
		this.gmlNameFilter = gmlNameFilter;
	}

	public SimpleBBOXFilter getBboxFilter() {
		return bboxFilter;
	}

	public boolean isSetBboxFilter() {
		return bboxFilter != null;
	}

	public void setBboxFilter(SimpleBBOXFilter bboxFilter) {
		this.bboxFilter = bboxFilter;
	}

	public boolean isSatisfiedBy(AbstractCityObject cityObject) throws FilterException {
		if (resourceIdFilter != null && !resourceIdFilter.isSatisfiedBy(cityObject))
			return false;

		if (gmlNameFilter != null && !gmlNameFilter.isSatisfiedBy(cityObject))
			return false;

		if (bboxFilter != null && !bboxFilter.isSatisfiedBy(cityObject))
			return false;			

		return true;
	}

}