package com.lomholdt.lagerstyring.model;
import javax.servlet.http.HttpSession; 
import javax.servlet.http.HttpServletRequest;

public class FlashMessage {
	
	public static void setFlashMessage(HttpServletRequest request, String parameter, Object message){
		HttpSession session = request.getSession();
		if(session.getAttribute(parameter) != null){
			return;
		}
		session.setAttribute(parameter, message);
	}
	
	public static void getFlashMessage(HttpServletRequest request, String parameter){
		HttpSession session = request.getSession();
		request.setAttribute(parameter, session.getAttribute(parameter));
		request.getSession().removeAttribute(parameter);
	}
}
