package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminStatements extends DBMain {
	
	public boolean addNewCompany(String companyName) throws SQLException{
    	PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = conn.prepareStatement("INSERT INTO companies (name, is_active) VALUES (?, ?)");
			statement.setString(1, companyName);
			statement.setBoolean(2, true);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(null!=statement)statement.close();} catch (SQLException e) 
			{e.printStackTrace();}
			try { if(null!=connection)connection.close();} catch (SQLException e) 
			{e.printStackTrace();}
		}
		return false;
	}

	public boolean companyExists(String companyName) throws SQLException {
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		Connection con = ds.getConnection();
		try {
			statement = con.prepareStatement("SELECT companies.name FROM companies WHERE companies.name = ?;");
			statement.setString(1, companyName);
			rs = statement.executeQuery();
			if (rs.next()) return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(null!=rs)rs.close();} catch (SQLException e) 
			{e.printStackTrace();}
			try { if(null!=statement)statement.close();} catch (SQLException e) 
			{e.printStackTrace();}
			try { if(null!=con)con.close();} catch (SQLException e) 
			{e.printStackTrace();}
		}
		return false;
	}
	
	public ArrayList<String> getCompanies() throws SQLException{
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		ArrayList<String> al = new ArrayList<String>();
		Connection con = ds.getConnection();
		try {
			statement = con.prepareStatement("SELECT companies.name FROM companies;");
			rs = statement.executeQuery();
			while (rs.next()){
				al.add(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try { if(null!=rs)rs.close();} catch (SQLException e) 
			{e.printStackTrace();}
			try { if(null!=statement)statement.close();} catch (SQLException e) 
			{e.printStackTrace();}
			try { if(null!=con)con.close();} catch (SQLException e) 
			{e.printStackTrace();}
		}
		return al;
	}
	
	public boolean stationExists(String companyName, String stationName) throws Exception{
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT stations.name FROM stations, companies WHERE stations.company_id = companies.id AND companies.name = ? AND stations.name = ?");
			try {
				// Do stuff with the statement
				statement.setString(1, companyName);
				statement.setString(2, stationName);
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
			try { if(null!=connection)connection.close();} catch (SQLException e) 
			{e.printStackTrace();}
		}
		return false;
		
	}
	
	public boolean addStationToCompany(String companyName, String stationName, String importance) throws Exception{
		if(companyExists(companyName) && !stationExists(companyName, stationName)){
			int companyId = new UserStatements().getCompanyId(companyName);
			// add new station
	    	PreparedStatement statement = null;
			Connection connection = ds.getConnection();
			try {
				statement = connection.prepareStatement("INSERT INTO stations (name, company_id, importance) VALUES (?, ?, ?)");
				try {
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
				try { if(null!=statement)statement.close();} catch (SQLException e) 
				{e.printStackTrace();}
				try { if(null!=connection)connection.close();} catch (SQLException e) 
				{e.printStackTrace();}
			}
		}
		return false;
	}
	
	public boolean storageExists(String companyName, String storageName) throws Exception{
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT storages.name, storages.company_id FROM storages, companies WHERE storages.name = ? AND companies.name = ? AND storages.company_id = companies.id");
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
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;
	}
	
	public void createNewStorage(String storageName, int companyId) throws Exception {
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("INSERT INTO storages(name, company_id, is_open) VALUES(?, ?, 0)");
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
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}

}
