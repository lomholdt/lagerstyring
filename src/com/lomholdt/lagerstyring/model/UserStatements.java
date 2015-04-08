package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserStatements extends DBMain {
	
	public int getCompanyId(int userId) throws Exception{
		Connection connection = c.getCon();
    	try {
    		pstmt = connection.prepareStatement("SELECT users.company_id FROM users WHERE users.id = ?");
    		pstmt.setInt(1, userId);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    				return rs.getInt("company_id");
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}
    	return 0;
    }
	
	public int getCompanyId(String companyName) throws Exception{
		Connection connection = c.getCon();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT companies.id FROM companies WHERE companies.name = ?");
			try {
				statement.setString(1, companyName);
				rs = statement.executeQuery();
				if (rs.next()) return rs.getInt("id");
			} finally {
				statement.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getUserId(String username, int companyId) throws Exception{
		Connection connection = c.getCon();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT users.id FROM users WHERE users.username = ? AND users.company_id = ?");
			try {
				statement.setString(1, username);
				statement.setInt(2, companyId);
				rs = statement.executeQuery();
				if (rs.next()) return rs.getInt("id");
			} finally {
				statement.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean userExists(String username, String userCompany) throws Exception{
		Connection connection = c.getCon();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT users.username, users.company_id FROM users WHERE users.username = ? AND users.company_id = (SELECT companies.id FROM companies WHERE companies.name = ?)");
			try {
				statement.setString(1, username);
				statement.setString(2, userCompany);
				rs = statement.executeQuery();
				if (rs.next()) return true;
			} finally {
				statement.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void addRoleToUser(String username, int companyId, String maxRole) throws Exception{
		int userId = getUserId(username, companyId);
		addRoleToUserHelper(userId, "user");
		if(maxRole.equals("manager")) addRoleToUserHelper(userId, "manager");
	}

	public void addRoleToUserHelper(int userId, String maxRole) throws Exception{
		Connection connection = c.getCon();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO roles (user_id, role) VALUES (?, ?)");
			try {
				statement.setInt(1, userId);
				statement.setString(2, maxRole);
				statement.executeUpdate();
			} finally {
				statement.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addUserToCompany(int companyId, String role, String username, String password) throws Exception{
		Connection connection = c.getCon();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password, company_id) VALUES (?, ?, ?)");
			try {
				statement.setString(1, username);
				statement.setString(2, Hash.hash256(password));
				statement.setInt(3, companyId);
				statement.executeUpdate();

			} finally {
				statement.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}


