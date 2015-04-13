package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class Authenticator extends DBMain {
	
	public boolean is(String role, int userId){
		try{
			Connection con = ds.getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT role FROM roles WHERE roles.user_id = ?;");
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if (rs.getString("role").equals(role)) return true;
			}
		}catch(Exception e){
			System.out.println(e);
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
}
