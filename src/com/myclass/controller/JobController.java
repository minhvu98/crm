package com.myclass.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myclass.dao.JobDao;
import com.myclass.dao.TaskDao;
import com.myclass.dao.UserDao;
import com.myclass.dto.TaskJobDto;
import com.myclass.entity.GroupTask;
import com.myclass.entity.Job;

@WebServlet(urlPatterns = {"/job", "/job/add", "/job/edit", "/job/details", "/job/delete"})
public class JobController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JobDao jobDao = null;
	private UserDao userDao = null;
	
	@Override
	public void init() throws ServletException {
		jobDao = new JobDao();
		userDao = new UserDao();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		String id = req.getParameter("id");
		switch (action) {
			case "/job":
				req.setAttribute("jobs", jobDao.findAll());
				req.getRequestDispatcher("/WEB-INF/views/job/index.jsp").forward(req, resp);
				break;
			case "/job/add":
				req.getRequestDispatcher("/WEB-INF/views/job/index.jsp").forward(req, resp);
				break;
			case "/job/edit":
				req.setAttribute("job", jobDao.findById(Integer.parseInt(id)));
				req.getRequestDispatcher("/WEB-INF/views/job/edit.jsp").forward(req, resp);
				break;
			case "/job/details":
				req.setAttribute("total", jobDao.Total());
				req.setAttribute("job", jobDao.findById(Integer.parseInt(id)));
				
				req.setAttribute("test", jobDao.getAccountById(Integer.parseInt(id)));
				req.setAttribute("countJob", jobDao.getCountById());
				req.getRequestDispatcher("/WEB-INF/views/job/details.jsp").forward(req, resp);
				break;
			case "/job/delete":
				int idDel = Integer.valueOf(req.getParameter("id"));
				jobDao.deleteById(idDel);
				resp.sendRedirect(req.getContextPath() + "/job");
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
		
		Job job = new Job();
		job.setName(name);
		job.setStartDate(startDate);
		job.setEndDate(endDate);
		int id = Integer.valueOf(req.getParameter("id"));
		switch (action) {
			case "/job/add":
				jobDao.add(job);
				break;
			case "/job/edit":
				job.setId(id);
				jobDao.update(job);
				break;
			default:
				break;
		}
		resp.sendRedirect(req.getContextPath() + "/job");
	}
}
