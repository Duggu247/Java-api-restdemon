package com.restdemon.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.db.DbConnection;
import com.restdemon.pojo.LocationBean;

@XmlRootElement
public class LocationService {

	Connection con =  new DbConnection().con;
	
	public JSONObject addLocation(LocationBean data)
	{
		LocationBean locationObj = new LocationBean();
		locationObj.setLocationName(data.getLocationName());
		
		JSONObject responseObj = new JSONObject();
		
		try {
			String sql = "INSERT INTO `location`(`location_name`) VALUES (?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, locationObj.getLocationName());
			
			int i = stmt.executeUpdate();
			
			if(i>0)
			{
				responseObj.put("returnValue","success");
				responseObj.put("returnMessage","New Location Registered Successfully");
			}
			else
			{
				responseObj.put("returnValue","failed");
				responseObj.put("returnMessage","Registration Failed");
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
	
	public JSONObject getLocation()
	{
		
		JSONObject responseObj = new JSONObject();
		
		JSONArray locationListArr = new JSONArray();
		try {
			
			String sql = "SELECT * FROM `location` order by location_name";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				JSONObject locationListObj = new JSONObject();
				
				locationListObj.put("location_id",rs.getInt("location_id"));
				locationListObj.put("location_name",rs.getString("location_name"));
				locationListArr.put(locationListObj);
			}
			responseObj.put("returnValue","success");
			responseObj.put("returnMessage","Date fetched");
			responseObj.put("data", locationListArr);
			
			con.close();
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
		return responseObj;
	}
}
