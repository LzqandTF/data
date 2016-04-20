package cn.com.chinaebi.dz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class ConnectionUtil {
	 
      private static Connection conn = null; 
      
      private static ResourceBundle res = ResourceBundle.getBundle("duiz_jdbc");
      
	  private static final String url = res.getString("jdbc.url");
	  private static String driverClassName = res.getString("jdbc.driverClassName"); 
	  private static String user = res.getString("jdbc.username"); 
	  private static String pass = res.getString("jdbc.password");
	  
	  public static Connection getConnecttion() throws Exception{
			Class.forName(driverClassName).newInstance(); 
			conn = DriverManager.getConnection(url, user, pass);
			return conn;
	  }
	  
	  public static void closeConnection(Connection conn) throws Exception{
		  if(conn != null){
			  conn.close();
		  }
	  }
}
