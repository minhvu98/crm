package com.myclass.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.dao.RoleDao;
import com.myclass.dao.UserDao;
import com.myclass.dto.UserDetailDto;
import com.myclass.entity.Role;
import com.myclass.entity.User;
import com.myclass.util.UrlConstants;

@WebServlet(name = "UserServlet", urlPatterns = { "/user", "/user/add", "/user/edit", "/user/delete", "/user/details" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = null;
	private RoleDao roleDao = null;

	@Override
	public void init() throws ServletException {
		userDao = new UserDao();
		roleDao = new RoleDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		String id = req.getParameter("id");
		switch (action) {
		case "/user":
			req.setAttribute("users", userDao.findAllUserRole());
			req.getRequestDispatcher("/WEB-INF/views/user/index.jsp").forward(req, resp);
			break;
		case "/user/add":
			req.setAttribute("roles", roleDao.findAll());
			req.getRequestDispatcher("/WEB-INF/views/user/add.jsp").forward(req, resp);
			break;
		case "/user/edit":
			req.setAttribute("user", userDao.findById(Integer.parseInt(id)));
			req.setAttribute("roles", roleDao.findAll());
			req.getRequestDispatcher("/WEB-INF/views/user/edit.jsp").forward(req, resp);
			break;
		case "/user/details":
			req.setAttribute("useru", userDao.findUserById(Integer.parseInt(id)));
			req.getRequestDispatcher("/WEB-INF/views/user/details.jsp").forward(req, resp);
			break;
		case "/user/delete":
			int idDel = Integer.valueOf(req.getParameter("id"));
			userDao.deleteById(idDel);
			resp.sendRedirect(req.getContextPath() + "/user");
			break;
		default:
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		resp.setCharacterEncoding("UTF-8");
		String action = req.getServletPath();

		// Lấy thông tin từ form
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String fullname = req.getParameter("fullname");
		String avatar = req.getParameter("avatar");
		String phone = req.getParameter("phone");
		
		
		User user = new User();
		user.setEmail(email);
		user.setFullname(fullname);
		user.setAvatar(avatar);
		user.setPassword(password);
		
		
	
		
		UserDetailDto UserDto = new UserDetailDto();
		UserDto.setEmail(email);
		UserDto.setFullname(fullname);
		UserDto.setAvatar(avatar);
		UserDto.setPassword(password);
		UserDto.setPhone(phone);

		int result = -1;
		
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
		switch (action) {
		case "/user/add":
			int roleId = Integer.valueOf(req.getParameter("roleId"));
			user.setRoleId(roleId);
			// MÃ HÓA MẬT KHẨU
			user.setPassword(hashed);
			// LƯU THÔNG TIN USER VÀO DB
			result = userDao.add(user);
			if (result == -1) {
				// Nếu thêm không thành công thì xuất thông báo cho người dùng biết
				req.setAttribute("message", "Thêm mới thất bại!");
				return;
			}
			break;
		case "/user/edit":
			
			int id = Integer.parseInt(req.getParameter("id"));
			UserDto.setPassword(hashed);
			UserDto.setId(id);
			userDao.updateUser(UserDto);
			break;
		case "/user/details":
			UserDto.setPassword(hashed);
			id = Integer.parseInt(req.getParameter("id"));
			UserDto.setId(id);
			userDao.updateUser(UserDto);
			break;
		default:
			break;
		}
		// Nếu thành công thì chuyển hướng về trang danh sách (load lại danh sách quyền)
		resp.sendRedirect(req.getContextPath() + "/user");
	}
}
