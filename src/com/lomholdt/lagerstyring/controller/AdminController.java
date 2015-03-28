package com.lomholdt.lagerstyring.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lomholdt.lagerstyring.model.AdminStatements;
import com.lomholdt.lagerstyring.model.Authenticator;
import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	Authenticator auth = new Authenticator();
	AdminStatements as = new AdminStatements();
	final static String NO_PERMISSION_MESSAGE = "You do not have permission to see this page.";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null || !auth.is("admin", user.getId())){
			FlashMessage.setFlashMessage(request, "error", NO_PERMISSION_MESSAGE);
			response.sendRedirect("");
			return;
		}
		
		
		
		request.setAttribute("companies", as.getCompanies());
		
		RequestDispatcher view = request.getRequestDispatcher("views/admin/index.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null || !auth.is("admin", user.getId())){
			FlashMessage.setFlashMessage(request, "error", NO_PERMISSION_MESSAGE);
			response.sendRedirect("");
			return;
		}
		
		String companyName = request.getParameter("companyName");
		
		if(companyName != null && !companyName.isEmpty()){
			// Create new company;
			if(!as.companyExists(companyName)){
				as.addNewCompany(companyName);
				request.setAttribute("msg", companyName + " succesfully added.");
			}
			else{
				request.setAttribute("error", "Company already exists.");
			}
			doGet(request, response);
			return;
		}
		
		
		
		
		
	}

}
