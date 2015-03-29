package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.SendResult;

import com.lomholdt.lagerstyring.model.Authenticator;
import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.InventoryStatements;
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
		if(user == null || !auth.is("manager", user.getId())){
			FlashMessage.setFlashMessage(request, "error", NO_PERMISSION_MESSAGE);
			response.sendRedirect("");
			return;
		}
		// User is allowed to be here
		
		ArrayList<Storage> storages = new InventoryStatements().getStorages(user.getCompanyId());
		if (storages.size() == 0){
			// Throw user to create storage site if he has no storages created
			return;
		}
		request.setAttribute("storages", storages);		
		RequestDispatcher view = request.getRequestDispatcher("views/inventory/add.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null || !auth.is("manager", user.getId())){
			FlashMessage.setFlashMessage(request, "error", NO_PERMISSION_MESSAGE);
			response.sendRedirect("");
			return;
		}
		String storage = request.getParameter("storage");
		String name = request.getParameter("name");
		String units = request.getParameter("units");
		
		if(storage == null || name == null || units == null
				|| storage.isEmpty() || name.isEmpty() || units.isEmpty()){
			request.setAttribute("error", "An error occured, did you fill out all the field?");
			doGet(request, response);
			return;
		}
		
		
		// We can safely add the item to the storage
		InventoryStatements is = new InventoryStatements();
		int storageId = is.getStorageId(user.getCompanyId(), storage);
		if(is.inventoryExists(name, storageId)){
			request.setAttribute("error", "The item " + name + " already exists in " + storage);		
			doGet(request, response);
			return;
		}

		try {
			is.addInventory(name, Integer.parseInt(units), storageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("msg", "Succesfully added " + name + " to " + storage);		
		doGet(request, response);
	}
}
