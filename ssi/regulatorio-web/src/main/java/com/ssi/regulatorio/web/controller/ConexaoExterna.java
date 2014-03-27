package com.ssi.regulatorio.web.controller;

import java.sql.Connection;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
public class ConexaoExterna{
     private static Connection  con = null;
     private static String url = "jdbc:microsoft:sqlserver://";
     private static String serverName= "10.126.111.216";
     private static String databaseName = "SSI_SCIENCE_DESENV";
     private static String userName = "pc_ssi";
     private static String password = "ssi321"; 
//     private static String userName = "kpi_rh";
//     private static String password = "kpi!@#";
     
     public static Connection getConnection(){
    	 try{
    		 
    		// Establish the connection. 
 			SQLServerDataSource ds = new SQLServerDataSource();
 			ds.setServerName(serverName);
 			ds.setPortNumber(1433); 
 			ds.setDatabaseName(databaseName);
 			ds.setUser(userName);
 			ds.setPassword(password);
 			
 			con = ds.getConnection();
 			
    		}catch(Exception ex){
    		  ex.printStackTrace();
    		}
    	 
    	 return con;
      }
     
     public static void closeConnection( Connection con ){
          try{
               if(con!=null)
                    con.close();
               con=null;
          }catch(Exception e){
               e.printStackTrace();
          }
     }

	public static String getDatabaseName() {
		return databaseName;
	}

	public static void setDatabaseName(String databaseName) {
		ConexaoExterna.databaseName = databaseName;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		ConexaoExterna.url = url;
	}

	public static String getServerName() {
		return serverName;
	}

	public static void setServerName(String serverName) {
		ConexaoExterna.serverName = serverName;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		ConexaoExterna.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ConexaoExterna.password = password;
	}
}
