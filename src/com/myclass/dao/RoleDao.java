package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBCConnection;
import com.myclass.entity.Role;

public class RoleDao {

	public List<Role> findAll() {

		String query = "SELECT * FROM roles;";

		List<Role> list = new ArrayList<Role>();
		try (Connection conn = JDBCConnection.getConnection()) {
			// Khởi tạo Statement
			PreparedStatement statement = conn.prepareStatement(query);
			// Thực thi câu lệnh truy vấn SELECT * FROM roles;
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				Role role = new Role(res.getInt("id"), res.getString("name"), res.getString("description"));
				list.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Role findById(int id) {
		String query = "SELECT * FROM roles WHERE id = ?;";
		Role role = new Role();
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				role.setId(res.getInt("id"));
				role.setName(res.getString("name"));
				role.setDescription(res.getString("description"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return role;
	}

	public int add(Role model) {

		String query = "INSERT INTO roles (name, description) VALUES (?, ?);";
		try (Connection conn = JDBCConnection.getConnection()) {

			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, model.getName());
			statement.setString(2, model.getDescription());
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int update(Role model) {

		String query = "UPDATE roles SET name = ?, description = ? WHERE id = ?;";
		try (Connection conn = JDBCConnection.getConnection()) {

			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, model.getName());
			statement.setString(2, model.getDescription());
			statement.setInt(3, model.getId());
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int deleteById(int id) {
		String query = "DELETE FROM roles WHERE id = ?;";
		try (Connection conn = JDBCConnection.getConnection()) {

			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
