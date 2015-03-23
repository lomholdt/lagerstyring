package com.lomholdt.lagerstyring.model;

import java.sql.PreparedStatement;

public class Authenticator extends DBMain {
	
	public boolean is(String role, int userId){
		try{
			PreparedStatement pstmt = c.preparedStatement("SELECT role FROM roles WHERE roles.user_id = ?;");
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if (rs.getString("role").equals(role)) return true;
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}
}
