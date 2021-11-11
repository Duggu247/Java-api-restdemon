package com.restdemon.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.db.DbConnection;
import com.mysql.jdbc.Statement;
import com.restdemon.pojo.OrderBean;

@XmlRootElement
public class OrderService {

	Connection con = new DbConnection().con;
	
	
	public JSONObject placeOrder(JSONObject data) throws Exception
	{
		OrderBean orderObj = new OrderBean();
		
		UserService userObj = new UserService();
		orderObj.setOrderedOnAt(userObj.getCurrentDate());
		
		JSONObject responseObj = new JSONObject();
		JSONArray billItems = (JSONArray) data.get("order");
		
		
		
		try {
			
			//Validation
			/* 
			 * 1. False values
			 * 2. Menu item stock check
			 * 3. Dish existance check
			 * 4. Commit & Rollback 
			 * 
			 * */
			
//			Boolean flag = false;
//			
//			JSONObject errorResponse = new JSONObject();
//			
//			if(Float.parseFloat(data.get("billAmount").toString()) <= 0)
//			{
//				 flag = true;
//				 errorResponse.put("billAmount", "invalid");
//			}
			
			
			// CustomerBills
			
			
			float billAmount =  Float.parseFloat(data.get("billAmount").toString());
			float actualAmount =  Float.parseFloat(data.get("actualAmount").toString());
			float discount =  Float.parseFloat(data.get("discount").toString());
			int paymentType =  (int) data.get("paymentType");
			
			String customerBillSql = "INSERT INTO `customer_bills`( `bill_amount`, `actual_amount`, `discount`, `payment_type`) VALUES (?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(customerBillSql, Statement.RETURN_GENERATED_KEYS);
			stmt.setFloat(1, billAmount);
			stmt.setFloat(2, actualAmount);
			stmt.setFloat(3, discount);
			stmt.setInt(4, paymentType);
			int i = stmt.executeUpdate();
			
			if(i>0)
			{
				//Bill items and Order
				
				try (ResultSet generatedKeys = stmt.getGeneratedKeys())
				{
		            if (generatedKeys.next())
		            {
		            	orderObj.setOrderId(generatedKeys.getInt(1));
		            }
		            else
		            {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            }
		        }
				
				int l = 0;
				
				try {
					
					String orderSql = "INSERT INTO `orders`(`user_id`, `coupen_id`, `customer_notes`, `ordered_on_at`, `delivery_address`, `contact_no`, `bill_id`, `hotel_id`) VALUES (?,?,?,?,?,?,?,?)";
					PreparedStatement stmtOrder = con.prepareStatement(orderSql);
					
					stmtOrder.setInt(1, (int) data.get("userId"));
					stmtOrder.setInt(2, (int) data.get("coupenId"));
					stmtOrder.setString(3,data.get("notes").toString());
					stmtOrder.setString(4, orderObj.getOrderedOnAt());
					stmtOrder.setString(5,data.get("deliveryAddress").toString());
					stmtOrder.setString(6,data.get("contactNo").toString());
					stmtOrder.setInt(7, orderObj.getOrderId());
					stmtOrder.setInt(8, (int) data.get("hotelId"));
					
					l = stmtOrder.executeUpdate();
					
				}catch(Exception e)
				{
					System.out.println(e);
				}
				
				int j[] = null ;
				
				try {
					String billItemsSql = "INSERT INTO `bill_items`( `bill_id`, `dish_id`, `quantity`, `price`) VALUES (?,?,?,?)";
					PreparedStatement stmtBillItems = con.prepareStatement(billItemsSql);
					
					for(int s=0;s<billItems.length();s++)
					{
						int executeLimit=0;
						
						stmtBillItems.setInt(1, orderObj.getOrderId());
						stmtBillItems.setInt(2, (int) ((JSONObject) billItems.get(s)).get("dishId"));
						stmtBillItems.setInt(3, (int) ((JSONObject) billItems.get(s)).get("quatity"));
						stmtBillItems.setFloat(4, Float.parseFloat(((JSONObject) billItems.get(s)).get("price").toString()));
						
						stmtBillItems.addBatch();
						
						executeLimit++;

			            if (executeLimit % 500 == 0 || executeLimit == billItems.length()-1) {
			            	j = stmtBillItems.executeBatch(); // Execute every 1000 items.
			            }
					}
					
					if(j.length>0 && l>0)
					{
						responseObj.put("returnValue","success");
						responseObj.put("returnMessage","Order has been placed Successfully");
					}
					else
					{
						String deleteOrderSql = "delete from customer_bills where order_id=?";
						PreparedStatement deleteStmt = con.prepareStatement(deleteOrderSql);
						deleteStmt.setInt(1, orderObj.getOrderId());
						int k = deleteStmt.executeUpdate();
						
						if(k>0)
						{
							responseObj.put("returnValue","failed");
							responseObj.put("returnMessage","Your order did'nt placed, please try again.");
						}
						else
						{
							responseObj.put("returnValue","failed");
							responseObj.put("returnMessage","Something went wrong. Your amount will be refunded in next 3 days. Sorry for the inconvenience.");
						}
						
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				
				
			}
			else
			{
				responseObj.put("returnValue","failed");
				responseObj.put("returnMessage","Your order did'nt placed, please try again.");
			}
			
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return responseObj;
	}
	
	public JSONObject orderHistory(OrderBean data)
	{
		OrderBean orderObj = new OrderBean();
		orderObj.setUserId(data.getUserId());
		
		JSONObject responseObj = new JSONObject();
		JSONObject orderListObj = new JSONObject();
		JSONArray orderListArr = new JSONArray();
	
		try {
			String sql="SELECT a.bill_id,a.order_id,a.ordered_on_at,a.customer_notes,a.delivery_address,a.contact_no,a.status,b.bill_amount,b.actual_amount,b.discount,b.payment_type, c.hotel_name,c.hotel_id FROM `orders` as a inner join customer_bills as b on a.bill_id=b.bill_id inner join hotel as c on a.hotel_id=c.hotel_id where a.user_id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, orderObj.getUserId());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				orderListObj.put("bill_id",rs.getInt("bill_id"));
				orderListObj.put("order_id",rs.getInt("order_id"));
				orderListObj.put("ordered_on_at",rs.getString("ordered_on_at"));
				orderListObj.put("customer_notes",rs.getString("customer_notes"));
				orderListObj.put("delivery_address",rs.getString("delivery_address"));
				orderListObj.put("contact_no",rs.getString("contact_no"));
				orderListObj.put("status",rs.getInt("status"));
				orderListObj.put("bill_amount",rs.getFloat("bill_amount"));
				orderListObj.put("actual_amount",rs.getFloat("actual_amount"));
				orderListObj.put("discount",rs.getFloat("discount"));
				orderListObj.put("payment_type",rs.getInt("payment_type"));
				orderListObj.put("hotel_id",rs.getInt("hotel_id"));
				orderListObj.put("hotel_name",rs.getString("hotel_name"));
				
				String billItemSql = "SELECT dish_id,quantity,price FROM `bill_items` where bill_id =?";
				PreparedStatement stmtBillItem = con.prepareStatement(billItemSql);
				stmtBillItem.setInt(1, rs.getInt("bill_id"));
				ResultSet rsBillItems = stmtBillItem.executeQuery();
				
				JSONArray billItemtArr = new JSONArray();
				
				while(rsBillItems.next())
				{
					JSONObject billItemObj = new JSONObject();
					
					billItemObj.put("dishId",rsBillItems.getInt("dish_id"));
					billItemObj.put("quatity",rsBillItems.getInt("quantity"));
					billItemObj.put("price",rsBillItems.getFloat("price"));
					billItemtArr.put(billItemObj);
				}
				
				orderListObj.put("orderedItems",billItemtArr);
				orderListArr.put(orderListObj);
			}
			responseObj.put("returnValue","success");
			responseObj.put("returnMessage","Date fetched");
			responseObj.put("data", orderListArr);
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return responseObj;
	}
}
