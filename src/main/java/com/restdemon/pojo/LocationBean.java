package com.restdemon.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocationBean {

	private int locationId;
	private String locationName;
	
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	@Override
	public String toString() {
		return "LocationBean [locationId=" + locationId + ", locationName=" + locationName + "]";
	}
}
