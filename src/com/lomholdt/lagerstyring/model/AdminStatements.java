package com.lomholdt.lagerstyring.model;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class AdminStatements extends DBMain {
	
	public boolean addNewCompany(String companyName){
		try {
			PreparedStatement pstmt = c.preparedStatement("INSERT INTO companies (name) VALUES (?)");
			pstmt.setString(1, companyName);
			pstmt.executeUpdate();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean companyExists(String companyName) {
		try {
			PreparedStatement pstmt = c.preparedStatement("SELECT companies.name FROM companies WHERE companies.name = ?;");
			pstmt.setString(1, companyName);
			rs = pstmt.executeQuery();
			if (rs.next()) return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String> getCompanies(){
		ArrayList<String> al = new ArrayList<String>();
		try {
			PreparedStatement pstmt = c.preparedStatement("SELECT companies.name FROM companies;");
			rs = pstmt.executeQuery();
			while (rs.next()){
				al.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

}
