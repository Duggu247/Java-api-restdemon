package com.restdemon.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HotelBean {

	private int id;
	private String hotelName;
	private String email;
	private String password;
	private String mobile;
	private String address;
    private String registerDate;
    private Integer locationId;
	
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int location_id) {
		this.locationId = location_id;
	}
	
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	@Override
	public String toString() {
		return "HotelBean [id=" + id + ", hotelName=" + hotelName + ", email=" + email + ", password=" + password + ", mobile="
				+ mobile + ", address=" + address + ", registerDate=" + registerDate + ", location_id=" + locationId
				+ "]";
	}
}
