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
import com.lomholdt.lagerstyring.model.UserStatements;

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
		String userCompany = request.getParameter("userCompany");
		String userRole = request.getParameter("userRole");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String stationCompany = request.getParameter("stationCompany");
		String newStationName = request.getParameter("newStationName");
		
		
		
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
		else if (userCompany != null && !userCompany.isEmpty() &&
				userRole != null && !userRole.isEmpty() && 
				username != null && !username.isEmpty() &&
				password != null && !password.isEmpty()){
			UserStatements us = new UserStatements();
			try {
				System.out.println("Testing if user exists");
				if(!us.userExists(username)){
					// add the new user 
					int companyId = us.getCompanyId(userCompany);
					us.addUserToCompany(companyId, userRole, username, password);
					us.addRoleToUser(username, userRole);
					request.setAttribute("msg", "user succesfully added.");
					doGet(request, response);
					return;
				}
				else{
					request.setAttribute("error", "Username already exists");
					doGet(request, response);
					return;
					
				}
			} catch (Exception e) {	
				e.printStackTrace();
			}
		}
		else if(stationCompany != null && !stationCompany.isEmpty() && 
				newStationName != null && !newStationName.isEmpty()){
			System.out.println("Trying to add new station.");
			try {
				if(new AdminStatements().addStationToCompany(stationCompany, newStationName, "primary")){
					request.setAttribute("msg", "Station " + newStationName + " succesfully added to " + stationCompany);
					doGet(request, response);
					return;
				}
				else{
					request.setAttribute("error", "Station already exists");
					doGet(request, response);
					return;
				}
				
			} catch (Exception e) {
				request.setAttribute("error", "An error occured while adding new station");
				doGet(request, response);
				return;
			}
		}
		
		
		
		request.setAttribute("error", "Something went wrong. Please try again.");
		doGet(request, response);
	}

}
