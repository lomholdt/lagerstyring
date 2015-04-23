package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lomholdt.lagerstyring.model.Authenticator;
import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.Inventory;
import com.lomholdt.lagerstyring.model.InventoryStatements;
import com.lomholdt.lagerstyring.model.LogBook;
import com.lomholdt.lagerstyring.model.LoggedStation;
import com.lomholdt.lagerstyring.model.LoggedStorage;
import com.lomholdt.lagerstyring.model.LoggedSummedInventory;
import com.lomholdt.lagerstyring.model.LoggedSummedStation;
import com.lomholdt.lagerstyring.model.LoggedSummedStorage;
import com.lomholdt.lagerstyring.model.Station;
import com.lomholdt.lagerstyring.model.Storage;
import com.lomholdt.lagerstyring.model.User;
import com.lomholdt.lagerstyring.model.UserStatements;

/**
 * Servlet implementation class PeriodController
 */
@WebServlet("/PeriodController")
public class PeriodController extends HttpServlet {
	Authenticator auth = new Authenticator();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PeriodController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		InventoryStatements is = new InventoryStatements();
		String storageId = request.getParameter("storageId");
		try {
			if(user == null || !auth.is("user", user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
				response.sendRedirect("");
				return;
			}
			if(!is.userOwnsStorage(Integer.parseInt(storageId), user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to view that report.");
				response.sendRedirect("count");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String fromDate = request.getParameter("from");
		String toDate = request.getParameter("to");
				

		try {
			if(storageId == null || storageId.isEmpty()){
				FlashMessage.setFlashMessage(request, "error", "You must select a storage first");
				response.sendRedirect("choose");
				return;
			}
			populateInput(request, response, user, Integer.parseInt(storageId));
			
			if(fromDate == null || fromDate.isEmpty() && toDate == null || toDate.isEmpty()){
				// default the dates
				// FROM
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, -1);
				Date f = new Date(cal.getTimeInMillis());
				fromDate = f.toString();
				
				// TO
				Date t = new Date(System.currentTimeMillis());
				toDate = t.toString();
			}
			
			Timestamp from = Timestamp.valueOf(fromDate + " 00:00:00");
			Timestamp to = Timestamp.valueOf(toDate + " 23:59:59");
			
			ArrayList<LogBook> al = is.getLogBooks(Integer.parseInt(storageId), from, to);
			request.setAttribute("logBooks", al);
			request.setAttribute("fromTime", fromDate);
			request.setAttribute("toTime", toDate);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		RequestDispatcher view = request.getRequestDispatcher("views/archive/period.jsp");
		view.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String storageId = request.getParameter("storageId");
		InventoryStatements is = new InventoryStatements();
		try {
			if(user == null || !auth.is("user", user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
				response.sendRedirect("");
				return;
			}
			if(!is.userOwnsStorage(Integer.parseInt(storageId), user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to view that report.");
				response.sendRedirect("count");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(storageId == null || storageId.isEmpty()){
			FlashMessage.setFlashMessage(request, "error", "No storage selected");
			response.sendRedirect("choose");
			return;
		}
		
		
		ArrayList<LoggedSummedStorage> al = new ArrayList<LoggedSummedStorage>();
		
		try {
			if(!is.userOwnsStorage(Integer.parseInt(storageId), user.getId())){
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to access this storage. The incident has been reported.");
				response.sendRedirect("choose");
				System.err.print("WARNING: User " + user.getId()+" "+user.getUsername() + " has tried to access storage " + storageId + " without permission");
				return;
			}
			
			if(storageId.equals("allStorages")){ // THIS IS NOT USED ANYMORE.. ONLY THE ELSE
				// get all storages
				try {
					ArrayList<Storage> storages = is.getStorages(user.getCompanyId());
					for (Storage storage : storages) {
						ArrayList<LoggedSummedStation> ls = getStationLogResults(request, response, user, Integer.toString(storage.getId()));
						if (ls.size() > 0){
							LoggedSummedStorage lStorage = new LoggedSummedStorage();
							lStorage.setStorage(storage);
							lStorage.setLoggedStation(ls);
							al.add(lStorage);
						}
					}				
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			else{
				ArrayList<LoggedSummedStation> ls = getStationLogResults(request, response, user, storageId);
				ArrayList<LoggedSummedInventory> li = getSummedLogResults(request, response, user, storageId);
				try {
					LoggedSummedStorage lStorage = new LoggedSummedStorage();
					lStorage.setStorage(is.getStorage(Integer.parseInt(storageId)));
					lStorage.setLoggedStation(ls);
					al.add(lStorage);		
					request.setAttribute("loggedInventory", li);
					
					} catch (Exception e) {
						// TODO: handle exception
					}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		request.setAttribute("loggedStations", al);
		doGet(request, response);
//		RequestDispatcher view = request.getRequestDispatcher("views/archive/period.jsp");
//		view.forward(request, response);
		
		
		
	
	}
	
	
	protected void populateInput(HttpServletRequest request, HttpServletResponse response, User user, int storageId){
		try {
			InventoryStatements is = new InventoryStatements();
//			ArrayList<Storage> storages = is.getStorages(user.getCompanyId());
			Storage storage = is.getStorage(storageId);
			ArrayList<Station> primaryStations = is.getStations(user.getCompanyId(), "primary");
			ArrayList<Station> secondaryStations = is.getStations(user.getCompanyId(), "secondary");
			ArrayList<Inventory> allInventory;
			allInventory = is.getAllInventory(user.getCompanyId());
			request.setAttribute("storage", storage);	
			request.setAttribute("allInventory", allInventory);
			request.setAttribute("primaryStations", primaryStations);
			request.setAttribute("secondaryStations", secondaryStations);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addMovesToInventory(LoggedSummedStation lss, Timestamp from, Timestamp to, int stationId, int storageId) throws Exception{
		ArrayList<LoggedSummedInventory> lsi = lss.getLoggedInventory();
		InventoryStatements is = new InventoryStatements();
		for (LoggedSummedInventory inventory : lsi) {
			inventory.setMoves(is.getListOfMoves(from, to, stationId, storageId, inventory.getName()));
		}
	}
	
	private void addMovesToInventory(ArrayList<LoggedSummedInventory> lsi, Timestamp from, Timestamp to, int storageId) throws Exception{
		InventoryStatements is = new InventoryStatements();
		for (LoggedSummedInventory inventory : lsi) {
			inventory.setMoves(is.getListOfMoves(from, to, storageId, inventory.getName()));
		}
	}
	
	
	public ArrayList<LoggedSummedStation> getStationLogResults(HttpServletRequest request, HttpServletResponse response, User user, String storageId) throws IOException{
		ArrayList<LoggedSummedStation> loggedStations = new ArrayList<>();
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String inventoryName = request.getParameter("inventoryName");
		String stationName = request.getParameter("stationName");
		
		// TODO TEST - REMOVE  
		String periods = request.getParameter("periods");
		
		if(periods == null || periods.isEmpty()){
			return loggedStations;
		}
		String[] periodsArr = periods.split("&");
		from = periodsArr[0];
		to = periodsArr[1];		
		// TEST
		if(from == null || from.isEmpty() ||
				to == null || to.isEmpty()){
			System.out.println("Returning null from getStationLogResult");
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
			stations.addAll(secondaryStations); // include secondary stations in stations list
			for (Station station : stations) {
				LoggedSummedStation ls;
						
				if (!inventoryName.equals("allInventory")){
					ls = is.getLoggedStation(fromDate, toDate, station.getId(), Integer.parseInt(storageId), inventoryName);
				}
				else{
					ls = is.getLoggedStation(fromDate, toDate, station.getId(), Integer.parseInt(storageId));
					addMovesToInventory(ls, fromDate, toDate, station.getId(), Integer.parseInt(storageId));
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
	
	public ArrayList<LoggedSummedInventory> getSummedLogResults(HttpServletRequest request, HttpServletResponse response, User user, String storageId){
		ArrayList<LoggedSummedInventory> loggedSummedInventory = new ArrayList<>();
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String periods = request.getParameter("periods");
		
		if(periods == null || periods.isEmpty()){
			return loggedSummedInventory;
		}
		
		String[] periodsArr = periods.split("&");
		from = periodsArr[0];
		to = periodsArr[1];
		if(from == null || from.isEmpty() ||
				to == null || to.isEmpty()){
			System.out.println("Returning null from getSummedLogResults");
			return loggedSummedInventory;
		}
		InventoryStatements is = new InventoryStatements();
		UserStatements us = new UserStatements();
		Timestamp fromDate = Timestamp.valueOf(from);
		Timestamp toDate = Timestamp.valueOf(to);
		
		try {
			
			loggedSummedInventory = is.getSummedLogResults(fromDate, toDate, Integer.parseInt(storageId));
			addMovesToInventory(loggedSummedInventory, fromDate, toDate, Integer.parseInt(storageId));

		} catch (Exception e) {
			// TODO: handle exception
		}
		return loggedSummedInventory;
	}
}
