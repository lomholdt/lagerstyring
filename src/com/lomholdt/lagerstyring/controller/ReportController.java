package com.lomholdt.lagerstyring.controller;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

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
import com.lomholdt.lagerstyring.model.LoggedStation;
import com.lomholdt.lagerstyring.model.LoggedStorage;
import com.lomholdt.lagerstyring.model.Station;
import com.lomholdt.lagerstyring.model.Storage;
import com.lomholdt.lagerstyring.model.User;
import com.lomholdt.lagerstyring.model.UserStatements;

/**
 * Servlet implementation class ReportController
 */
@WebServlet("/ReportController")
public class ReportController extends HttpServlet {
	Authenticator auth = new Authenticator();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		String storageId = request.getParameter("storageId");
		InventoryStatements is = new InventoryStatements();
		ArrayList<LoggedStorage> al = new ArrayList<LoggedStorage>();
		
		if(storageId == null || storageId.isEmpty()){
			FlashMessage.setFlashMessage(request, "error", "No storage selected");
			response.sendRedirect("choose");
		}
		
		
		if(storageId.equals("allStorages")){
			// get all storages
			try {
				ArrayList<Storage> storages = is.getStorages(user.getCompanyId());
				for (Storage storage : storages) {
					ArrayList<LoggedStation> ls = getLogResults(request, response, user, Integer.toString(storage.getId()));
					if (ls.size() > 0){
						LoggedStorage lStorage = new LoggedStorage();
						lStorage.setStorage(storage);
						lStorage.setLoggedStation(ls);
						al.add(lStorage);
					}
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			ArrayList<LoggedStation> ls = getLogResults(request, response, user, storageId);
			if (ls.size() > 0){
				try {
					LoggedStorage lStorage = new LoggedStorage();
					lStorage.setStorage(is.getStorage(Integer.parseInt(storageId)));
					lStorage.setLoggedStation(ls);
					al.add(lStorage);					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("logResults", al);
		RequestDispatcher view = request.getRequestDispatcher("views/archive/report.jsp");
		view.forward(request, response);
		
		
		
		
		
		
	}
	
	
	public ArrayList<LoggedStation> getLogResults(HttpServletRequest request, HttpServletResponse response, User user, String storageId) throws IOException{
		ArrayList<LoggedStation> loggedStations = new ArrayList<LoggedStation>();
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String inventoryName = request.getParameter("inventoryName");
		String stationName = request.getParameter("stationName");
		
		// TODO TEST - REMOVE  
		String periods = request.getParameter("periods");
		String[] periodsArr = periods.split("&");
		from = periodsArr[0];
		to = periodsArr[1];
		
				
		// TEST
		
		if(from == null || from.isEmpty() ||
				to == null || to.isEmpty()){
			System.out.println("Returning null");
			return loggedStations;
		}
				
		InventoryStatements is = new InventoryStatements();
		UserStatements us = new UserStatements();
		
		Timestamp fromDate = Timestamp.valueOf(from);
		Timestamp toDate = Timestamp.valueOf(to);
		
		//Storage s = is.getStorage(Integer.parseInt(storageId));
//		fromDate.setHours(0);
//		fromDate.setMinutes(0);
//		fromDate.setSeconds(0);
//		
//		toDate.setDate(java.sql.Date.valueOf(to).getDay());
//		toDate.setTime(Calendar.getInstance().getTimeInMillis());
		

		try {			

			ArrayList<Station> stations = is.getStations(us.getCompanyId(user.getId()), "primary");
			ArrayList<Station> secondaryStations = is.getStations(us.getCompanyId(user.getId()), "secondary");
			stations.addAll(secondaryStations);
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
