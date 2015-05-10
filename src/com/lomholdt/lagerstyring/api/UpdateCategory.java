package com.lomholdt.lagerstyring.api;

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
import com.lomholdt.lagerstyring.model.Messages;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class UpdateCategory
 */
@WebServlet("/UpdateCategory")
public class UpdateCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Authenticator auth = new Authenticator();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCategory() {
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
		
		String inventoryId = request.getParameter("inventoryId");
		String categoryId = request.getParameter("categoryId");
		if(inventoryId == null || inventoryId.isEmpty() || categoryId == null || categoryId.isEmpty()){
			// error
			return;
		}
				
		try {
			
			InventoryStatements is = new InventoryStatements();
			
			if(categoryId.equals("none")){
				// delete category for inventory
				is.deleteInventoryCategory(Integer.parseInt(inventoryId));
			}
			else{
				// update cateogry
				is.updateCategory(Integer.parseInt(inventoryId), Integer.parseInt(categoryId));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
