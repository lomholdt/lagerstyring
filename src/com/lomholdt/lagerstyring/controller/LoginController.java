package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.LoginStatements;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user != null){
			response.sendRedirect("count");
			return;
		}
		
		RequestDispatcher view = request.getRequestDispatcher("views/login/login.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FlashMessage.getFlashMessage(request, "msg");
		FlashMessage.getFlashMessage(request, "error");
		LoginStatements ls = new LoginStatements();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String companyName = request.getParameter("companyName");
		
			try {
				if(ls.login(username, password, companyName)) {
					User currentUser = ls.getUser(username, companyName);
					HttpSession session = request.getSession();
					session.setAttribute("user", currentUser);
					response.sendRedirect("count"); // main page				
				}
				else {
					request.setAttribute("error", "Company, username or password was incorrect");
					RequestDispatcher view = request.getRequestDispatcher("views/login/login.jsp");
					view.forward(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
