package com.lomholdt.lagerstyring.model;

public abstract class Messages {
	
	/**
	 * ERRORS
	 */
	public static final String ERROR_NO_PERMISSION_TO_VIEW_PAGE = "Du har ikke de nødvendige tilladelser til at kunne se siden.";
	public static final String ERROR_LOGIN_TO_VIEW_PAGE = "Du skal være logget ind for at kunne se siden.";
	
	private Messages(){ } // Don't instantiate this class

}
