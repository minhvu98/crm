package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import com.myclass.connection.JDBCConnection;
import com.myclass.dto.TaskJobDto;
import com.myclass.dto.TotalTask;
import com.myclass.entity.Job;
import com.myclass.entity.Role;
import com.myclass.entity.StatusJob;
import com.myclass.entity.Task;

public class JobDao {
	
	public List<Job> findAll() {

		String query = "SELECT * FROM jobs;";

		List<Job> list = new ArrayList<Job>();
		try (Connection conn = JDBCConnection.getConnection()) {
			// Khởi tạo Statement
			PreparedStatement statement = conn.prepareStatement(query);
			// Thực thi câu lệnh truy vấn SELECT * FROM Jobs;
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				Job job = new Job();
				job.setId(res.getInt("id"));
				job.setName(res.getString("name"));
				job.setStartDate(res.getDate("start_date"));
				job.setEndDate(res.getDate("end_date"));
				list.add(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<StatusJob> getCountById() {
		String query ="SELECT COUNT(id)  AS  count_id, status_id , name,id FROM tasks group by status_id;";
		List<StatusJob> list = new ArrayList<StatusJob>();
		try (Connection conn = JDBCConnection.getConnection()){
			PreparedStatement statement = conn.prepareStatement(query);
			ResultSet res = statement.executeQuery();
			while(res.next()) {
				StatusJob stJob = new StatusJob();
				stJob.setId((res.getInt("id") * 3));
				stJob.setName(res.getString("name"));
				stJob.setCountId(res.getString("count_id"));
				stJob.setStatusId(res.getString("status_id"));
				list.add(stJob);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public List<TotalTask> Total(){
		String query = "SELECT COUNT(id) FROM tasks WHERE id";
		List<TotalTask> list = new ArrayList<TotalTask>();
		try (Connection conn = JDBCConnection.getConnection()) {
			// Khởi tạo Statement
			PreparedStatement statement = conn.prepareStatement(query);
			// Thực thi câu lệnh truy vấn SELECT * FROM Jobs;
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				TotalTask toTal = new TotalTask();
				toTal.setTotal(res.getString("COUNT(id)"));
				list.add(toTal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int add(Job model) {

		String query = "INSERT INTO jobs (name, start_date, end_date) VALUES (?, ?, ?);";
		try (Connection conn = JDBCConnection.getConnection()) {
			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, model.getName());
			statement.setDate(2, model.getStartDate());
			statement.setDate(3, model.getEndDate());
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int update(Job model) {

		String query = "UPDATE jobs SET name = ?, start_date = ?, end_date = ? WHERE id = ?;";
		try (Connection conn = JDBCConnection.getConnection()) {
			// Tạo câu lệnh insert
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, model.getName());
			statement.setDate(2, model.getStartDate());
			statement.setDate(3, model.getEndDate());
			statement.setInt(4, model.getId());
			// Thực thi câu lệnh insert => trả về số lượng dòng bị ảnh hưởng trong bảng
			return statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int deleteById(int id) {
		String query = "DELETE FROM jobs WHERE id = ?;";
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
	public Job findById(int id) {
		String query = "SELECT * FROM jobs WHERE id = ?;";
		Job job = new Job();
		try (Connection conn = JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				job.setId(res.getInt("id"));
				job.setName(res.getString("name"));
				job.setStartDate(res.getDate("start_date"));
				job.setEndDate(res.getDate("end_date"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return job;
	}

	
	public TaskJobDto getAccountById(int id){
		TaskJobDto model = new TaskJobDto();
		String query = "SELECT t.id, t.name, t.start_date, t.end_date, u.fullname AS userName, j.name AS jobName, s.name AS statusName " + 
				"FROM crm.tasks AS t JOIN crm.users AS u ON t.user_id = u.id " + 
				"INNER JOIN crm.jobs AS j ON t.job_id = j.id JOIN crm.status AS s ON t.status_id = s.id " + 
				"WHERE t.id = ?;";
		try (Connection conn =JDBCConnection.getConnection()) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while(res.next()) {
				
				model.setId(res.getInt("id"));
				model.setName(res.getString("name"));
				model.setStartDate(res.getDate("start_date"));
				model.setEndDate(res.getDate("end_date"));
				model.setUserName(res.getString("userName"));
				model.setJobName(res.getString("jobName"));
				model.setStatusName(res.getString("statusName"));
				break;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	public ArrayList<Integer> getAccountByGroupId(int groupId){
		ArrayList<Integer> models = new ArrayList<>();
		String query ="SELECT DISTINCT id From crm.tasks WHERE id = ?";
		try (Connection conn = JDBCConnection.getConnection()){
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, groupId);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				models.add(res.getInt("id"));
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return models;
	}

}
