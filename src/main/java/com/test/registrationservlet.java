package com.test;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class registrationservlet
 */
public class registrationservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public registrationservlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		dispatcher = request.getRequestDispatcher("registration.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		RequestDispatcher dispatcher = null;
		// Database connection
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false", "root", "Qwerty123@");
			PreparedStatement pst = con
					.prepareStatement("INSERT INTO users(uname,upwd,uemail,umobile) VALUES(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(3, uemail);
			pst.setString(2, upwd);
			pst.setString(4, umobile);
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if (rowCount > 0) {
				request.setAttribute("status", "success");
			} else {
				request.setAttribute("status", "fail");
			}
			dispatcher.forward(request, response);
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

//		PrintWriter writer = response.getWriter();
//		writer.print("Working"+uname+uemai+".");
	}

}
