package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminStatements extends DBMain {
	
	public boolean addNewCompany(String companyName){
		try {
			Connection conn = ds.getConnection();
			PreparedStatement statement = conn.prepareStatement("INSERT INTO companies (name) VALUES (?)");
			statement.setString(1, companyName);
			statement.executeUpdate();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean companyExists(String companyName) {
		try {
			Connection con = ds.getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT companies.name FROM companies WHERE companies.name = ?;");
			statement.setString(1, companyName);
			rs = statement.executeQuery();
			if (rs.next()) return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String> getCompanies(){
		ArrayList<String> al = new ArrayList<String>();
		try {
			Connection con = ds.getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT companies.name FROM companies;");
			rs = statement.executeQuery();
			while (rs.next()){
				al.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}
	
	public boolean stationExists(String companyName, String stationName) throws Exception{
		Connection connection = ds.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT stations.name FROM stations, companies WHERE stations.company_id = companies.id AND companies.name = ? AND stations.name = ?");
			try {
				// Do stuff with the statement
				statement.setString(1, companyName);
				statement.setString(2, stationName);
				rs = statement.executeQuery();
				if (rs.next()){
					return true;					
				}
			} finally {
				statement.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean addStationToCompany(String companyName, String stationName, String importance) throws Exception{
		if(companyExists(companyName) && !stationExists(companyName, stationName)){
			int companyId = new UserStatements().getCompanyId(companyName);
			// add new station
			Connection connection = ds.getConnection();;
			try {
				PreparedStatement statement = connection.prepareStatement("INSERT INTO stations (name, company_id, importance) VALUES (?, ?, ?)");
				try {
					System.out.println("Adding station...");
					// Do stuff with the statement
					statement.setString(1, stationName);
					statement.setInt(2, companyId);
					statement.setString(3, importance);
					statement.executeUpdate();
					return true;
				}
				catch(Exception e){
					e.printStackTrace();
					
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally {
				try { if(null!=rs)rs.close();} catch (SQLException e) 
				{e.printStackTrace();}
				try { if(null!=statement)statement.close();} catch (SQLException e) 
				{e.printStackTrace();}
				try { if(null!=conn)conn.close();} catch (SQLException e) 
				{e.printStackTrace();}
			}
		}
		return false;
	}
	
	public boolean storageExists(String companyName, String storageName) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT storages.name, storages.company_id FROM storages, companies WHERE storages.name = ? AND companies.name = ? AND storages.company_id = companies.id");
			try {
				// Do stuff with the statement
				statement.setString(1, storageName);
				statement.setString(2, companyName);
				rs = statement.executeQuery();
				if (rs.next()){
					return true;					
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=conn)conn.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;
	}
	
	public void createNewStorage(String storageName, int companyId) throws Exception {
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO storages(name, company_id, is_open) VALUES(?, ?, 0)");
			statement.setString(1, storageName);
			statement.setInt(2, companyId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=conn)conn.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}

}
