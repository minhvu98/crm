package com.myclass.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myclass.dao.JobDao;
import com.myclass.dao.StatusDao;
import com.myclass.dao.TaskDao;
import com.myclass.dao.UserDao;
import com.myclass.entity.Task;

@WebServlet(urlPatterns = {"/task", "/task/add", "/task/edit","/task/delete"})
public class TaskController extends HttpServlet {

	private TaskDao taskDao = null;
	private StatusDao statusDao = null;
	private UserDao userDao = null;
	private JobDao jobDao = null;
	
	@Override
	public void init() throws ServletException {
		taskDao = new TaskDao();
		statusDao = new StatusDao();
		jobDao = new JobDao();
		userDao = new UserDao();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		String id = req.getParameter("id");
		switch (action) {
			case "/task":
				req.setAttribute("tasks", taskDao.findAll());
				req.getRequestDispatcher("/WEB-INF/views/task/index.jsp").forward(req, resp);
				break;
			case "/task/add":
				req.setAttribute("users", userDao.findAll());
				req.setAttribute("status", statusDao.findAll());
				req.setAttribute("jobs", jobDao.findAll());
				req.getRequestDispatcher("/WEB-INF/views/task/add.jsp").forward(req, resp);
				break;
			case "/task/edit":
				req.getRequestDispatcher("/WEB-INF/views/task/edit.jsp").forward(req, resp);
				break;
			case "/task/delete":
				int idDel = Integer.valueOf(req.getParameter("id"));
				taskDao.deleteById(idDel);
				resp.sendRedirect(req.getContextPath() + "/task");
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		
		String name = req.getParameter("name");
		Date startDate = Date.valueOf(req.getParameter("startDate"));
		Date endDate = Date.valueOf(req.getParameter("endDate"));
		int userId = Integer.valueOf(req.getParameter("userId"));
		int jobId = Integer.valueOf(req.getParameter("jobId"));
		int statusId = Integer.valueOf(req.getParameter("statusId"));
		
		Task task = new Task();
		task.setName(name);
		task.setStartDate(startDate);
		task.setEndDate(endDate);
		task.setUserId(userId);
		task.setJobId(jobId);
		task.setStatusId(statusId);
		
		switch (action) {
			case "/task/add":
				taskDao.add(task);
				break;
			case "/task/edit":
				int id = Integer.valueOf(req.getParameter("id"));
				break;
			default:
				break;
		}
		resp.sendRedirect(req.getContextPath() + "/task");
	}
}

