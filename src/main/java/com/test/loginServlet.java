package com.test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class loginServlet
 */
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public loginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("username");
		String upwd = request.getParameter("password");
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		// Database connection
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false", "root", "Qwerty123@");
			PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE uname = ? AND upwd = ?");
			pst.setString(1, uname);
			pst.setString(2, upwd);
			ResultSet rs = pst.executeQuery();
			System.out.println(rs);

			if (rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				response.sendRedirect("home");
			} else {
				request.setAttribute("status", "fail");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// database close
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
