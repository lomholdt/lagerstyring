package com.lomholdt.lagerstyring.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.lomholdt.lagerstyring.model.AdminStatements;
import com.lomholdt.lagerstyring.model.Authenticator;
import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.InventoryStatements;
import com.lomholdt.lagerstyring.model.Messages;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class DeleteCompany
 */
@WebServlet("/DeleteCompany")
public class DeleteCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Authenticator auth = new Authenticator();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCompany() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
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
				response.sendRedirect("count");
				return;
			}
		} catch (SQLException e1) {
			System.out.print("Permission denied for deleting company. Unauthorized user was " + user.getUsername() + " at " + user.getCompanyName() + 
					" with user id " + user.getId());
			e1.printStackTrace();
		}
		
		
		try {
			AdminStatements as = new AdminStatements();
			String companyId = request.getParameter("companyId");
			Gson gs = new Gson();
			PrintWriter pw = response.getWriter();
			if(as.companyExists(Integer.parseInt(companyId)) && as.deleteCompany(Integer.parseInt(companyId))){
				pw.print(gs.toJson("true"));
			}
			else{
				pw.print(gs.toJson("false"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
