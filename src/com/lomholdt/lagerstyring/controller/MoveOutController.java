package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null || !auth.is("user", user.getId())){
			FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
			response.sendRedirect("");
			return;
		}
		
		String storageId = request.getParameter("sid");
		if(storageId != null && !storageId.isEmpty()){
			InventoryStatements is = new InventoryStatements();
			if(!is.storageIsOpen(Integer.parseInt(storageId))){
				FlashMessage.setFlashMessage(request, "error", "The storage is not open.");
				response.sendRedirect("move");
				return;
			}
			Storage storage = is.getStorageWithInventory(Integer.parseInt(storageId));
			ArrayList<Station> stations = is.getStations(user.getCompanyId());
			request.setAttribute("stations", stations);
			request.setAttribute("storage", storage);
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
		if(user == null || !auth.is("user", user.getId())){
			FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
			response.sendRedirect("");
			return;
		}
		
		String storageId = request.getParameter("sid");
		String stationId = request.getParameter("stationId");
		
		
		System.out.println(storageId);
		if(storageId == null || storageId.isEmpty()) {
			FlashMessage.setFlashMessage(request, "error", "No storage was chosen, please try again.");
			response.sendRedirect("move");
			return;
		}
		else if(stationId == null || stationId.isEmpty()){
			System.out.println(stationId);
			FlashMessage.setFlashMessage(request, "error", "No station was chosen, please try again.");
			response.sendRedirect("move");
			return;
		}



		Map<String, String[]> m  = request.getParameterMap();
		
		InventoryStatements is = new InventoryStatements();
		// Must ensure no impossible decrements and ignore empty and 0 input
		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getKey().equals("sid") || entry.getKey().equals("stationId")) continue;
			if(entry.getValue()[0].equals("0") || entry.getValue()[0].isEmpty()) continue;
			int newValue = is.currentInventoryUnits(Integer.parseInt(entry.getKey())) - Integer.parseInt(entry.getValue()[0]);
			if (newValue < 0){
				// CANNOT DECREMENT TO LESS THAN ZERO - ABORT
				FlashMessage.setFlashMessage(request, "error", "Cannot decrement to less than 0");
				response.sendRedirect("move"); 
				return;
			}
		}
		
		// Update the values that are not zero
		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getKey().equals("sid") || entry.getKey().equals("stationId")) continue;
			// TODO Need to secure that updated id's belong to the user updating!
			if(!entry.getValue()[0].equals("0") && !entry.getValue()[0].isEmpty()){
				// DECREMENT THIS AMOUNT FROM DATABASE
				is.decrementUnits(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()[0]));
			}
		}

		String msg = "Afgang gennemført fra " + is.getStorage(Integer.parseInt(storageId)).getName() + " til " + is.getStation(Integer.parseInt(stationId)).getName();
		FlashMessage.setFlashMessage(request, "msg", msg);
		response.sendRedirect("move");	
	}

}
