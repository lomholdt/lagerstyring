package com.lomholdt.lagerstyring.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lomholdt.lagerstyring.model.Authenticator;
import com.lomholdt.lagerstyring.model.FlashMessage;
import com.lomholdt.lagerstyring.model.InventoryStatements;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class SaveTemporaryUnitsController
 */
@WebServlet("/SaveTemporaryUnitsController")
public class SaveTemporaryUnitsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Authenticator auth = new Authenticator();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveTemporaryUnitsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String storageId = request.getParameter("sid");
		String isUpdate = request.getParameter("update");
		String save = request.getParameter("save");
		InventoryStatements is = new InventoryStatements();
		try {
			if(user == null || !auth.is("user", user.getId())){
				System.out.println("1");
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to see this page.");
				response.sendRedirect("");
				return;
			}
			if(storageId == null || storageId.isEmpty()) {
				System.out.println("2");
				FlashMessage.setFlashMessage(request, "error", "No storage was chosen, please try again.");
				response.sendRedirect("count");
			}
			if(!is.userOwnsStorage(Integer.parseInt(storageId), user.getId())){
				System.out.println("3");
				FlashMessage.setFlashMessage(request, "error", "You do not have permission to save to this storage.");
				response.sendRedirect("count");
				return;
			}
			if(isUpdate == null || isUpdate.isEmpty()){
				System.out.println("4");
				response.sendRedirect("count");
				return;
			}
			if(save == null || save.isEmpty()){
				System.out.println("5");
				response.sendRedirect("count");
				return;
			}
		} catch (Exception e) {
			System.out.println("Error on save");
			e.printStackTrace();
		}
		
		
		Map<String, String[]> m  = request.getParameterMap();
		Map<Integer, Double> tempValues = new HashMap<>();
		ArrayList<Integer> deleteTempValues = new ArrayList<>();
		for(Map.Entry<String, String[]> entry : m.entrySet()){
			if(entry.getKey().equals("sid") || entry.getKey().equals("update") || entry.getKey().equals("save")) continue;
			if(entry.getValue()[0] == null) continue;
			if(entry.getValue()[0].isEmpty()){
				// delete it from temp values
				deleteTempValues.add(Integer.parseInt(entry.getKey()));
			}
			else{
				tempValues.put(Integer.parseInt(entry.getKey()), Double.parseDouble(entry.getValue()[0]));				
			}
		}
		try {
			if(tempValues.size() > 0){ // if theres any input on save, save it
				is.setTempUnits(tempValues, Integer.parseInt(storageId));
			}
			if(deleteTempValues.size() > 0){
				// delete empty values if any
				is.deleteEmptyUnits(deleteTempValues);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
