package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBCConnection;
import com.myclass.dto.UserDto;
import com.mysql.cj.xdevapi.Result;

public class SearchDao {
	Connection conn = JDBCConnection.getConnection();
	public int count (String txtSearch) {
		String query ="SELECT count(*) from users where fullname like ?;";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, "%"+ txtSearch+"$");
			ResultSet res = statement.executeQuery();
			while(res.next()) {
				return res.getInt(1);
			}
		} catch (Exception e) {
			
		}
		return 0;
	}
	
	public List<UserDto> SearchByFullName(String txtSearch){
		List<UserDto> list = new ArrayList<UserDto>();
		String query ="SELECT u.id, u.email, u.fullname, r.description FROM crm.users AS u JOIN crm.roles AS r ON u.role_id = r.id WHERE u.fullname LIKE ?";
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1,"%" + txtSearch + "%");
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				UserDto user = new UserDto();
				user.setId(res.getInt("id"));
				user.setFullname(res.getString("fullname"));
				user.setEmail(res.getString("email"));
				user.setDescription(res.getString("description"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
