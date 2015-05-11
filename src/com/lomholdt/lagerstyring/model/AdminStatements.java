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
			statement = connection.prepareStatement("INSERT INTO companies (name, is_active) VALUES (?, ?)");
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
	
	public boolean deleteCompany(int companyId) throws SQLException{
    	PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("DELETE FROM companies WHERE companies.id = ?");
			statement.setInt(1, companyId);
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
	
	public boolean companyExists(int companyId) throws SQLException {
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		Connection con = ds.getConnection();
		try {
			statement = con.prepareStatement("SELECT companies.name FROM companies WHERE companies.id = ?;");
			statement.setInt(1, companyId);
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
	
	public ArrayList<Company> getCompanies() throws SQLException{
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		ArrayList<Company> al = new ArrayList<>();
		Connection con = ds.getConnection();
		try {
			statement = con.prepareStatement("SELECT companies.id, companies.name, companies.created_at, companies.is_active FROM companies;");
			rs = statement.executeQuery();
			while (rs.next()){
				Company c = new Company();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setCreatedAt(rs.getTimestamp("created_at"));
				c.setActive(rs.getBoolean("is_active"));
				c.setCategories(getCompanyCategories(rs.getInt("id"))); // TODO TEST THIS!
				
				al.add(c);
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
	
	public ArrayList<String> getCompanyCategories(int companyId) throws SQLException{
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		ArrayList<String> al = new ArrayList<>();
		Connection con = ds.getConnection();
		try {
			statement = con.prepareStatement("SELECT categories.category FROM categories WHERE categories.company_id = ?");
			statement.setInt(1, companyId);
			rs = statement.executeQuery();
			while (rs.next()){
				al.add(rs.getString("category"));
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
	
	public ArrayList<User> getCompanyUsers(int companyId) throws SQLException{
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		ArrayList<User> al = new ArrayList<>();
		Connection con = ds.getConnection();
		try {
			statement = con.prepareStatement("SELECT users.id, users.company_id, users.username, users.created_at, companies.name AS company "
					+ "FROM users "
					+ "LEFT JOIN companies ON users.company_id = companies.id "
					+ "WHERE users.company_id = ?;");
			statement.setInt(1, companyId);
			rs = statement.executeQuery();
			while (rs.next()){
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setCompanyId(rs.getInt("company_id"));
				u.setCompanyName(rs.getString("company"));
				u.setMemberSince(rs.getTimestamp("created_at"));
				
				al.add(u);
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
	
	public void changeCompanyStatus(int companyId) throws Exception {
    	PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("UPDATE companies SET companies.is_active = !companies.is_active WHERE companies.id = ?");
			statement.setInt(1, companyId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}

	public boolean categoryExists(String categoryName, int companyId) throws SQLException {
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT categories.category "
					+ "FROM categories "
					+ "WHERE categories.company_id = ? "
					+ "AND categories.category = ?;");
			try {
				// Do stuff with the statement
				statement.setInt(1, companyId);
				statement.setString(2, categoryName);
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

	public void addNewCategory(String categoryCompany, int companyId) throws SQLException {
	   	PreparedStatement statement = null;
    	ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("INSERT INTO categories(company_id, category) VALUES(?, ?);");
			statement.setInt(1, companyId);
			statement.setString(2, categoryCompany);
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

	public void deleteCategory(int categoryId) throws SQLException {
    	PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("DELETE FROM categories WHERE categories.id = ?;");
			statement.setInt(1, categoryId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		
		
	}

}
