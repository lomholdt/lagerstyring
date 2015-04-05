package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
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

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.lomholdt.lagerstyring.model.Authenticator;
import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.Inventory;
import com.lomholdt.lagerstyring.model.InventoryStatements;
import com.lomholdt.lagerstyring.model.LoggedStation;
import com.lomholdt.lagerstyring.model.LoggedStorage;
import com.lomholdt.lagerstyring.model.Station;
import com.lomholdt.lagerstyring.model.Storage;
import com.lomholdt.lagerstyring.model.User;
import com.lomholdt.lagerstyring.model.UserStatements;

/**
 * Servlet implementation class ArchiveController
 */
@WebServlet("/ArchiveController")
public class ArchiveController extends HttpServlet {
	Authenticator auth = new Authenticator();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArchiveController() {
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
		
		
		try {
			InventoryStatements is = new InventoryStatements();
			ArrayList<Storage> storages = is.getStorages(user.getCompanyId());
			ArrayList<Station> primaryStations = is.getStations(user.getCompanyId(), "primary");
			ArrayList<Station> secondaryStations = is.getStations(user.getCompanyId(), "secondary");
			ArrayList<Inventory> allInventory;
			allInventory = is.getAllInventory(user.getCompanyId());
			request.setAttribute("storages", storages);	
			request.setAttribute("allInventory", allInventory);
			request.setAttribute("primaryStations", primaryStations);
			request.setAttribute("secondaryStations", secondaryStations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RequestDispatcher view = request.getRequestDispatcher("views/storage/archive.jsp");
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
		
		String storageId = request.getParameter("storageId");
		InventoryStatements is = new InventoryStatements();
		ArrayList<LoggedStorage> al = new ArrayList<LoggedStorage>();
		
		if(storageId == null || storageId.isEmpty()){
			request.setAttribute("error", "No storage selected.");
			RequestDispatcher view = request.getRequestDispatcher("views/storage/archive.jsp");
			view.forward(request, response);
		}
		
		
		if(storageId.equals("allStorages")){
			// get all storages
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
		}
		else{
			ArrayList<LoggedStation> ls = getLogResults(request, response, user, storageId);
			if (ls.size() > 0){
				LoggedStorage lStorage = new LoggedStorage();
				lStorage.setStorage(is.getStorage(Integer.parseInt(storageId)));
				lStorage.setLoggedStation(ls);
				al.add(lStorage);
			}
		}
		request.setAttribute("logResults", al);
		RequestDispatcher view = request.getRequestDispatcher("views/storage/archive.jsp");
		view.forward(request, response);
		
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @param storageId
	 * @param importance
	 * @return
	 * @throws IOException
	 */
	public ArrayList<LoggedStation> getLogResults(HttpServletRequest request, HttpServletResponse response, User user, String storageId) throws IOException{
		ArrayList<LoggedStation> loggedStations = new ArrayList<LoggedStation>();
		String search = request.getParameter("search");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String inventoryName = request.getParameter("inventoryName");
		String stationName = request.getParameter("stationName");
		
		
		if(search == null || search.isEmpty() || 
				from == null || from.isEmpty() ||
				to == null || to.isEmpty()){
			System.out.println("Returning null");
			return loggedStations;
		}
				
		InventoryStatements is = new InventoryStatements();
		UserStatements us = new UserStatements();
		
		Timestamp fromDate = new Timestamp(java.sql.Date.valueOf(from).getTime());
		Timestamp toDate = new Timestamp(java.sql.Date.valueOf(to).getTime());
		
		//Storage s = is.getStorage(Integer.parseInt(storageId));
		fromDate.setHours(0);
		fromDate.setMinutes(0);
		fromDate.setSeconds(0);
		
		toDate.setDate(java.sql.Date.valueOf(to).getDay());
		toDate.setTime(Calendar.getInstance().getTimeInMillis());
		
		System.out.println(fromDate);
		System.out.println(toDate);
		
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
