package com.restdemon.service;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.tomcat.util.codec.binary.Base64;
import org.codehaus.jettison.json.JSONObject;

import com.restdemon.pojo.UserBean;
import com.db.DbConnection;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

@XmlRootElement
public class UserService {

	Connection con = new DbConnection().con;
	
    // Encryption-Begin
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;
    
    public UserService() throws Exception {
        myEncryptionKey = "ThisIsSpartaThisIsSparta";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }
    
    public String getCurrentDate()
    {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.encodeBase64(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }


    public String decrypt(String encryptedString) {
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
 // Encryption-End
    
	public JSONObject login(UserBean data) throws Exception
	{
//		UserService us = new UserService();
		
		UserBean user = new UserBean();
		
		JSONObject responseObj = new JSONObject();
		// User authendication 
		try {
			
			String customerSql = "select * from customer where email=?";
			String adminSql = "select * from admin where email=?";
			String sql = data.getRole() ==1?adminSql:customerSql;
			
			PreparedStatement stmt= con.prepareStatement(sql);
			stmt.setString(1, data.getEmail());
			ResultSet rs=stmt.executeQuery();  
			if(rs.next()){
				
				if(data.getPassword().equals(decrypt(rs.getString("password"))))
				{
//					user.setName(data.getRole() ==1?rs.getString("admin_name"):rs.getString("customer_name"));
//					user.setId(data.getRole() ==1?rs.getInt("admin_id"):rs.getInt("customer_id"));
//					user.setEmail(data.getRole() ==1?rs.getString("email"):rs.getString("email"));
					
					responseObj.put("name", data.getRole() ==1?rs.getString("admin_name"):rs.getString("customer_name"));
					responseObj.put("id", data.getRole() ==1?rs.getString("admin_id"):rs.getString("customer_id"));
					responseObj.put("email", rs.getString("email"));
					responseObj.put("role", data.getRole());
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
	
	public JSONObject register(UserBean data) throws Exception
	{
		UserService us = new UserService();
		
		UserBean user = new UserBean();
		user.setRegisterDate(getCurrentDate());
		
		JSONObject responseObj = new JSONObject();
		
		//Register user
		try {
			
			if(data.getEmail() != null && !data.getEmail().trim().isEmpty()
					&& data.getPassword() != null && !data.getPassword().trim().isEmpty()
					&& data.getName() != null && !data.getName().trim().isEmpty()
					&& data.getMobile() != null && !data.getMobile().trim().isEmpty()
					&& (((data.getRole() == 0) && data.getAddress() != null && !data.getAddress().trim().isEmpty()) || data.getRole() == 1))
			{
					String customerSql = "INSERT INTO `customer`(`email`, `password`, `customer_name`, `customer_mobile`, `registered_on`, `primary_delivery_address`) VALUES (?,?,?,?,?,?)";
					String adminSql = "INSERT INTO `admin`( `email`, `password`,`admin_name`, `mobile`, `registered_on`) VALUES (?,?,?,?,?)";
					String sql = data.getRole() ==1?adminSql:customerSql;
					
					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.setString(1, data.getEmail());
					stmt.setString(2, us.encrypt(data.getPassword()));
					stmt.setString(3, data.getName());
					stmt.setString(4, data.getMobile());
					stmt.setString(5, user.getRegisterDate());
					if(data.getRole() != 1)
					{
						stmt.setString(6, data.getAddress());
					}
					
					int i=stmt.executeUpdate();
					if(i>0)
					{
						responseObj.put("returnValue","success");
						responseObj.put("returnMessage","Registered Successfully");
					}
					else
					{
						responseObj.put("returnValue","failed");
						responseObj.put("returnMessage","Registered Failed");
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
}
