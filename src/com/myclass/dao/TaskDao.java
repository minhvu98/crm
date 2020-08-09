package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBCConnection;
import com.myclass.dto.TaskDto;
import com.myclass.entity.Task;

public class TaskDao {
	
	public List<TaskDto> findAll() {

		String query = "SELECT t.id, t.name, t.start_date, t.end_date, u.fullname AS userName, j.name AS jobName, s.name AS statusName "
				+ "FROM crm.tasks AS t "
				+ "JOIN crm.users AS u ON t.user_id = u.id "
				+ "JOIN crm.jobs AS j ON t.job_id = j.id "
				+ "JOIN crm.status AS s ON t.status_id = s.id ";

		List<TaskDto> list = new ArrayList<TaskDto>();
		try (Connection conn = JDBCConnection.getConnection()) {
			// Khởi tạo Statement
			PreparedStatement statement = conn.prepareStatement(query);
			// Thực thi câu lệnh truy vấn
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				TaskDto taskDto = new TaskDto();
				taskDto.setId(res.getInt("id"));
				taskDto.setName(res.getString("name"));
				taskDto.setStartDate(res.getDate("start_date"));
				taskDto.setEndDate(res.getDate("end_date"));
				taskDto.setUserName(res.getString("userName"));
				taskDto.setJobName(res.getString("jobName"));
				taskDto.setStatusName(res.getString("statusName"));
				list.add(taskDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Task findById(int id) {
		String query = "SELECT * FROM tasks WHERE id = ?;";
		Task task = new Task();
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				task.setId(res.getInt("id"));
				task.setName(res.getString("name"));
				task.setStartDate(res.getDate("start_date"));
				task.setEndDate(res.getDate("end_date"));
				task.setUserId(res.getInt("user_id"));
				task.setJobId(res.getInt("job_id"));
				task.setStatusId(res.getInt("status_id"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return task;
	}

	public int add(Task model) {

		String query = "INSERT INTO tasks (name, start_date, end_date, user_id, job_id, status_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		try (Connection conn = JDBCConnection.getConnection()) {
			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, model.getName());
			statement.setDate(2, model.getStartDate());
			statement.setDate(3, model.getEndDate());
			statement.setInt(4, model.getUserId());
			statement.setInt(5, model.getJobId());
			statement.setInt(6, model.getStatusId());
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	public int update(Task model) {
		String query = "UPDATE tasks SET name = ?, start_date = ?, end_date = ?, user_id = ?, job_id = ?, status_id = ? WHERE id = ?;";
		
		try (Connection conn = JDBCConnection.getConnection()) {
			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, model.getName());
			statement.setDate(2, model.getStartDate());
			statement.setDate(3, model.getEndDate());
			statement.setInt(4, model.getUserId());
			statement.setInt(5, model.getJobId());
			statement.setInt(6, model.getStatusId());
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int deleteById(int id) {
		String query = "DELETE FROM tasks WHERE id = ?;";
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
