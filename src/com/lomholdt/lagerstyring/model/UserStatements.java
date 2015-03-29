package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserStatements extends DBMain {
	
	public int getCompanyId(int userId){
    	try {
    		pstmt = c.preparedStatement("SELECT users.company_id FROM users WHERE users.id = ?");
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
				return 0;
			} finally {
				System.out.println("Closing statement");
				statement.close();
			}
		} finally {
			System.out.println("Closing connection");
//			connection.close();
		}
	}
	
	public int getUserId(String username) throws Exception{
		Connection connection = c.getCon();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT users.id FROM users WHERE users.username = ?");
			try {
				statement.setString(1, username);
				rs = statement.executeQuery();
				if (rs.next()) return rs.getInt("id");
				return 0;
			} finally {
				System.out.println("Closing statement");
				statement.close();
			}
		} finally {
			System.out.println("Closing connection");
//			connection.close();
		}
	}
	
	public boolean userExists(String username) throws Exception{
		Connection connection = c.getCon();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT users.username FROM users WHERE users.username = ?");
			try {
				statement.setString(1, username);
				rs = statement.executeQuery();
				if (rs.next()) return true;
				return false;
			} finally {
				System.out.println("Closing statement");
				statement.close();
			}
		} finally {
			System.out.println("Closing connection");
//			connection.close();
		}
	}
	
	public void addRoleToUser(String username, String maxRole) throws Exception{
		int userId = getUserId(username);
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
				System.out.println("Closing statement");
				statement.close();
			}
		} finally {
			System.out.println("Closing connection");
//			connection.close();
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
				System.out.println("Closing statement");
				statement.close();
			}
		} finally {
			System.out.println("Closing connection");
//			connection.close();
		}
	}
	
	
	
	
	
}


