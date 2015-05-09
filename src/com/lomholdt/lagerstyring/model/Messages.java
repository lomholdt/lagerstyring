package com.lomholdt.lagerstyring.model;

public abstract class Messages {
	
	/**
	 * ERRORS
	 */
	public static final String ERROR_NO_PERMISSION_TO_VIEW_PAGE = "Du har ikke de nødvendige tilladelser til at kunne se siden.";
	public static final String ERROR_LOGIN_TO_VIEW_PAGE = "Du skal være logget ind for at kunne se siden.";
	public static final String ERROR_NO_PERMISSION_TO_CLOSE_STORAGE = "Du har ikke tilladelse til at lukke lageret.";
	public static final String ERROR_NO_STORAGES_CREATED = "Du har ikke noget lager. Opret et først.";
	public static final String ERROR_NO_STORAGE_CHOSEN = "Du skal vælge et lager først, prøv igen.";
	public static final String ERROR_DID_NOT_FILL_OUT_ALL_FIELDS = "Der opstod en fejl. Har du udfyldt alle felterne korrekt?";
	public static final String ERROR_CANNOT_CLOSE_WITH_EMPTY_FIELDS = "Du kan ikke lukke med tomme felter.";
	

	/**
	 * OK MESSAGES
	 */
	public static final String OK_STORAGE_CLOSED_SUCCESSFULLY = "Du kan ikke lukke med tomme felter.";
	
	private Messages(){ } // Don't instantiate this class

}
