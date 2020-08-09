package com.myclass.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myclass.dto.LoginDto;

@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//Ép kiểu ServletRequest sang HttpServletRequest để sử dụng hàm getSession()
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		String action = req.getServletPath();
		
		// ------- NẾU VÀO TRANG LOGIN THÌ KHÔNG CẦN KIỂM TRA -------
		if(action.startsWith("/login")) {
			chain.doFilter(request, response);
			return;
		}
		
		// -----===== KIỂM TRA ĐĂNG NHẬP ======-----	
		HttpSession session = req.getSession();
		LoginDto loginDto = (LoginDto)session.getAttribute("USER_LOGIN");
		if (loginDto == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		
		// -----==== PHÂN QUYỀN NGƯỜI DÙNG =====-------
		// B1. KIỂM TRA URL
		String roleName = loginDto.getRoleName();
		if(action.startsWith("/role") && !roleName.equals("ROLE_ADMIN")) {
			resp.sendRedirect(req.getContextPath() + "/error/forbidden");
			return;
		}
		
		if(action.startsWith("/user") && roleName.equals("ROLE_USER")) {
			resp.sendRedirect(req.getContextPath() + "/error/forbidden");
			return;
		}
		
		chain.doFilter(request, response);
	}

}
