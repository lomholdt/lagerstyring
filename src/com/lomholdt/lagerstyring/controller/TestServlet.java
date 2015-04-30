package com.lomholdt.lagerstyring.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
 
public class TestServlet extends HttpServlet {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataSource ds;
    private Connection conn;
    private Statement statement;
     
    public void init() throws ServletException {
  	  try {
  		Context ctx = new InitialContext();
  		ds = (DataSource)ctx.lookup("java:comp/env/jdbc/lagerstyring");
  	  } catch (NamingException e) {
  		e.printStackTrace();
  	  }
    }
 
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
         
    	ResultSet resultSet = null;
        try {
        	Connection conn = ds.getConnection();
            // Get Connection and Statement
            
            statement = conn.createStatement();
            String query = "SELECT * FROM users";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + resultSet.getString(2) + resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try { if(null!=resultSet)resultSet.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=conn)conn.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
    }
}