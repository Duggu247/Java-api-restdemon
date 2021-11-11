package com.restdemon.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.db.DbConnection;
import com.restdemon.pojo.HotelBean;
import com.restdemon.pojo.UserBean;

@XmlRootElement
public class HotelService {

	Connection con = new DbConnection().con;
	
	public JSONObject login(HotelBean data) throws Exception
	{
		UserService us = new UserService();
		HotelBean hotelObj = new HotelBean();
		
		JSONObject responseObj = new JSONObject();
		// User authendication 
		try {
			
			String sql = "select * from hotel where email=?";
			
			PreparedStatement stmt= con.prepareStatement(sql);
			stmt.setString(1, data.getEmail());
			ResultSet rs=stmt.executeQuery();  
			if(rs.next()){
				
				if(data.getPassword().equals(us.decrypt(rs.getString("password"))))
				{
					hotelObj.setHotelName(rs.getString("hotel_name"));
					hotelObj.setId(rs.getInt("hotel_id"));
					hotelObj.setEmail(rs.getString("email"));
					
					responseObj.put("name", rs.getString("hotel_name"));
					responseObj.put("id", rs.getString("hotel_id"));
					responseObj.put("email", rs.getString("email"));
					
					responseObj.put("returnValue","success");
					responseObj.put("returnMessage","Login Successful");
				}
				else
				{
					responseObj.put("returnValue","failed");
					responseObj.put("returnMessage","Wrong password, try again.");
				}
				
			}
			else
			{
				responseObj.put("returnValue","failed");
				responseObj.put("returnMessage","Invalid credential");
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
			try
			{
				responseObj.put("returnValue","failed");
				responseObj.put("returnMessage",e);
			}catch(Exception excep)
			{
				System.out.println(excep);
			}
		}
		
		
		return responseObj; 
	}
	
	public JSONObject register(HotelBean data) throws Exception
	{
		UserService us = new UserService();
		
		HotelBean hotelObj = new HotelBean();
		hotelObj.setRegisterDate(us.getCurrentDate());
		JSONObject responseObj = new JSONObject();
		
		//Register user
		try {
			
			if(data.getEmail() != null && !data.getEmail().trim().isEmpty()
					&& data.getPassword() != null && !data.getPassword().trim().isEmpty()
					&& data.getHotelName() != null && !data.getHotelName().trim().isEmpty()
					&& data.getMobile() != null && !data.getMobile().trim().isEmpty()
					&& data.getAddress() != null && !data.getAddress().trim().isEmpty()
					&& data.getLocationId() != 0
					)
			{
					String sql = "INSERT INTO `hotel`(`email`, `password`, `hotel_name`,  `mobile`, `registered_on`, `address`, `location_id`) VALUES (?,?,?,?,?,?,?)";
					
					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.setString(1, data.getEmail());
					stmt.setString(2, us.encrypt(data.getPassword()));
					stmt.setString(3, data.getHotelName());
					stmt.setString(4, data.getMobile());
					stmt.setString(5, hotelObj.getRegisterDate());
					stmt.setString(6, data.getAddress());
					stmt.setInt(7, data.getLocationId());
					
					
					int i=stmt.executeUpdate();
					if(i>0)
					{
						responseObj.put("returnValue","success");
						responseObj.put("returnMessage","New Hotel Registered Successfully");
					}
					else
					{
						responseObj.put("returnValue","failed");
						responseObj.put("returnMessage","Registration Failed");
					}
					
					con.close();
				
			}
			else
			{
				responseObj.put("returnValue","failed");
				responseObj.put("returnMessage","Enter all details.");
			}
			  
			
		}catch(Exception e)
		{
			System.out.println(e);
			try
			{
				responseObj.put("returnValue","failed");
				responseObj.put("returnMessage",e);
			}catch(Exception excep)
			{
				System.out.println(excep);
			}
		}
		return responseObj;
	}
	
	public JSONObject getHotels(HotelBean data)
	{
		HotelBean hotelObj = new HotelBean();
		hotelObj.setLocationId(data.getLocationId());
		
		JSONObject responseObj = new JSONObject();
		JSONObject hotelListObj = new JSONObject();
		JSONArray hotelListArr = new JSONArray();
		try {
			
			if(data.getLocationId() != 0)
			{
				String sql = "SELECT hotel_id,hotel_name FROM `hotel` where location_id=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setInt(1, hotelObj.getLocationId());
				ResultSet rs = stmt.executeQuery();
				while(rs.next()){
					
					hotelListObj.put("hotel_id",rs.getInt("hotel_id"));
					hotelListObj.put("hotel_name",rs.getString("hotel_name"));
					hotelListArr.put(hotelListObj);
				}
				responseObj.put("returnValue","success");
				responseObj.put("returnMessage","Date fetched");
				responseObj.put("data", hotelListArr);
				
			}
			else
			{
				responseObj.put("returnValue","failed");
				responseObj.put("returnMessage","Invalid input.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return responseObj;
	}
	
	public JSONObject getDishes(HotelBean data)
	{
		HotelBean hotelObj = new HotelBean();
		hotelObj.setId(data.getId());
		
		JSONObject responseObj = new JSONObject();
		JSONObject dishListObj = new JSONObject();
		JSONArray dishListArr = new JSONArray();
		try {
			
			if(data.getId() != 0)
			{
				String sql = "SELECT a.menu_id,a.dish_id,b.dish_name,b.food_type,b.category,a.description,a.price,a.offer_percentage FROM `hotel_menu` as a inner join dishes as b on a.dish_id = b.dish_id where a.is_available=1 and a.hotel_id=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setInt(1, hotelObj.getId());
				ResultSet rs = stmt.executeQuery();
				while(rs.next()){
					dishListObj.put("menu_id",rs.getString("menu_id"));
					dishListObj.put("dish_id",rs.getString("dish_id"));
					dishListObj.put("dish_name",rs.getString("dish_name"));
					dishListObj.put("food_type",rs.getString("food_type"));
					dishListObj.put("category",rs.getString("category"));
					dishListObj.put("description",rs.getString("description"));
					dishListObj.put("price",rs.getString("price"));
					dishListObj.put("offer_percentage",rs.getString("offer_percentage"));
					dishListArr.put(dishListObj);
				}
				responseObj.put("returnValue","success");
				responseObj.put("returnMessage","Date fetched");
				responseObj.put("data", dishListArr);
				
			}
			else
			{
				responseObj.put("returnValue","failed");
				responseObj.put("returnMessage","Invalid input.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return responseObj;
	}
}
