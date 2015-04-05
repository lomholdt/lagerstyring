package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.Date;
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
import com.lomholdt.lagerstyring.model.Station;
import com.lomholdt.lagerstyring.model.Storage;
import com.lomholdt.lagerstyring.model.User;

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
		if(user == null || !auth.is("user", user.getId())){
			FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
			response.sendRedirect("");
			return;
		}
		
		
		InventoryStatements is = new InventoryStatements();
		String storageId = request.getParameter("storageId");
		String fromDate = request.getParameter("from");
		String toDate = request.getParameter("to");
				
		populateInput(request, response, user, Integer.parseInt(storageId));

		try {
			
			if(storageId == null || storageId.isEmpty()){
				//  if no storage specified, just pick the first one
//				storageId = Integer.toString(is.getFirstStorageId(user.getCompanyId()));
				FlashMessage.setFlashMessage(request, "error", "You must select a storage first");
				response.sendRedirect("choose");
				return;
			}
			
			if(fromDate == null || fromDate.isEmpty() && toDate == null || toDate.isEmpty()){
				System.out.println("Setting automatic date parameter");
				// default the dates
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, -1);
				Date f = new Date(cal.getTimeInMillis());
				fromDate = f.toString();
				
				Date t = new Date(System.currentTimeMillis());
				toDate = t.toString();
			}
			
			Timestamp from = Timestamp.valueOf(fromDate + " 00:00:00");
			Timestamp to = Timestamp.valueOf(toDate + " 23:59:59");
			
			System.out.println(from);
			System.out.println(to);
			
			ArrayList<LogBook> al = is.getLogBooks(Integer.parseInt(storageId), from, to);
			request.setAttribute("logBooks", al);			
			
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
}
