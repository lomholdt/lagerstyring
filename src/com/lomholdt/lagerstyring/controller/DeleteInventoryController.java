package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lomholdt.lagerstyring.model.Authenticator;
import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.InventoryStatements;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class DeleteInventoryController
 */
@WebServlet("/DeleteInventoryController")
public class DeleteInventoryController extends HttpServlet {
	Authenticator auth = new Authenticator();
	private static final long serialVersionUID = 1L;
	private static final String NO_PERMISSION_MESSAGE = "You do not have permission to see this page.";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteInventoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			if(user == null || !auth.is("manager", user.getId())){
				FlashMessage.setFlashMessage(request, "error", NO_PERMISSION_MESSAGE);
				response.sendRedirect("");
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String[] inventoryIds = request.getParameterValues("i");
		
		if (inventoryIds != null && inventoryIds.length > 0){
			// TODO does user own the item he is trying to delete? IF NOT THROW HIM OUT!
			try {
				InventoryStatements is = new InventoryStatements();
				String deleteMsg = "";
				for (String inventoryId : inventoryIds) {
					String inventoryName = is.getInventoryName(Integer.parseInt(inventoryId));
					is.deleteInventory(Integer.parseInt(inventoryId));
					deleteMsg += inventoryName + " <br>";
				}
				FlashMessage.setFlashMessage(request, "msg", "Succesfully deleted <br>" + deleteMsg);
				response.sendRedirect("inventory");					
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			// get him outta here!
			FlashMessage.setFlashMessage(request, "error", "An error occured while deleting");
			response.sendRedirect("inventory");
			return;
		}
	}

}
