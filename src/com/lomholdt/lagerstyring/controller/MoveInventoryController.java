package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import com.lomholdt.lagerstyring.model.Storage;
import com.lomholdt.lagerstyring.model.User;

/**
 * Servlet implementation class MoveInventoryController
 */
@WebServlet("/MoveInventoryController")
public class MoveInventoryController extends HttpServlet {
	Authenticator auth = new Authenticator();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoveInventoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		FlashMessage.getFlashMessage(request, "msg");
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
		
		// get storages for logged in user
		try {
			InventoryStatements is = new InventoryStatements();
			ArrayList<Storage> storages = is.getStorages(user.getCompanyId());
			
			request.setAttribute("storages", storages);
			RequestDispatcher view = request.getRequestDispatcher("views/inventory/move.jsp");
			view.forward(request, response);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
