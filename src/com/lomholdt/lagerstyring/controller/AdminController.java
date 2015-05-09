package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.SQLException;

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
import com.lomholdt.lagerstyring.model.Messages;
import com.lomholdt.lagerstyring.model.User;
import com.lomholdt.lagerstyring.model.UserStatements;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	Authenticator auth = new Authenticator();
	AdminStatements as = new AdminStatements();
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
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			if(user == null || !auth.is("admin", user.getId())){
				FlashMessage.setFlashMessage(request, "error", Messages.ERROR_NO_PERMISSION_TO_VIEW_PAGE);
				response.sendRedirect("");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			request.setAttribute("companies", as.getCompanies());
			
			RequestDispatcher view = request.getRequestDispatcher("views/admin/index.jsp");
			view.forward(request, response);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			if(user == null || !auth.is("admin", user.getId())){
				FlashMessage.setFlashMessage(request, "error", Messages.ERROR_NO_PERMISSION_TO_VIEW_PAGE);
				response.sendRedirect("");
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String companyName = request.getParameter("companyName");
		String userCompany = request.getParameter("userCompany");
		String userRole = request.getParameter("userRole");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String stationCompany = request.getParameter("stationCompany");
		String newStationName = request.getParameter("newStationName");
		String newStationImportance = request.getParameter("newStationImportance");
		String storageCompany = request.getParameter("storageCompany");
		String newStorageName = request.getParameter("newStorageName");
		String categoryCompany = request.getParameter("categoryCompany");
		String newCategoryName = request.getParameter("newCategoryName");
		
		
		
		if(companyName != null && !companyName.isEmpty()){
			// Create new company;
			try {
				if(!as.companyExists(companyName)){
					as.addNewCompany(companyName);
					request.setAttribute("msg", companyName + " succesfully added.");
				}
				else{
					request.setAttribute("error", "Company already exists.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				if(!us.userExists(username, userCompany)){
					// add the new user 
					int companyId = us.getCompanyId(userCompany);
					us.addUserToCompany(companyId, userRole, username, password);
					us.addRoleToUser(username, companyId, userRole);
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
				newStationName != null && !newStationName.isEmpty() &&
				newStationImportance != null && !newStationImportance.isEmpty()){
			try {
				if(as.addStationToCompany(stationCompany, newStationName, newStationImportance)){
					request.setAttribute("msg", newStationImportance + " station " + newStationName + " succesfully added to " + stationCompany);
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
		else if (storageCompany != null && !storageCompany.isEmpty() &&
				newStorageName != null && !newStorageName.isEmpty()){
			try {
				// create the new storage for the company if it does not exists
				if(!as.storageExists(storageCompany, newStorageName)){
					int companyId = new UserStatements().getCompanyId(storageCompany);
					as.createNewStorage(newStorageName, companyId);
					request.setAttribute("msg", "Succesfully added " + newStorageName + " to " + storageCompany);
					doGet(request, response);
					return;
				}
				
			} catch (Exception e) {
				request.setAttribute("error", "An error occured while adding new storage");
				doGet(request, response);
				return;
				
			}
		}
		else if(categoryCompany != null && !categoryCompany.isEmpty()){
			try {
				// add the new category to company
				int companyId = new UserStatements().getCompanyId(categoryCompany);
				if(as.categoryExists(newCategoryName, companyId)){
					// category already exists
					request.setAttribute("error", Messages.ERROR_CATEGORY_ALREADY_EXISTS);
					doGet(request, response);
					return;
				}
				
				as.addNewCategory(newCategoryName, companyId);
				request.setAttribute("msg", Messages.addNewCategorySuccess(newCategoryName, categoryCompany));
				doGet(request, response);
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		request.setAttribute("error", Messages.ERROR_SOMETHING_WENT_WRONG);
		doGet(request, response);
	}


}
