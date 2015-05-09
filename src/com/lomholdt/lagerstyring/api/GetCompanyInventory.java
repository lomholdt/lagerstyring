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
import com.lomholdt.lagerstyring.model.Authenticator;
import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.Inventory;
import com.lomholdt.lagerstyring.model.InventoryStatements;
import com.lomholdt.lagerstyring.model.Messages;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class GetCompanyInventory
 */
@WebServlet("/GetCompanyInventory")
public class GetCompanyInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Authenticator auth = new Authenticator();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCompanyInventory() {
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
			e1.printStackTrace();
		}
		
		
		try {
			InventoryStatements is = new InventoryStatements();
			String companyId = request.getParameter("companyId");
			ArrayList<Inventory> inventory = is.getAllInventory(Integer.parseInt(companyId));
			
			Gson gs = new Gson();
			PrintWriter pw = response.getWriter();
			pw.print(gs.toJson(inventory));	
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
