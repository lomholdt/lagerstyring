package com.lomholdt.lagerstyring.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Set;

import com.lomholdt.lagerstyring.model.User;

public class LoginStatements extends DBMain {
	    
    public boolean login(String username, String pwd) {
    	try {
			pwd = hash256(pwd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	try {
    		pstmt = c.preparedStatement("SELECT username, password FROM users WHERE username =?;");
    		pstmt.setString(1, username);
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
    
	public static String hash256(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }
    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
    
    public Set<String> getUserRoles(int userId){
		Set<String> s = new HashSet<String>();
    	try{
			PreparedStatement pstmt = c.preparedStatement("SELECT roles.role FROM roles WHERE roles.user_id = ?");
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
    
	public User getUser(String email){
		try{
			PreparedStatement pstmt = c.preparedStatement("SELECT users.id, users.username, users.company_id, roles.role FROM users, roles WHERE users.username=? AND roles.user_id=users.id");
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
