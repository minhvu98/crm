package com.myclass.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://localhost:3306/crm";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "123456";

	public static Connection getConnection() {
		
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("Không tìm thấy Driver!");
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.out.println("Sai thông tin kết nối database!");
			e.printStackTrace();
		}
		
		return null;
	}
}
