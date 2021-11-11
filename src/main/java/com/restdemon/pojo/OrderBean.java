package com.restdemon.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderBean {

	private int orderId;
	private int userId;
	private int dishId;
	private int hotelId;
	
	private int quatity;
	private float price;
	
	private String notes;
	private int paymentType;
	private float billAmount;
	private float actualAmount;
	private float discount;
	private String coupenId;
	private String deliveryAddress;
	private String contactNo;
	private String orderedOnAt;

	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getOrderedOnAt() {
		return orderedOnAt;
	}
	public void setOrderedOnAt(String orderedOnAt) {
		this.orderedOnAt = orderedOnAt;
	}
	@Override
	public String toString() {
		return "OrderBean [orderId=" + orderId + ", userId=" + userId + ", dishId=" + dishId + ", hotelId=" + hotelId
				+ ", quatity=" + quatity + ", price=" + price + ", notes=" + notes + ", paymentType=" + paymentType
				+ ", billAmount=" + billAmount + ", actualAmount=" + actualAmount + ", discount=" + discount
				+ ", coupenId=" + coupenId + ", deliveryAddress=" + deliveryAddress + ", contactNo=" + contactNo
				+ ", orderedOnAt=" + orderedOnAt + "]";
	}
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getDishId() {
		return dishId;
	}
	public void setDishId(int dishId) {
		this.dishId = dishId;
	}
	public int getQuatity() {
		return quatity;
	}
	public void setQuatity(int quatity) {
		this.quatity = quatity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public float getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(float billAmount) {
		this.billAmount = billAmount;
	}
	public float getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(float actualAmount) {
		this.actualAmount = actualAmount;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getCoupenId() {
		return coupenId;
	}
	public void setCoupenId(String coupenId) {
		this.coupenId = coupenId;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
}
