package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

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
import com.lomholdt.lagerstyring.model.Storage;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class OpenStorageController
 */
@WebServlet("/OpenStorageController")
public class OpenStorageController extends HttpServlet {
	Authenticator auth = new Authenticator();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpenStorageController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null || !auth.is("user", user.getId())){
			FlashMessage.setFlashMessage(request, "error", "You Do not have permission to see this page.");
			response.sendRedirect("");
			return;
		}
		
		String storageId = request.getParameter("sid");
		if(storageId != null && !storageId.isEmpty()){
			InventoryStatements is = new InventoryStatements();
			if(is.storageIsOpen(Integer.parseInt(storageId))){
				FlashMessage.setFlashMessage(request, "error", "The storage is already open");
				response.sendRedirect("count");
				return;
			}
			Storage storage = is.getStorageWithInventory(Integer.parseInt(storageId));
			request.setAttribute("storage", storage);
		}
		else{
			FlashMessage.setFlashMessage(request, "error", "No storage was chosen, please try again.");
			response.sendRedirect("count");
		}
		
		RequestDispatcher view = request.getRequestDispatcher("views/storage/open.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null || !auth.is("user", user.getId())){
			FlashMessage.setFlashMessage(request, "error", "You Do not have permission to see this page.");
			response.sendRedirect("");
			return;
		}

				
		String sid = request.getParameter("sid");
		if(sid == null || sid.isEmpty()) {
			FlashMessage.setFlashMessage(request, "error", "No storage was chosen, please try again.");
			response.sendRedirect("count");
			return;
		}


		Map<String, String[]> m  = request.getParameterMap();
		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getValue()[0].equals("")){
				request.setAttribute("sid", sid);
				FlashMessage.setFlashMessage(request, "error", "Empty input is not allowed");
				response.sendRedirect("count"); 
				return;
			}
		}

		InventoryStatements is = new InventoryStatements();
		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getKey().equals("sid")) continue;
			// TODO Need to secure that updated id's belong to the user updating!
			is.updateUnitsAt(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()[0]));
		}
		is.changeStorageStatus(Integer.parseInt(sid));
		
		is.getStorage(Integer.parseInt(sid));
		try {
			is.addToStorageLog(is.getStorageName(Integer.parseInt(sid)), Integer.parseInt(sid), "Ã…ben");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		FlashMessage.setFlashMessage(request, "msg", "Lageret er nu Œbnet");
		response.sendRedirect("count");
		

	}

}
