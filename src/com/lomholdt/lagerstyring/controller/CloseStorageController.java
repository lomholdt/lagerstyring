package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.lomholdt.lagerstyring.model.LoggedInventory;
import com.lomholdt.lagerstyring.model.LoggedStation;
import com.lomholdt.lagerstyring.model.Station;
import com.lomholdt.lagerstyring.model.Storage;
import com.lomholdt.lagerstyring.model.User;
import com.lomholdt.lagerstyring.model.UserStatements;

/**
 * Servlet implementation class CloseStorageController
 */
@WebServlet("/CloseStorageController")
public class CloseStorageController extends HttpServlet {
	Authenticator auth = new Authenticator();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CloseStorageController() {
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
				FlashMessage.setFlashMessage(request, "error", "The storage is already closed");
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
		// We are good - Perform action on storage
		
		String search = request.getParameter("search");
		if (search != null && !search.isEmpty()){
			ArrayList<LoggedStation> ls = getLogResults(request, response, user, storageId);
			request.setAttribute("logResults", ls);
		}
				
		InventoryStatements is = new InventoryStatements();
		ArrayList<Station> primaryStations = is.getStations(user.getCompanyId(), "primary");
		ArrayList<Station> secondaryStations = is.getStations(user.getCompanyId(), "secondary");
		request.setAttribute("primaryStations", primaryStations);
		request.setAttribute("secondaryStations", secondaryStations);
		RequestDispatcher view = request.getRequestDispatcher("views/storage/close.jsp");
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
		}

		

		Map<String, String[]> m  = request.getParameterMap();
		// Sloppy but working method for ensuring no empty input
		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getValue()[0].equals("")){
				request.setAttribute("sid", sid);
				FlashMessage.setFlashMessage(request, "error", "Empty input is not allowed");
				response.sendRedirect("count"); 
				return;
			}
		}

		InventoryStatements is = new InventoryStatements();
		// Update all the values
		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getKey().equals("sid")) continue;
			// TODO Need to secure that updated id's belong to the user updating!
			is.updateUnitsAt(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()[0]));
		}
		is.changeStorageStatus(Integer.parseInt(sid));
		
		is.getStorage(Integer.parseInt(sid));
		try {
			is.addToStorageLog(is.getStorageName(Integer.parseInt(sid)), Integer.parseInt(sid), "Luk");
		} catch (Exception e) {
			e.printStackTrace();
		}

		FlashMessage.setFlashMessage(request, "msg", "Lageret er nu lukket");
		response.sendRedirect("count");
	}
	
	
	
	
	public ArrayList<LoggedStation> getLogResults(HttpServletRequest request, HttpServletResponse response, User user, String storageId) throws IOException{
		ArrayList<LoggedStation> loggedStations = new ArrayList<LoggedStation>();
//		String search = request.getParameter("search");
		//String from = request.getParameter("from");
		//String to = request.getParameter("to");
		String inventoryName = request.getParameter("inventoryName");
		String stationName = request.getParameter("stationName");
		
		
//		if(search == null || search.isEmpty() || 
//				from == null || from.isEmpty() ||
//				to == null || to.isEmpty()){
//			System.out.println("Returning null");
//			return loggedStations;
//		}
				
		InventoryStatements is = new InventoryStatements();
		UserStatements us = new UserStatements();
		
//		Timestamp fromDate = new Timestamp(java.sql.Date.valueOf(from).getTime());
//		Timestamp toDate = new Timestamp(java.sql.Date.valueOf(to).getTime());
		
		Storage s = is.getStorage(Integer.parseInt(storageId));
		Timestamp fromDate = new Timestamp(s.getOpenedAt().getTimeInMillis());
		Timestamp toDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		request.setAttribute("fromTimestamp", fromDate);
		request.setAttribute("toTimestamp", toDate);
		
//		System.out.println(fromDate);
//		System.out.println(toDate);
		
		try {			

			ArrayList<Station> stations = is.getStations(us.getCompanyId(user.getId()), "primary");
			for (Station station : stations) {
				LoggedStation ls;
						
				if (!inventoryName.equals("allInventory")){
					ls = is.getLoggedItems(fromDate, toDate, inventoryName, station.getId(), Integer.parseInt(storageId));
				}
				else{
					ls = is.getLoggedItems(fromDate, toDate, station.getId(), Integer.parseInt(storageId));					
				}
				
				
				if(!stationName.equals("allStations")){
					if (ls.getLoggedInventory().size() != 0 && station.getName().equals(stationName)) loggedStations.add(ls);
				}
				else{					
					if (ls.getLoggedInventory().size() != 0) loggedStations.add(ls);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loggedStations;
	}
	
}
