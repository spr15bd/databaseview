/* GetData.java - All database access is performed through instances of this class (and the results, including exceptions
 * raised, returned to the main servlet). There are methods here to retrieve all the tables in a particular schema, 
 * retrieve all columns in a table, all keys in a table, and perform sql queries. There is also a method to validate
 * members' username and password details. 
 */

package dbView;

import java.util.ArrayList;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;
//import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import java.io.IOException;

public class GetData {
	String schema=null;
	String password=null;
	String dbHost=null;
	String dbName=null;
	String ERROR=null;
	String LOGIN=null;
	HttpServletRequest request=null;
	HttpServletResponse response=null;
	PageContext pageContext;
	RequestDispatcher rd=null;
	Connection conn=null;

	public GetData(HttpServletRequest request, HttpServletResponse response, String schema, String password, String dbHost, String dbName){
		LOGIN = response.encodeURL("login.jsp");
		ERROR = response.encodeURL("error");
		this.response=response;
		this.request=request;
		this.schema=schema;
		this.password=password;
		this.dbHost=dbHost;
		this.dbName=dbName;
		dbm("Finished constructor");
	}
	
	public ArrayList[] getTables() {
		conn=Connector.getConnection(schema, password, dbHost, dbName);
		if (conn!=null) {
			dbm("GetData.getTables: conn!=null!");
			ResultSet tableNames=null;
			DatabaseMetaData metaData=null;
			ArrayList []tables = new ArrayList[2];
			ArrayList<String> error=new ArrayList<String>();
			ArrayList<String> tableData=new ArrayList<String>();
			try {
				dbm("Inside GetData.getTables() try!");
				metaData=conn.getMetaData();
				System.out.println(metaData.getTableTypes());
				String [] names = {"TABLE","VIEW"};
				dbm("done the TABLE array.");
				
					
				//tableNames=metaData.getTables(null,schema.toUpperCase(),"%",names);
				tableNames=metaData.getTables(null,"%",null,names);
				dbm("New New getTables");
				dbm("Just done the resultset dec.");

				
				dbm("About to test the resultset");
				while (tableNames.next()) {		
					/* There are tables to display. */
					tableData.add(tableNames.getString("TABLE_NAME"));
					tableData.add(tableNames.getString("TABLE_TYPE"));
					String v=tableNames.getString("TABLE_SCHEM");
					dbm("Table_Schem value got is: "+v);
					if (v==null) v=schema;
					tableData.add(v);
				}
				tables[0]=tableData;
				dbm("Finished the resultset test.");
				
				dbm("GetData.getTables(): end of try block.");
			} catch (SQLException sql){
				error.add(sql.toString());
				
			} finally {
				dbm("Executing finally clause");
				try {
					tableNames.close();
					conn.close();
				} catch (SQLException sql) {
					error.add(sql.toString());
					tables[1]=error;
				}
				return tables;
			}
		} else {
			return null;
		}
	}

	public ArrayList[] getColumns(String table) {
		conn=Connector.getConnection(schema, password, dbHost, dbName);
		if (conn!=null) {
			dbm("GetData.getColumns("+table+"): conn!=null!");
			ResultSet columns=null;
			DatabaseMetaData metaData=null;
			ArrayList<String> columnTitles=new ArrayList<String>();
			ArrayList<String> columnTypes=new ArrayList<String>();
			ArrayList<String> error=new ArrayList<String>();
			ArrayList[]columnData=new ArrayList[3];
			try {
				dbm("Inside GetData.getColumns() try!");
				metaData=conn.getMetaData();
				columns=metaData.getColumns(null, "%", table, "%");
				dbm("Got columns resultSet.");
				
				while (columns.next()){
					columnTitles.add(columns.getString("COLUMN_NAME"));
					String dataType=columns.getString("TYPE_NAME");											if (dataType.equals("VARCHAR") || dataType.equals("VARCHAR2")){
						columnTypes.add("x");
					} else if (dataType.equals("DATE")){
						columnTypes.add("14-Feb-05");
					} else if (dataType.equals("NUMBER")){
						columnTypes.add("9");
					} else {
						columnTypes.add("x");
					}
					dbm("It contains some columns!.");
				}
				columnData[0]=columnTitles;
				columnData[1]=columnTypes;
			} catch (SQLException sql){
				error.add(sql.toString());
				
				
			} finally {
				try {
					conn.close();
					columns.close();
				} catch (SQLException sql) {
					error.add(sql.toString());
					columnData[2]=error;
				}
				return columnData;
			}
		} else {
			return null;
		}			
	}

