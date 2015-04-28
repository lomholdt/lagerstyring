package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.lomholdt.lagerstyring.model.Authenticator;
import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.InventoryStatements;
import com.lomholdt.lagerstyring.model.Station;
import com.lomholdt.lagerstyring.model.Storage;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class AddInventoryController
 */
@WebServlet("/AddInventoryController")
public class AddInventoryController extends HttpServlet {
	Authenticator auth = new Authenticator();
	private static final long serialVersionUID = 1L;
	private static final String NO_PERMISSION_MESSAGE = "You do not have permission to see this page.";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddInventoryController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		FlashMessage.getFlashMessage(request, "error");
		
		try {
			// User is allowed to be here
			InventoryStatements is = new InventoryStatements();
			ArrayList<Storage> storages = is.getStorages(user.getCompanyId());
			if (storages.size() == 0){
				// Throw user to create storage site if he has no storages created
				FlashMessage.setFlashMessage(request, "error", "You have no storages. Please create one first.");
				response.sendRedirect("count");
				return;
			}
			request.setAttribute("storages", storages);
			RequestDispatcher view = request.getRequestDispatcher("views/inventory/add.jsp");
			// get the inventory for deletion
			request.setAttribute("allInventory", is.getAllInventory(user.getCompanyId()));
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		String storage = request.getParameter("storage");
		String name = request.getParameter("name");
		String units = request.getParameter("units");
		String price = request.getParameter("price");
		String salesPrice = request.getParameter("salesPrice");
		price = price.replaceAll(",", "."); // if input uses comma replace with compatible dot
		
		Pattern p = Pattern.compile("(\\d)+([,\\.])?(\\d)*");
		Matcher m = p.matcher(price);
		Matcher sp = p.matcher(salesPrice);
		
		
		if(storage == null || name == null || units == null || price == null || salesPrice == null
				|| storage.isEmpty() || name.isEmpty() || units.isEmpty() || price.isEmpty() || salesPrice.isEmpty() || !m.matches() || !sp.matches()){
			request.setAttribute("error", "An error occured, did you fill out all the field?");
			doGet(request, response);
			return;
		}
		
		
		// We can safely add the item to the storage
		try {
			InventoryStatements is = new InventoryStatements();
			int storageId = is.getStorageId(user.getCompanyId(), storage);
			ArrayList<Station> primaryStations = is.getStations(user.getCompanyId(), "primary");
			ArrayList<Station> secondaryStations = is.getStations(user.getCompanyId(), "secondary");
			request.setAttribute("primaryStations", primaryStations);
			request.setAttribute("secondaryStations", secondaryStations);
			if(is.inventoryExists(name, storageId)){
				request.setAttribute("error", "The item " + name + " already exists in " + storage);		
				doGet(request, response);
				return;
			}
			is.addInventory(name, Integer.parseInt(units), storageId, Double.parseDouble(price), Double.parseDouble(salesPrice));
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("msg", "Succesfully added " + name + " to " + storage);		
		doGet(request, response);
	}
}
