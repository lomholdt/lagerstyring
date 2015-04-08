package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Set;

import com.lomholdt.lagerstyring.model.User;

public class LoginStatements extends DBMain {
	    
    public boolean login(String username, String pwd, String companyName) {
		
    	try {
    		Connection connection = c.getCon();
    		pwd = Hash.hash256(pwd);
    		pstmt = connection.prepareStatement("SELECT users.username, users.password, companies.name AS company FROM users, companies WHERE username = ? AND companies.name = ?;");
    		pstmt.setString(1, username);
    		pstmt.setString(2, companyName);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			if(rs.getString("password").equals(pwd)) {
    				return true;
    			}
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}
    	return false;
    }
    
    public Set<String> getUserRoles(int userId) throws Exception{
    	Connection connection = c.getCon();
		Set<String> s = new HashSet<String>();
    	try{
			PreparedStatement pstmt = connection.prepareStatement("SELECT roles.role FROM roles WHERE roles.user_id = ?");
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				s.add(rs.getString("role"));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return s;
    }
    
	public User getUser(String email) throws Exception{
		Connection connection = c.getCon();
		try{
			PreparedStatement pstmt = connection.prepareStatement("SELECT users.id, users.username, users.company_id, roles.role FROM users, roles WHERE users.username=? AND roles.user_id=users.id");
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if(rs.next()){
				User currentUser = new User();
				currentUser.setId(rs.getInt("id"));
				currentUser.setUsername(rs.getString("username"));
				currentUser.setCompanyId(rs.getInt("company_id"));
				
				Set<String> roles = getUserRoles(rs.getInt("id"));
				currentUser.setRole(roles);
				return currentUser;
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
}