	public ArrayList[] getKeys(String table){
		conn=Connector.getConnection(schema, password, dbHost, dbName);
		if (conn!=null) {
			dbm("GetData.getKeys("+table+"): conn!=null!");
			ResultSet primary=null;
			ResultSet foreign=null;
			DatabaseMetaData metaData=null;
			ArrayList<String> primaryKeys=new ArrayList<String>();
			ArrayList<String> foreignKeys=new ArrayList<String>();
			ArrayList[]keyData=new ArrayList[3];
			ArrayList<String> error=new ArrayList<String>();
			try{
				dbm("Inside GetData.getKeys() try.");
				metaData=conn.getMetaData();
				dbm("About to get prkeys resultSet.");
				primary=metaData.getPrimaryKeys(null, schema.toUpperCase(), table); 
				dbm("Got prkeys resultSet.");
				
				while (primary.next()){
					primaryKeys.add(primary.getString(4));
				}
				
				ArrayList []tables=this.getTables();
				for (int i=0; i<tables[0].size(); i=i+3){
					String tableName=(String)tables[0].get(i);
					String tableType=(String)tables[0].get(i+1);
					if (tableType.equals("TABLE")){
						foreign=metaData.getCrossReference(null, schema.toUpperCase(), table, null, schema.toUpperCase(), tableName);
						dbm("Got frkeys resultSet.");
						
						while (foreign.next()){
							dbm("It contains some keys!.");
							foreignKeys.add(foreign.getString("FKCOLUMN_NAME"));
							foreignKeys.add(foreign.getString("FKTABLE_NAME"));
						}
					}
				}
				dbm("About to return keyData.");
				keyData[0]=primaryKeys;
				keyData[1]=foreignKeys;
				
			} catch (SQLException sql){
				error.add(sql.toString());
				
			} finally {
				try {
					primary.close();
					foreign.close();
					conn.close();
				} catch (SQLException sql) {
					error.add(sql.toString());
					keyData[2]=error;
				}
				return keyData;
			}
		} else {
			return null;
		}					
	}

