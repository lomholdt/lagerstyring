package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class Authenticator extends DBMain {
	
	public boolean is(String role, int userId) throws SQLException{
		Connection connection = ds.getConnection();
		try{
			statement = connection.prepareStatement("SELECT role FROM roles WHERE roles.user_id = ?;");
			statement.setInt(1, userId);
			rs = statement.executeQuery();
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
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;
	}
}
