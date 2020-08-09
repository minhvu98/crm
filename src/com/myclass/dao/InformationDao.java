package com.myclass.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connection.JDBCConnection;
import com.myclass.dto.CountJobUser;
import com.myclass.dto.InformationDto;

public class InformationDao {
	
	
	Connection conn = JDBCConnection.getConnection();
	public List<InformationDto> getTaskJobUserById(int id) {
		List<InformationDto> list = new ArrayList<InformationDto>();
		String query = "SELECT u.id,  s.name AS statusName, j.name AS job_name " + 
				"FROM crm.users AS u JOIN crm.tasks AS t ON t.user_id = u.id " + 
				"INNER JOIN crm.status AS s ON s.id = t.status_id "
				+ "JOIN crm.jobs AS j ON j.id = t.job_id  WHERE u.id = ?;";
		InformationDto inf = new InformationDto();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				inf.setId(res.getInt("id"));
				inf.setStatusname(res.getString("statusName"));
				inf.setJobname(res.getString("job_name"));
				list.add(inf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public InformationDto getInforUserById(int id) {
		String query = "SELECT id, email , password, fullname, avatar, phone FROM crm.users WHERE id = ?";
		InformationDto inf = new InformationDto();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {			
				inf.setEmail(res.getString("email"));
				inf.setPassword(res.getString("password"));
				inf.setFullname(res.getString("fullname"));
				inf.setAvatar(res.getString("avatar"));
				inf.setPhone(res.getString("phone"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inf;
	}
	public List<CountJobUser> getCountUser(int id){
		String query ="SELECT s.name AS statusName, count(*) AS count " + 
				"FROM crm.users AS u JOIN crm.tasks AS t ON t.user_id = u.id " + 
				"INNER JOIN crm.status AS s ON s.id = t.status_id JOIN crm.jobs AS j ON j.id = t.job_id    WHERE u.id = ? group by status_id;";
		List<CountJobUser> list = new ArrayList<CountJobUser>();
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				CountJobUser CJU = new CountJobUser();
				CJU.setStatusname(res.getString("statusName"));
				CJU.setCount(res.getString("count"));
				list.add(CJU);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
