package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBCConnection;
import com.myclass.entity.Role;
import com.myclass.entity.Status;

public class StatusDao {

	public List<Status> findAll() {

		String query = "SELECT * FROM status;";

		List<Status> list = new ArrayList<Status>();
		try (Connection conn = JDBCConnection.getConnection()) {
			// Khởi tạo Statement
			PreparedStatement statement = conn.prepareStatement(query);
			// Thực thi câu lệnh truy vấn SELECT * FROM roles;
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				Status status = new Status(res.getInt("id"), res.getString("name"));
				list.add(status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
