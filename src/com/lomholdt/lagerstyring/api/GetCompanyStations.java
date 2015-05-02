package com.lomholdt.lagerstyring.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

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
import com.lomholdt.lagerstyring.model.Station;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class GetCompanyStations
 */
@WebServlet("/GetCompanyStations")
public class GetCompanyStations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Authenticator auth = new Authenticator();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCompanyStations() {
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
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			if(user == null || !auth.is("admin", user.getId())){
				FlashMessage.setFlashMessage(request, "error", Messages.ERROR_NO_PERMISSION_TO_VIEW_PAGE);
				response.sendRedirect("count");
				return;
			}
		} catch (SQLException e1) {
			System.out.print("Permission denied for getting company stations");
			e1.printStackTrace();
		}
		
		try {
			Gson gs = new Gson();
			InventoryStatements is = new InventoryStatements();
			int companyId = Integer.parseInt(request.getParameter("companyId"));
			ArrayList<Station> alp =  is.getStations(companyId, "primary");
			ArrayList<Station> als =  is.getStations(companyId, "secondary");
			alp.addAll(als);
			
			PrintWriter pw = response.getWriter();
			pw.print(gs.toJson(alp));
			
		} catch (Exception e) {
			System.out.println("Could not get company stations");
			e.printStackTrace();
		}
		
	}

}
