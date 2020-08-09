package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBCConnection;
import com.myclass.dto.LoginDto;
import com.myclass.dto.UserDetailDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

public class UserDao {
	
	public List<UserDto> findAllUserRole() {
		
		String query = "SELECT u.id, u.email, u.fullname, r.description FROM crm.users AS u JOIN crm.roles AS r ON u.role_id = r.id";
		List<UserDto> list = new ArrayList<UserDto>();
		try (Connection conn = JDBCConnection.getConnection()) {
			// Khởi tạo Statement
			PreparedStatement statement = conn.prepareStatement(query);
			// Thực thi câu lệnh truy vấn;
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				UserDto user = new UserDto();
				user.setId(res.getInt("id"));
				user.setEmail(res.getString("email"));
				user.setFullname(res.getString("fullname"));
				user.setDescription(res.getString("description"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public UserDetailDto findUserById(int id) {
		String query = "SELECT u.id, u.email,u.avatar, u.fullname,u.password,u.phone, r.description FROM crm.users AS u JOIN crm.roles AS r ON u.role_id = r.id WHERE u.id = ?";
		UserDetailDto user = new UserDetailDto();
		try (Connection conn = JDBCConnection.getConnection()) {
			// Khởi tạo Statement
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			// Thực thi câu lệnh truy vấn;
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				user.setId(res.getInt("id"));
				user.setEmail(res.getString("email"));
				user.setFullname(res.getString("fullname"));
				user.setAvatar(res.getString("avatar"));
				user.setPassword(res.getString("password"));
				user.setPhone(res.getString("phone"));
				user.setRoleid(res.getString("description"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	public List<User> findAll() {

		String query = "SELECT * FROM users;";

		List<User> list = new ArrayList<User>();
		try (Connection conn = JDBCConnection.getConnection()) {
			// Khởi tạo Statement
			PreparedStatement statement = conn.prepareStatement(query);
			// Thực thi câu lệnh truy vấn SELECT * FROM Users;
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				User user = new User();
				user.setId(res.getInt("id"));
				user.setEmail(res.getString("email"));
				user.setPassword(res.getString("password"));
				user.setFullname(res.getString("fullname"));
				user.setAvatar(res.getString("avatar"));
				user.setRoleId(res.getInt("role_id"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	




	public User findById(int id) {
		String query = "SELECT * FROM users WHERE id = ?;";
		User user = new User();
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				user.setId(res.getInt("id"));
				user.setEmail(res.getString("email"));
				user.setPassword(res.getString("password"));
				user.setFullname(res.getString("fullname"));
				user.setAvatar(res.getString("avatar"));
				user.setRoleId(res.getInt("role_id"));
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public int add(User model) {

		String query = "INSERT INTO users (email, password, fullname, avatar, role_id) "
				+ "VALUES (?, ?, ?, ?, ?);";
		try (Connection conn = JDBCConnection.getConnection()) {
			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, model.getEmail());
			statement.setString(2, model.getPassword());
			statement.setString(3, model.getFullname());
			statement.setString(4, model.getAvatar());
			statement.setInt(5, model.getRoleId());
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int update(User model) {

		String query = "UPDATE users SET email = ?, fullname = ?, "
				+ "avatar = ?, role_id = ?, password = ? WHERE id = ?;";
		try (Connection conn = JDBCConnection.getConnection()) {
			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, model.getEmail());
			statement.setString(2, model.getFullname());
			statement.setString(3, model.getAvatar());
			statement.setInt(4, model.getRoleId());
			statement.setInt(5, model.getId());
			statement.setString(6, model.getPassword());
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int updateUser(UserDetailDto model) {

		String query = "UPDATE users SET email = ?, fullname = ?, avatar = ?, password = ?, phone = ? WHERE id = ?;";
		try (Connection conn = JDBCConnection.getConnection()) {			
			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, model.getEmail());
			statement.setString(2, model.getFullname());
			statement.setString(3, model.getAvatar());
			statement.setString(4, model.getPassword());
			statement.setString(5, model.getPhone());;
			statement.setInt(6, model.getId());
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	
	public int deleteById(int id) {
		String query = "DELETE FROM users WHERE id = ?;";
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

	public User findByEmail(String email) {
		String query = "SELECT * FROM users WHERE email = ?;";
		User user = null;
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, email);
			
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				user = new User();
				user.setId(res.getInt("id"));
				user.setEmail(res.getString("email"));
				user.setPassword(res.getString("password"));
				user.setFullname(res.getString("fullname"));
				user.setAvatar(res.getString("avatar"));
				user.setRoleId(res.getInt("role_id"));
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public LoginDto checkLogin(String email) {
		String query = "SELECT u.id, u.email, u.password, u.fullname, u.phone, r.name "
				+ "FROM crm.users AS u JOIN crm.roles AS r  ON u.role_id = r.id WHERE email = ?";
		LoginDto loginDto = null;
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, email);
			
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				loginDto = new LoginDto();
				loginDto.setId(res.getInt("id"));
				loginDto.setEmail(res.getString("email"));
				loginDto.setPassword(res.getString("password"));
				loginDto.setFullname(res.getString("fullname"));
				loginDto.setRoleName(res.getString("name"));
				loginDto.setPhone(res.getString("phone"));
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginDto;
	}

}