	public ArrayList[] getQuery(String query) {
		query=query.toLowerCase();
		dbm("Inside GetData.getQuery method.");
		dbm("Schema: "+schema);
		dbm("Password: "+password);
		conn=Connector.getConnection(schema, password, dbHost, dbName);
		if (conn!=null) {
			dbm("GetData.conn!=null.");
			ResultSetMetaData rsmd=null;
			ResultSet rs=null;
			Statement statement=null;
			ArrayList<String> columnNames=new ArrayList<String>();
			ArrayList<ArrayList> rowData=new ArrayList<ArrayList>();
			ArrayList<String> error=new ArrayList<String>();
			ArrayList[]queryResults=new ArrayList[3];
			
			int numberOfColumns = 0;
			try {
				dbm("Inside GetData.getQuery() try.");
				/* 	I added this line of code as some databases such as Oracle don't seem to like commands like rs.absolute & 
				 *   	rs.first in a forward-only resultset...*/
				statement=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				dbm("User's Query: "+query);
				if (query.indexOf("select")==0 || query.indexOf("desc")==0){
					dbm("select/desc query.");
					rs=statement.executeQuery(query);
					dbm("Executed query");
					if (rs.next()){
						dbm("There are rows in the resultset");
						rsmd = rs.getMetaData();
						dbm("Got meta data");
						numberOfColumns = rsmd.getColumnCount();
						dbm("No. of columns: "+numberOfColumns);
						for (int i=1; i<=numberOfColumns; i++) {
							dbm("There are column names.");
							columnNames.add(StringUtil.capitalise(rsmd.getColumnLabel(i)));
						}
						/* 	The resultset class doesn't seem to go back to the first row when 							 *	using rs.first, rs.previous etc. so I had to use brute force here!
						 */
						rs.first();
						rs.previous();
						while (rs.next()) {
							dbm("There are rows in the resultset.");
							ArrayList<String> columnData=new ArrayList<String>();
							dbm("Declared columnData resultset");
							for (int j=1; j<=numberOfColumns; j++) {
								columnData.add(rs.getString(j));
								dbm("added column "+j);
							}
							dbm("About to add columnData to rowData resultset");
							rowData.add(columnData);//
							dbm("added it successfully");
						}
						dbm("Finished adding resultset to the arraylists");
						queryResults[0]=columnNames;
						queryResults[1]=rowData;

						if (queryResults[0]!=null) {
							dbm("GetData.getQuery(): columnNames is not null.");
						}
						
					}
				} else {
					if (query.indexOf("update")==0 || query.indexOf("UPDATE")==0) {
						dbm("update query.");
						columnNames.add("update query");
						
						dbm("added flag to queryResults");
					} else if (query.indexOf("delete")==0 || query.indexOf("DELETE")==0) {
						columnNames.add("delete query");

					} else if (query.indexOf("insert")==0 || query.indexOf("INSERT")==0) {
						columnNames.add("insert query");

					} else if (query.indexOf("create table")==0 || query.indexOf("CREATE TABLE")==0) {
						columnNames.add("create query");


					} else if (query.indexOf("drop table")==0 || query.indexOf("DROP TABLE")==0) {
						columnNames.add("drop query");


					}else {
					
						columnNames.add("miscellaneous");
						
						dbm("miscellaneous query");
					}
					queryResults[0]=columnNames;
					dbm("Performing executeUpdate");
					int updated=statement.executeUpdate(query);
					dbm("Updated: "+updated);
				}
				dbm("Attempted to execute the query.");
				
				
				
			
			} catch (MySQLSyntaxErrorException m) {
				dbm(m.toString());
				error.add("You have an error in your SQL syntax.");
				queryResults[2]=error;
				dbm("added the mysql error to queryresults");
			} catch (SQLException sql) {
				dbm(sql.toString());
				error.add(sql.toString());
				queryResults[2]=error;
				dbm("added the sql error to queryresults");
			} finally {
				dbm("Executing finally clause");
				try{
					if (rs!=null) rs.close();
					if (conn!=null) conn.close();
				} catch (SQLException sql){
					error.add(sql.toString());
					queryResults[2]=error;
					//queryResults[2].add(sql);
				}
				dbm("About to return query results.");
				if (queryResults==null) dbm("QueryResults is null");
				return queryResults;
				
			}
		} else {
			String message="Sorry, no connection was established in order to carry out this query.";
			displayMessage(message, null, ERROR);
			dbm("About to return default value for ArrayList.");
			return new ArrayList[3];
		}		
	}

	public boolean isMember(String username, String password, String userStatement){
		dbm("Finding out if user is a member");
		conn=Connector.getConnection("adminf33srsY", "m_YDk9y5fAbi", "jdbc:mysql://127.2.210.2:3306", "databaseview");
		if (conn!=null) {
			dbm("Established a connection with username & password");
			PreparedStatement statement=null;
			ResultSet rs=null;
			try {
				dbm("Preparing statement");
				statement=conn.prepareStatement(userStatement);
				statement.setString(1, username);
				statement.setString(2, password);
				rs=statement.executeQuery();
				if (rs.next()) {		// If the username and password are in the same row.....
					// They are a member! 
					dbm("This person is a member");
					return true;
				} else {
					dbm("This person is not a member");
					return false;
				}
			} catch (SQLException sql){
				dbm("SQL exception");
				String message="An error occurred whilst trying to establish whether user is a member.";
				displayMessage(message, sql, ERROR);
				return false;
			} finally {
				try {
					if (rs != null){
						rs.close();
					}
					statement.close();
					dbm("closed statement");
					conn.close();
					dbm("closed connection");
				} catch (SQLException sql) {
					dbm("SQL exception");
					String message="Sorry, an error has occurred whilst trying to close the connection to the database.";
					displayMessage(message, sql, ERROR);
					return false;
				}
			}
		} else {	
			return false;
		}
	}

	private void displayMessage(String message, Exception exception, String url) {
		dbm("Inside displayMessage.");
		request.setAttribute("message", message);
		if (exception!=null){
			request.setAttribute("javax.servlet.jsp.jspException", exception);
			dbm("exception!=null.");
		}	
		RequestDispatcher rd=request.getRequestDispatcher(url);
		dbm("sending user to "+url);
		try{
			rd.forward(request, response);
		} catch (ServletException se){
			dbm("Servlet Exception: "+se.toString());
		} catch (IOException ioe){
			dbm("IO Exception: "+ioe.toString());
		}
	}

	private static void dbm(String msg) {
		System.out.println("GetData: "+msg);
	}
}