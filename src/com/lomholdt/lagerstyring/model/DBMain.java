package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DBMain {
	
	
	protected DataSource ds;
    protected Connection conn;
	
	//protected PreparedStatement statement;
	//ResultSet rs;
	
	public DBMain() {
		
	  	  try {
	    		Context ctx = new InitialContext();
	    		ds = (DataSource)ctx.lookup("java:comp/env/jdbc/lagerstyring");
	    	  } catch (NamingException e) {
	    		e.printStackTrace();
	    	  }
	  	  
//	  	try (final Connection poolConn = ds.getConnection()) {
//	  	    final Connection conn = poolConn.unwrap(Connection.class);
//
//	  	}
	  	catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	//poolConn is returned to the pool
    }
	
	
}
