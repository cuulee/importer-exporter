package org.citydb.config.project.query.filter.lod;

import java.util.LinkedHashSet;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="LodFilterType", propOrder={
		"lods"
})
public class LodFilter {
	@XmlAttribute
	private LodFilterMode mode = LodFilterMode.OR;
	@XmlAttribute
	private LodSearchMode searchMode = LodSearchMode.DEPTH;	
	@XmlAttribute
	private Integer searchDepth = 1;
	@XmlElement(name="lod", required = true)
	private LinkedHashSet<Integer> lods;
	
	public LodFilter() {
		this(false);
	}
	
	public LodFilter(boolean defaultValue) {
		lods = new LinkedHashSet<>();
		if (defaultValue) {
			for (int lod = 0; lod < 5; lod++)
				setLod(lod);
		}
	}
	
	public LodFilterMode getMode() {
		return mode;
	}

	public void setMode(LodFilterMode mode) {
		this.mode = mode;
	}
	
	public LodSearchMode getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(LodSearchMode searchMode) {
		this.searchMode = searchMode;
	}

	public boolean isSetSearchDepth() {
		return searchDepth != null && searchDepth.intValue() >= 0;
	}
	
	public int getSearchDepth() {
		return isSetSearchDepth() ? searchDepth.intValue() : 1;
	}
	
	public void setSearchDepth(int searchDepth) {
		if (searchDepth < 0)
			throw new IllegalArgumentException("The LoD search depth must be greater or equal to 0.");
		
		this.searchDepth = searchDepth;
	}

	public void unsetSearchDepth() {
		searchDepth = null;
	}
	
	public boolean isSetLod(int lod) {
		return lods.contains(lod);
	}
	
	public void setLod(int lod) {
		if (lod < 0 || lod > 4)
			throw new IllegalArgumentException("LoD value must be between 0 and 4.");
		
		lods.add(lod);
	}
	
	public void setLod(int lod, boolean enable) {
		if (lod < 0 || lod > 4)
			throw new IllegalArgumentException("LoD value must be between 0 and 4.");
		
		if (enable)
			lods.add(lod);
		else
			lods.remove(lod);
	}
	
	public boolean isSetAnyLod() {
		for (int lod = 0; lod < 5; lod++) {
			if (lods.contains(lod))
				return true;
		}
		
		return false;
	}
	
	public boolean areAllEnabled() {
		for (int lod = 0; lod < 5; lod++) {
			if (!lods.contains(lod))
				return false;
		}
		
		return true;
	}
	
}
