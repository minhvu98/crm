package com.myclass.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.dao.InformationDao;
import com.myclass.dao.UserDao;
import com.myclass.dto.LoginDto;
import com.myclass.dto.UserDetailDto;
import com.myclass.dto.UserDto;

@WebServlet(name = "InforServlet", urlPatterns = {"/infor"})
public class InformationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InformationDao infDao = null;
	private UserDao userDao = null;
	
	@Override
	public void init() throws ServletException {
		infDao = new InformationDao();
		userDao = new UserDao();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		LoginDto loginDto = (LoginDto)session.getAttribute("USER_LOGIN");
		int id = loginDto.getId();
		System.out.println(id);
		
		
		req.setAttribute("count", infDao.getCountUser(id));
		req.setAttribute("InforUser", infDao.getInforUserById(id));
		req.setAttribute("UserJob", infDao.getTaskJobUserById(id));
		req.getRequestDispatcher("/WEB-INF/views/inforuser/index.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String fullname = req.getParameter("fullname");
		String avatar = req.getParameter("avatar");
		String phone = req.getParameter("phone");
		
		 
		UserDetailDto UserDto = new UserDetailDto();
		UserDto.setEmail(email);
		UserDto.setFullname(fullname);
		UserDto.setAvatar(avatar);
		UserDto.setPassword(password);
		UserDto.setPhone(phone);

		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
		UserDto.setPassword(hashed);
		switch (action) {
		case "/infor":
			HttpSession session = req.getSession();
			LoginDto loginDto = (LoginDto)session.getAttribute("USER_LOGIN");
			int id = loginDto.getId();
			UserDto.setId(id);
			userDao.updateUser(UserDto);
			break;

		default:
			break;
		}
		// Nếu thành công thì chuyển hướng về trang danh sách (load lại danh sách quyền)
				resp.sendRedirect(req.getContextPath() + "/home");
	}
	
}
