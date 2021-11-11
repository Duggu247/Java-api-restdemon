package com.db;

import java.sql.*;

public class DbConnection {
	
	public Connection con = null;
	
	public DbConnection()
	{
		String url="jdbc:mysql://localhost:3306/";
		String username = "root";
		String password = "";
		String database = "online-food-order";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url+database, username, password);
			System.out.println("Database connected");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
}
