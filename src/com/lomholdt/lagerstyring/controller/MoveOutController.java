package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.lomholdt.lagerstyring.model.Station;
import com.lomholdt.lagerstyring.model.Storage;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class MoveOutController
 */
@WebServlet("/MoveOutController") // AFGANG
public class MoveOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Authenticator auth = new Authenticator();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoveOutController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			if(user == null || !auth.is("user", user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
				response.sendRedirect("");
				return;
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String storageId = request.getParameter("sid");
		if(storageId != null && !storageId.isEmpty()){
			InventoryStatements is = new InventoryStatements();
			try {
				if(!is.storageIsOpen(Integer.parseInt(storageId))){
					FlashMessage.setFlashMessage(request, "error", "The storage is not open.");
					response.sendRedirect("move");
					return;
				}
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Storage storage = is.getStorageWithInventory(Integer.parseInt(storageId));
				ArrayList<Station> primaryStations = is.getStations(user.getCompanyId(), "primary");
				ArrayList<Station> secondaryStations = is.getStations(user.getCompanyId(), "secondary");
				request.setAttribute("primaryStations", primaryStations);
				request.setAttribute("secondaryStations", secondaryStations);
				request.setAttribute("storage", storage);				
			} catch (Exception e) {
				System.out.println("Error in move out controller");
				e.printStackTrace();
			}
		}
		else{
			FlashMessage.setFlashMessage(request, "error", "No storage was chosen, please try again.");
			response.sendRedirect("move");
			return;
		}
		
		RequestDispatcher view = request.getRequestDispatcher("views/inventory/move-out.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		try {
			if(user == null || !auth.is("user", user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
				response.sendRedirect("");
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String storageId = request.getParameter("sid");
		String stationId = request.getParameter("stationId");
		
		if(storageId == null || storageId.isEmpty()) {
			FlashMessage.setFlashMessage(request, "error", "No storage was chosen, please try again.");
			response.sendRedirect("move");
			return;
		}
		else if(stationId == null || stationId.isEmpty()){
			FlashMessage.setFlashMessage(request, "error", "No station was chosen, please try again.");
			response.sendRedirect("move");
			return;
		}



		Map<String, String[]> m  = request.getParameterMap();
		
		InventoryStatements is = new InventoryStatements();
		// Must ensure no impossible decrements and ignore empty and 0 input
//		for(Map.Entry<String, String[]> entry : m.entrySet()){
//			if(entry.getKey().equals("sid") || entry.getKey().equals("stationId")) continue;
//			if(entry.getValue()[0].equals("0") || entry.getValue()[0].isEmpty()) continue;
//			int newValue = is.currentInventoryUnits(Integer.parseInt(entry.getKey())) - Integer.parseInt(entry.getValue()[0]);
//			if (newValue < 0){
//				// CANNOT DECREMENT TO LESS THAN ZERO - ABORT
//				FlashMessage.setFlashMessage(request, "error", "Cannot decrement to less than 0");
//				response.sendRedirect("move"); 
//				return;
//			}
//		}
		
		// Update the values that are not zero
		String inventoryOverview = "";
		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getKey().equals("sid") || entry.getKey().equals("stationId")) continue;
			// TODO Need to secure that updated id's belong to the user updating!
			if(!entry.getValue()[0].equals("0") && !entry.getValue()[0].isEmpty()){
				// DECREMENT THIS AMOUNT FROM DATABASE
				try {
					is.decrementUnits(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()[0]));
					String inventoryName = is.getInventoryName(Integer.parseInt(entry.getKey()));
					String amount = entry.getValue()[0];
					inventoryOverview += String.format("-%s %s <br>", amount, inventoryName);
					is.addToInventoryLog(inventoryName, 
							-Integer.parseInt(amount), 
							Integer.parseInt(storageId), 
							Integer.parseInt(stationId),
							"Afgang", 
							is.getInventoryPrice(Integer.parseInt(entry.getKey())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		

		try {
			String msg = "<h5>"+"Afgang gennemf¿rt fra " + is.getStorage(Integer.parseInt(storageId)).getName() + " til " + is.getStation(Integer.parseInt(stationId)).getName() + "</h5>" + inventoryOverview;
			FlashMessage.setFlashMessage(request, "msg", msg);
			response.sendRedirect("move");	
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
