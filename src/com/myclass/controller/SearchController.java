package com.myclass.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myclass.connection.JDBCConnection;
import com.myclass.dao.SearchDao;
import com.myclass.dao.UserDao;
import com.myclass.dto.UserDto;
import com.myclass.entity.User;

import java.sql.*;
import java.util.*;

/**
 * Servlet implementation class SearchMovie
 */
@WebServlet(name = "SearchServlet", urlPatterns = "/search")
public class SearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SearchDao searchDao = null;
    @Override
    	public void init() throws ServletException {
    		searchDao = new SearchDao();
    	}
    
    @Override
    	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
    			throws ServletException, IOException {
    	String action = req.getServletPath();
		switch (action) {
		case "/search":

			req.getRequestDispatcher("/WEB-INF/views/search/index.jsp").forward(req, resp);
			break;
		
		default:
			break;
		}
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
    		throws ServletException, IOException {
    	
    	
    	String action = req.getServletPath();
    	String txtSearch = req.getParameter("txtSearch");
    	List<UserDto> list = searchDao.SearchByFullName(txtSearch);
    	
			
			
			
			req.setAttribute("list", list);
			req.getRequestDispatcher("/WEB-INF/views/search/index.jsp").forward(req, resp);

		
    }
}