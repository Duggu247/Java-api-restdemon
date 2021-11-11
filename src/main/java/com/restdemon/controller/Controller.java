package com.restdemon.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.restdemon.pojo.HotelBean;
import com.restdemon.pojo.LocationBean;
import com.restdemon.pojo.OrderBean;
import com.restdemon.pojo.UserBean;
import com.restdemon.service.HotelService;
import com.restdemon.service.LocationService;
import com.restdemon.service.OrderService;
import com.restdemon.service.UserService;

@Path("/api")
public class Controller {

	@Path("/login")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject login(UserBean data) throws Exception
	{
		
		UserService userObj = new UserService();
		return userObj.login(data);
	}
	
	@Path("/register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject register(UserBean data) throws Exception 
	{
		UserService userObj = new UserService();
		return userObj.register(data);
	}
	
	//Hotel
	@Path("/loginHotel")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject hotelRegister(HotelBean data) throws Exception 
	{
		HotelService hotelObj = new HotelService();
		return hotelObj.login(data);
	}
	
	@Path("/registerHotel")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject hotelLogin(HotelBean data) throws Exception 
	{
		HotelService hotelObj = new HotelService();
		return hotelObj.register(data);
	}
	
	@Path("/hotelList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject hotelList(HotelBean data) throws Exception 
	{
		HotelService hotelObj = new HotelService();
		return hotelObj.getHotels(data);
	}
	
	@Path("/dishesList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject dishesList(HotelBean data) throws Exception 
	{
		HotelService hotelObj = new HotelService();
		return hotelObj.getDishes(data);

	}
	
	@Path("/locationList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject locationList() throws Exception 
	{
		LocationService locationObj = new LocationService();
		return locationObj.getLocation();
	}
	
	@Path("/addLocation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject addLocation(LocationBean data) throws Exception 
	{
		LocationService locationObj = new LocationService();
		return locationObj.addLocation(data);
	}
	
	@Path("/placeOrder")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject placeOrder(JSONObject data) throws Exception 
	{
		OrderService orderObj = new OrderService();
		return orderObj.placeOrder(data);
//		System.out.println(data);
//		JSONObject responseObj = new JSONObject();
//		responseObj.put("returnValue","success");
//		responseObj.put("returnMessage","Login Successful");
//		return responseObj;
	}
	
	@Path("/orderHistory")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject orderHistory(OrderBean data) throws Exception 
	{
		OrderService orderObj = new OrderService();
		return orderObj.orderHistory(data);
//		System.out.println(data);
//		JSONObject responseObj = new JSONObject();
//		responseObj.put("returnValue","success");
//		responseObj.put("returnMessage","Login Successful");
//		return responseObj;
	}

}
