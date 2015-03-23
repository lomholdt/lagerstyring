package com.lomholdt.lagerstyring.model;

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
	
}


