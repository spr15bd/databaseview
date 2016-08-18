/* Connector.java - class to create database connections as needed by the application*/

package dbView;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Connector {
	
	public static Connection getConnection(String schema, String password, String dbHost, String dbName) {
		Connection con=null;
		String driver="com.mysql.jdbc.Driver";
		String dbURL=dbHost+"/"+dbName;
		String username=schema;
		System.out.println("Connector: "+"dbURL is "+dbURL);
		System.out.println("Connector: "+"password is "+password);
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(dbURL, username, password);
			
		} catch (ClassNotFoundException cnf) {
			System.out.println("Class Not found exception raised");
			cnf.printStackTrace();
			return null;
		} catch (SQLException sql) {
			sql.printStackTrace();
			System.out.println("Sql exception raised in Connector");
			System.out.println("Failed to connect using "+schema+" and "+password);
			return null;
		}
		return con;
	}
}