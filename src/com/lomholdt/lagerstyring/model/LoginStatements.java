package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.lomholdt.lagerstyring.model.User;

public class LoginStatements extends DBMain {
	    
    public boolean login(String username, String pwd, String companyName) throws SQLException {		
    	Connection connection = ds.getConnection();
    	try {
    		int companyId = new UserStatements().getCompanyId(companyName);
    		System.out.println("Got company Id: " + companyId);
    		pwd = Hash.hash256(pwd);
    		statement = connection.prepareStatement("SELECT users.username, users.password, companies.name AS company FROM users, companies WHERE users.username = ? AND users.company_id = ? AND users.company_id = companies.id");
    		statement.setString(1, username);
    		statement.setInt(2, companyId);
    		rs = statement.executeQuery();
    		if(rs.next()) {
    			if(rs.getString("password").equals(pwd)) {
    				return true;
    			}
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
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
    
    public Set<String> getUserRoles(int userId) throws Exception{
    	Connection connection = ds.getConnection();
		Set<String> s = new HashSet<String>();
    	try{
    		statement = connection.prepareStatement("SELECT roles.role FROM roles WHERE roles.user_id = ?");
    		statement.setInt(1, userId);
			rs = statement.executeQuery();
			while(rs.next()){
				s.add(rs.getString("role"));
			}
		} catch (Exception e){
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
		return s;
    }
    
	public User getUser(String username, String companyName) throws Exception{
		Connection connection = ds.getConnection();
		try{
			statement = connection.prepareStatement("SELECT users.id, users.username, users.company_id, companies.name AS companyName FROM users, companies WHERE users.username = ? AND users.company_id = companies.id AND companies.name = ?");
			statement.setString(1, username);
			statement.setString(2, companyName);
			rs = statement.executeQuery();
			if(rs.next()){
				User currentUser = new User();
				currentUser.setId(rs.getInt("id"));
				currentUser.setUsername(rs.getString("username"));
				currentUser.setCompanyId(rs.getInt("company_id"));
				currentUser.setCompanyName(rs.getString("companyName"));
				
				Set<String> roles = getUserRoles(rs.getInt("id"));
				currentUser.setRole(roles);
				return currentUser;
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return null;
	}
}
