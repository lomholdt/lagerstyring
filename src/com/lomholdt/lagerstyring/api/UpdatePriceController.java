package com.lomholdt.lagerstyring.api;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * Servlet implementation class UpdatePriceController
 */
@WebServlet("/UpdatePriceController")
public class UpdatePriceController extends HttpServlet {
	Authenticator auth = new Authenticator();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePriceController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
//		String uPrice = request.getParameter("uPrice");
//		String uSalesPrice = request.getParameter("uSalesPrice");
		String newValue = request.getParameter("newValue");
		String type = request.getParameter("type");
		String inventoryId = request.getParameter("inventoryId");
		Pattern p = Pattern.compile("(\\d)+([,\\.])?(\\d)*");
		InventoryStatements is = new InventoryStatements();
		
		try {
			if(user == null || !auth.is("manager", user.getId())){
				System.out.println("1");
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
				response.sendRedirect("count");
				return;
			}
			else if(inventoryId == null || inventoryId.isEmpty()){
				return;
			}
			else if(!is.userOwnsInventory(user.getCompanyId(), Integer.parseInt(inventoryId))){
				System.out.println("2");
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to perform this action.");
				response.sendRedirect("count");
				return;
			
			}
			if(is.inventoriesStorageIsOpen(Integer.parseInt(inventoryId))){
				System.out.println("3");
				FlashMessage.setFlashMessage(request, "error", "Please close storages before changeing price");
				response.sendRedirect("inventory");
				return;
			}
		} catch (Exception e1) {
			System.out.println("ERROR");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			if(newValue != null) newValue = newValue.replaceAll(",", ".");
			
			if (!newValue.isEmpty()){ // update price
				Matcher up = p.matcher(newValue);
				if(up.matches()){
					if(type.equals("uPrice")){
						is.updatePrice(Double.parseDouble(newValue), Integer.parseInt(inventoryId));						
					}
					else if(type.equals("uSalesPrice")){
						is.updateSalesPrice(Double.parseDouble(newValue), Integer.parseInt(inventoryId));
					}
					
				}
				
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
