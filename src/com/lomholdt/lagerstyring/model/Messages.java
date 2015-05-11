package com.lomholdt.lagerstyring.model;

public abstract class Messages {
	
	/**
	 * ERRORS
	 */
	public static final String ERROR_NO_PERMISSION_TO_VIEW_PAGE = "Du har ikke de nødvendige tilladelser til at kunne se siden.";
	public static final String ERROR_LOGIN_TO_VIEW_PAGE = "Du skal være logget ind for at kunne se siden.";
	public static final String ERROR_NO_PERMISSION_TO_CLOSE_STORAGE = "Du har ikke tilladelse til at lukke lageret.";
	public static final String ERROR_NO_PERMISSION_TO_OPEN_STORAGE = "Du har ikke tilladelse til at åbne lageret.";
	public static final String ERROR_NO_STORAGES_CREATED = "Du har ikke noget lager. Opret et først.";
	public static final String ERROR_NO_STORAGE_CHOSEN = "Du skal vælge et lager først, prøv igen.";
	public static final String ERROR_DID_NOT_FILL_OUT_ALL_FIELDS = "Der opstod en fejl. Har du udfyldt alle felterne korrekt?";
	public static final String ERROR_CANNOT_CLOSE_WITH_EMPTY_FIELDS = "Du kan ikke lukke med tomme felter.";
	public static final String ERROR_SOMETHING_WENT_WRONG = "Der opstod en fejl.";
	public static final String ERROR_CATEGORY_ALREADY_EXISTS = "Kategorien findes allerede.";
	public static final String ERROR_STORAGE_IS_NOT_OPEN = "Lageret er ikke åben.";
	public static final String ERROR_STORAGE_IS_NOT_CLOSED = "Lageret er ikke lukket.";
	public static final String ERROR_OCCURED_WHILE_DELETING = "Der opstod en fejl ved sletning af varer.";
	
	

	/**
	 * OK MESSAGES
	 */
	public static final String OK_STORAGE_CLOSED_SUCCESSFULLY = "Lageret er nu lukket.";
	public static final String OK_STORAGE_OPENED_SUCCESSFULLY = "Lageret er nu åbent.";
	
	public static final String OK_CATEGORY_ADDED_TO_COMPANY = "Kategorien %s blev tilføjet til %s";
	public static final String OK_INVENTORY_DELETED = "Følgende varer blev fjernet <br> %s";
	
	private Messages(){ } // Don't instantiate this class

	public static String addNewCategorySuccess(String categoryCompany, String companyName) {
		return String.format(OK_CATEGORY_ADDED_TO_COMPANY, categoryCompany, companyName);
	}

	public static String deletedInventory(String deleteMsg) {
		return String.format(OK_INVENTORY_DELETED, deleteMsg);
	}

}
