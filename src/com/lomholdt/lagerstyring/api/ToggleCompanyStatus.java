package com.lomholdt.lagerstyring.api;

import java.io.IOException;
import java.sql.SQLException;

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

/**
 * Servlet implementation class ToggleCompanyStatus
 */
@WebServlet("/ToggleCompanyStatus")
public class ToggleCompanyStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Authenticator auth = new Authenticator();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToggleCompanyStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			if(user == null || !auth.is("admin", user.getId())){
				FlashMessage.setFlashMessage(request, "error", Messages.ERROR_NO_PERMISSION_TO_VIEW_PAGE);
				response.sendRedirect("count");
				return;
			}
		} catch (SQLException e1) {
			System.out.print("Permission denied for changeing company status");
			e1.printStackTrace();
		}
			
		try {
			AdminStatements as = new AdminStatements();
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			as.changeCompanyStatus(companyId);
		} catch (Exception e) {
			System.out.println("Could not change company status");
			e.printStackTrace();
		}
	}
}
