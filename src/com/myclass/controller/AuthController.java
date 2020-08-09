package com.myclass.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.dao.UserDao;
import com.myclass.dto.LoginDto;
import com.myclass.entity.User;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class AuthController extends HttpServlet {

	private UserDao userDao = null;
	
	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
			case "/login":
				req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
				break;
			case "/logout":
				// XÓA SESSION
				HttpSession session = req.getSession();
				session.removeAttribute("USER_LOGIN");
				// CHUYỂN HƯỚNG VỀ TRANG LOGIN
				resp.sendRedirect(req.getContextPath() + "/login");
			default:
				break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//B1: LẤY THÔNG TIN ĐĂNG NHẬP TỪ FORM
		String email = req.getParameter("email");
		String password =  req.getParameter("password");
		
		// B2: Gọi hàm truy vấn kiểm tra email từ tầng DAO
			LoginDto user = userDao.checkLogin(email);
			
		// - Nếu trả về null -> foward về lại trang login.jsp để thông báo sai email
			if(user == null) {
				req.setAttribute("message", "Sai thông tin đăng nhập!");
				req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
				System.out.println("Sai email");
			}
			
		// - Nếu trả về khác null -> CHuyển qua bước 3
		// B3: So sánh mật khẩu
		// - Nếu mật khẩu ko khớp -> foward về lại trang login.jsp để thông báo sai mật khẩu
			if(!BCrypt.checkpw(password, user.getPassword())){
				req.setAttribute("message", "Sai thông tin đăng nhập!");
				System.out.println("Sai Pass");
				req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
			}
		// - Nếu mật khẩu khớp -> CHuyển qua bước 4
		// B4: Lưu thông tin user vào Session
			
			// Tạo Session
			HttpSession session = req.getSession();
			// Set thông tin user vào session
			session.setAttribute("USER_LOGIN", user);
		
		// B5: Chuyển hướng về trang chủ
			resp.sendRedirect(req.getContextPath() + "/home");
			
	}
}
