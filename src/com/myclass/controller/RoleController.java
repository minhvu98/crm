package com.myclass.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myclass.dao.RoleDao;
import com.myclass.entity.Role;
import com.myclass.util.UrlConstants;

@WebServlet(name = "RoleServlet", urlPatterns = { UrlConstants.ROLE_ALL, UrlConstants.ROLE_ADD, UrlConstants.ROLE_EDIT,
		UrlConstants.ROLE_DELETE })
public class RoleController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RoleDao roleDao = null;

	@Override
	public void init() throws ServletException {
		roleDao = new RoleDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		switch (action) {
		case UrlConstants.ROLE_ALL:
			List<Role> list = roleDao.findAll();
			req.setAttribute("roles", list);
			req.getRequestDispatcher("/WEB-INF/views/role/index.jsp").forward(req, resp);
			break;
		case UrlConstants.ROLE_ADD:
			req.getRequestDispatcher("/WEB-INF/views/role/add.jsp").forward(req, resp);
			break;
		case UrlConstants.ROLE_EDIT:
			// Lấy id từ url
			int id = Integer.parseInt(req.getParameter("id"));
			// Gọi hàm getById của lớp DAO
			Role role = roleDao.findById(id);
			// Gửi role lấy từ DAO về cho edit.jsp
			req.setAttribute("role", role);
			req.getRequestDispatcher("/WEB-INF/views/role/edit.jsp").forward(req, resp);
			break;
		case UrlConstants.ROLE_DELETE:
			// Lấy id từ url
			id = Integer.parseInt(req.getParameter("id"));
			// Gọi hàm xóa của lớp DAO
			roleDao.deleteById(id);
			// Chuyển hướng về trang danh sách để load lại table
			resp.sendRedirect(req.getContextPath() + "/role");
			return;
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
		String name = req.getParameter("name");
		String desc = req.getParameter("desc");

		Role role = new Role();
		role.setName(name);
		role.setDescription(desc);

		int result = -1;
		switch (action) {
		case UrlConstants.ROLE_ADD:
			result = roleDao.add(role);
			if (result == -1) {
				// Nếu thêm không thành công thì xuất thông báo cho người dùng biết
				req.setAttribute("message", "Thêm mới thất bại!");
				req.getRequestDispatcher("/WEB-INF/views/role/add.jsp").forward(req, resp);
				return;
			}
			break;
		case UrlConstants.ROLE_EDIT:
			// Lấy id
			int id = Integer.parseInt(req.getParameter("id"));
			role.setId(id);

			result = roleDao.update(role);
			if (result == -1) {
				// Nếu cập nhật không thành công thì xuất thông báo cho người dùng biết
				req.setAttribute("message", "Cập nhật thất bại!");
				req.getRequestDispatcher("/WEB-INF/views/role/edit.jsp").forward(req, resp);
				return;
			}
			break;
		default:
			break;
		}

		// Nếu thành công thì chuyển hướng về trang danh sách (load lại danh sách quyền)
		resp.sendRedirect(req.getContextPath() + "/role");
	}
}
