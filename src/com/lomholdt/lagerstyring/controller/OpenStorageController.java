package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
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
		String storageId = request.getParameter("sid");
		InventoryStatements is = new InventoryStatements();
		try {
			if(user == null || !auth.is("user", user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You Do not have permission to see this page.");
				response.sendRedirect("");
				return;
			}
			if(!is.userOwnsStorage(Integer.parseInt(storageId), user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to open this storage.");
				response.sendRedirect("");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Storage storage = is.getStorageWithInventory(Integer.parseInt(storageId));
			request.setAttribute("storage", storage);
		} catch (Exception e) {
			// TODO: handle exception
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
		String storageId = request.getParameter("sid");
		String isUpdate = request.getParameter("update");
		InventoryStatements is = new InventoryStatements();
		try {
			if(user == null || !auth.is("user", user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
				response.sendRedirect("");
				return;
			}
			if(!is.userOwnsStorage(Integer.parseInt(storageId), user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to open this storage.");
				response.sendRedirect("count");
				return;
			}
			if(isUpdate == null || isUpdate.isEmpty()){
				doGet(request, response);
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				
		if(storageId == null || storageId.isEmpty()) {
			FlashMessage.setFlashMessage(request, "error", "No storage was chosen, please try again.");
			response.sendRedirect("count");
			return;
		}
		
		try {
			if(is.storageIsOpen(Integer.parseInt(storageId))){
				FlashMessage.setFlashMessage(request, "error", "The storage is already open");
				response.sendRedirect("count");
				return;
			}
			Storage storage = is.getStorageWithInventory(Integer.parseInt(storageId));
			request.setAttribute("storage", storage);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		Map<String, String[]> m  = request.getParameterMap();
		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getValue()[0].equals("")){
				request.setAttribute("sid", storageId);
				FlashMessage.setFlashMessage(request, "error", "Empty input is not allowed");
				response.sendRedirect("count"); 
				return;
			}
		}

		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getKey().equals("sid") || entry.getKey().equals("update")) continue;
			try {
				// TODO Need to secure that updated id's belong to the user updating!
				is.updateUnitsAt(Integer.parseInt(entry.getKey()), Double.parseDouble(entry.getValue()[0]));				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			is.changeStorageStatus(Integer.parseInt(storageId));			
			is.getStorage(Integer.parseInt(storageId));
			String storageName = is.getStorageName(Integer.parseInt(storageId));
			is.addToStorageLog(storageName, Integer.parseInt(storageId), "Åben");
			is.openArchiveLog(storageName, Integer.parseInt(storageId));
			is.setInventoryAtOpen(Integer.parseInt(storageId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		FlashMessage.setFlashMessage(request, "msg", "Lageret er nu Åbnet");
		response.sendRedirect("count");
	}

}
