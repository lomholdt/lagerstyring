package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.lomholdt.lagerstyring.model.DBConnect;

public abstract class DBMain {
	
	
	protected DataSource ds;
    protected Connection conn;
    //protected Statement statement;
    static int count = 0;
	
	protected PreparedStatement statement;
	ResultSet rs;
//	DBConnect c;
	
	public DBMain() {
		
	  	  try {
	    		Context ctx = new InitialContext();
	    		ds = (DataSource)ctx.lookup("java:comp/env/jdbc/lagerstyring");
	    	  } catch (NamingException e) {
	    		e.printStackTrace();
	    	  }
	  	  
	  	try (final Connection poolConn = ds.getConnection()) {
	  	    final Connection conn = poolConn.unwrap(Connection.class);
	  	    
	  	    statement = conn.prepareStatement("select * from users");
	  	    rs = statement.executeQuery();
	  	    System.out.println(count++);
	  	    
	  	    
	  	    
	  	}
	  	catch(Exception e){
	  		
	  	}
	  	//poolConn is returned to the pool
    }
	
	
}
