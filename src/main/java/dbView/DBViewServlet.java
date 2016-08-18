/* 	DBToolsServlet.java - This is the application's main servlet. 
/*	If the request parameter 'action' equals 'login', the servlet will attempt to log the user on to the application. 
/*	If 'action' equals 'displayTables', the servlet will retrieve the current user's schema information and forward this info to 'tables&views.jsp'. 
/*	If 'action' equals 'preparedQuery', this means the user has opted to choose one of the automatic queries from the vertical menu ('select', 'update', etc). The servlet will formulate the query and forward it to 'sqlquery.jsp' where it will be displayed in the query window. 
/*	If 'action' equals 'executeQuery' the servlet will carry out the query that the user has typed in the query window.
/*	
/*	Whenever the servlet requires data from a database account, it calls the GetData class, which deals exclusively with all the database connections and exceptions. These calls use an instance of GetData, and will return any database exceptions raised as well as query results, in the form of arrayLists. Exceptions returned by GetData are sent by the servlet to either a standard error page (error.jsp), or one of the other jsps, depending on their nature.
/*
/*	Further down the page there are also 'helper methods for the above, which deal with retrieving user account details (username, password, dbusername, password, and whether they have checked logon preference boxes), standard queries used when the user attempts to log on or update password details, and setting queries by clicking one menu item (select, update, etc).
/*
/*	Lastly, displayMessage forwards messages and exceptions to the various jsps, including error.jsp.
 */



package dbView;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.lang.Exception;
import java.io.IOException;
import java.util.Enumeration;
import java.util.ArrayList;

			
@WebServlet(name = "Simple Servlet", description = "This is a simple servlet with annotations", urlPatterns = "/getServlet") 
public class DBViewServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		dbm("inside DBViewServlet doPost()");

		String LOGIN = response.encodeURL("login.jsp");
		String DBLOGIN = response.encodeURL("dblogin.jsp");
		String QUERYLOG = response.encodeURL("querylog.jsp");
		String QUERYRESULTS = response.encodeURL("queryresults.jsp");
		String SQLQUERY = response.encodeURL("sqlquery.jsp");
		String TABLESANDVIEWS = response.encodeURL("tables&views.jsp");
	 	String ERROR = response.encodeURL("error.jsp");
		String message=null;
		GetData connect=null;
		Exception error=null;
		HttpSession session=request.getSession(false);
		boolean validSession=false;
		String action=(String)request.getParameter("action");
		dbm("Action: "+action);
			
		
		if (action.equals("login")){
			dbm("inside action=login");
			dbm("About to enter userDetails()");
			String[]userDetails=getUserDetails(request, response, action);
			if (userDetails[0]!=null && userDetails[0].trim().length()>0 && userDetails[1]!=null && userDetails[1].trim().length()>0) {
<<<<<<< HEAD
				connect=new GetData(request, response, "u£!sR561lE7", "!jT5sE69LkD", "jdbc:mysql://127.8.226.2:3306", "databaseview");
				//connect=new GetData(request, response, "adminf33srsY", "m_YDk9y5fAbi", "jdbc:mysql://127.2.210.2:3306", "databaseview");
=======
				//connect=new GetData(request, response, "adminxUaHXqi", "fcPCWIKuWsHr", "jdbc:mysql://127.8.226.2:3306", "webstore");
				//connect=new GetData(request, response, "adminxUaHXqi", "fcPCWIKuWsHr", "jdbc:mysql://127.8.226.2:3306", "webstore");
				connect=new GetData(request, response, "adminxUaHXqi", "fcPCWIKuWsHr", "jdbc:mysql://127.2.210.1:8080", "webstore");
				//connect=new GetData(request, response, "adminxUaHXqi", "fcPCWIKuWsHr", "jdbc:mysql://"+System.getenv("OPENSHIFT_<MYSQL>_DB_HOST")+":"+System.getenv("OPENSHIFT_<MYSQL>_DB_HOST"), System.getenv("OPENSHIFT_<MYSQL>_DB_PORT"));
				

>>>>>>> parent of 2f9397e... Amended servlet
				dbm("UserDetails0:"+userDetails[0]);
				dbm("UserDetails1:"+userDetails[1]);
				dbm("UserDetails2:"+userDetails[2]);
				
				if (connect.isMember(userDetails[0], userDetails[1], userStatement())){
					dbm("This user is a member");
					// Invalidate any existing session and set up a new one.....
					if (session!=null) {
						session.invalidate();
					}	
					session=request.getSession(true);
					session.setMaxInactiveInterval(5*60);
						
					// Add all the user's account details to session attributes.
		
					session.setAttribute("username", userDetails[0]);
					session.setAttribute("password", userDetails[1]);
					
					if (userDetails[2]==null){	
						RequestDispatcher rd=request.getRequestDispatcher(DBLOGIN);
						rd.forward(request, response);
					} else {
						// Bypass the Database Login Page as the user has ticked the preferences box.
						dbm("Bypassing the Database Login Page");
						ArrayList[]preferences=null;
		
						preferences=connect.getQuery(userPreferencesStatement(userDetails[0]));
						if (preferences[2]!=null){
							error=(Exception)preferences[2].get(0);
							if (error!=null){	
								dbm("Exception "+error);
								message="Sorry, an error occurred whilst trying to use your preferred database login details.";
								displayMessage(request, response, message, error, ERROR);
							}
						}
						ArrayList columnNames=null;
						ArrayList rowData=null;
						ArrayList columnData=null;
						if (preferences[0]!=null) columnNames=preferences[0];
						if (preferences[1]!=null) rowData=preferences[1];
						if (rowData==null || columnNames==null){
							dbm("No resultset was returned by dbt_preferences table.");
							message="You must set up a default database account before you  can connect to it - this can be done on the database login page.";
							displayMessage(request, response, message, null, LOGIN);
							return;
						}
						columnData=(ArrayList)rowData.get(0);
						for (int i=0; i<columnNames.size(); i++) {
							if (columnNames.get(i).equals("Db_username")){
								userDetails[1]=(String)columnData.get(i);
								dbm("userDetails[1]: "+userDetails[1]);
							} else if (columnNames.get(i).equals("Db_password")){
								userDetails[2]=(String)columnData.get(i);
								dbm("userDetails[2]: "+userDetails[2]);
							} else if (columnNames.get(i).equals("Db_host")){
								userDetails[4]=(String)columnData.get(i);
								dbm("userDetails[4]: "+userDetails[4]);
							} else if (columnNames.get(i).equals("Db_name")){
								userDetails[5]=(String)columnData.get(i);
								dbm("userDetails[5]: "+userDetails[5]);
							} 
						}
					
						ArrayList []tables=null;
						connect=new GetData(request, response, userDetails[1], userDetails[2], userDetails[4], userDetails[5]);
						tables=connect.getTables();
						
						if (tables[0]==null){		/*(No connection).*/
							dbm("Connection returned null");
							message="A database error occurred whilst attempting to retrieve your tables.";
							displayMessage(request, response, message, null, LOGIN);
						} else if (tables[1]!=null){	/*(Exception was raised).*/	
							error=(Exception)tables[1].get(0);
							if (error!=null){
								message="Sorry, a database error occurred whilst trying to enter your schema.";
								displayMessage(request, response, message, error, ERROR);
								return;
							}
						} else { 			/*(Successfully returned the tables).*/
							dbm("Setting session attributes");
							request.setAttribute("schemaData", tables[0]);
							if (request.getAttribute("schemaData")!=null) dbm("Not Null in DBToolsServlet....Schema is "+request.getAttribute("schemaData"));
							session.setAttribute("dbusername", userDetails[1]);
							session.setAttribute("password", userDetails[2]);
							session.setAttribute("dbhost", userDetails[4]);
							session.setAttribute("dbname", userDetails[5]);
							RequestDispatcher rd=request.getRequestDispatcher(TABLESANDVIEWS);
							rd.forward(request, response);
							return;
						}
					}
					
				} else {
					message="Please enter a valid user account.";
					displayMessage(request, response, message, null, LOGIN);
				}
				
			} else {
				if (userDetails[0]==null || userDetails[0].trim().length()<1) {
					dbm("username is null.");
					message="You must enter a valid username.";
					displayMessage(request, response, message, null, LOGIN);
				} else if (userDetails[1]==null || userDetails[1].trim().length()<1) {
					dbm("password is null.");
					message="You must enter a valid password.";
					displayMessage(request, response, message, null, LOGIN);
				}
			}
		}
		


		else if (action.equals("displayTables")){
			dbm("inside action=dblogin");
			if (!validSession(request, response, session, LOGIN)){
				return;
			}
			
			String[]userDetails=getUserDetails(request, response, action);
			dbm("Successfully got user details.");
			if (userDetails[1]!=null && userDetails[1].trim().length()>0 && userDetails[2]!=null && userDetails[2].trim().length()>0 && userDetails[4]!=null && userDetails[4].trim().length()>0 && userDetails[5]!=null && userDetails[5].trim().length()>0) {
				dbm("UserDetails0:"+userDetails[0]);
				dbm("UserDetails1:"+userDetails[1]);
				dbm("UserDetails2:"+userDetails[2]);
				dbm("UserDetails3:"+userDetails[3]);
				dbm("UserDetails4:"+userDetails[4]);
				dbm("UserDetails5:"+userDetails[5]);
				connect=new GetData(request, response, userDetails[1], userDetails[2], userDetails[4], userDetails[5]); /* (User's schema, password, and the database host and database name).*/
				ArrayList []tables=null;
				
				tables=connect.getTables();
			
				if (tables==null){		/*(No connection was made).*/
					message="ERROR: Please make sure all the fields are filled correctly";
					displayMessage(request, response, message, null, DBLOGIN);
					return;
				} else if (tables[1]!=null){	/*(Exception was raised).*/
					error=(Exception)tables[1].get(0);
					if (error!=null){
					
						message="ERROR: a database error occurred whilst trying to enter your schema.";
						displayMessage(request, response, message, error, ERROR);
						return;
					}
				} else {			/*(Successfully retrieved tables).*/
					
					request.setAttribute("schemaData", tables[0]);
					if (request.getAttribute("schemaData")!=null) dbm("Not Null in DBToolsServlet....");
					session.setAttribute("dbusername", userDetails[1]);
					session.setAttribute("password", userDetails[2]);
					session.setAttribute("dbhost", userDetails[4]);
					session.setAttribute("dbname", userDetails[5]);
						
					/* If the user elects to tick the 'store database preferences' box on the db login page, then it's imperative that they update the dbt_preferences table in my account rather than another one! So, instantiate a brand new GetData object with the correct username and password. */ 
					if (userDetails[3]!=null){
						dbm("User has elected to update db logon settings");
						ArrayList[]preferences=null;
						//connect=new GetData(request, response, "adminf33srsY", "m_YDk9y5fAbi", "jdbc:mysql://127.2.210.2:3306", "databaseview");
						connect=new GetData(request, response, "u£!sR561lE7", "!jT5sE69LkD", "jdbc:mysql://127.2.210.2:3306", "databaseview");
						preferences=connect.getQuery(deleteOldUserPreferencesStatement(userDetails[0]));
						preferences=connect.getQuery(addUserPreferencesStatement(userDetails[0], userDetails[1], userDetails[2], userDetails[4], userDetails[5]));
						if (preferences[2]!=null){
							error=(Exception)preferences[2].get(0);
							if (error!=null){
					
								message="Sorry, an error occurred whilst trying to update your preferred database login details.";
								displayMessage(request, response, message, error, ERROR);
								return;
							}
						}
					}	
					
					RequestDispatcher rd=request.getRequestDispatcher(TABLESANDVIEWS);
					rd.forward(request, response);
				}
			} else {
				if (userDetails[1]==null || userDetails[1].trim().length()<1) {
					dbm("username is null.");
					message="You must enter a valid db username.";
					displayMessage(request, response, message, null, DBLOGIN);
				} else if (userDetails[2]==null || userDetails[2].trim().length()<1) {
					dbm("password is null.");
					message="You must enter a valid db password.";
					displayMessage(request, response, message, null, DBLOGIN);
				} else if (userDetails[4]==null || userDetails[4].trim().length()<1) {
					dbm("db host is null.");
					message="You must enter a valid db host.";
					displayMessage(request, response, message, null, DBLOGIN);
				} else if (userDetails[5]==null || userDetails[5].trim().length()<1) {
					dbm("db name is null.");
					message="You must enter a valid database name.";
					displayMessage(request, response, message, null, DBLOGIN);
				}
			}
		
		} else if (action.equals("preparedQuery")){
			dbm("action equals preparedQuery");
			if (!validSession(request, response, session, LOGIN)){
				return;
			}
			String[]userDetails=getUserDetails(request, response, action);
			dbm("UserDetails0:"+userDetails[0]);
			dbm("UserDetails1:"+userDetails[1]);
			dbm("UserDetails2:"+userDetails[2]);
			dbm("UserDetails3:"+userDetails[3]);
			dbm("UserDetails4:"+userDetails[4]);
			dbm("UserDetails5:"+userDetails[5]);
			String queryType=(String)request.getParameter("queryType");
			String tableChoice=(String)request.getParameter("tableChoice");
			if (tableChoice!=null){
				dbm("TableChoice!=null");
				connect=new GetData(request, response, userDetails[1], userDetails[2], userDetails[4], userDetails[5]);
				ArrayList []columnData=null;
				columnData=connect.getColumns(tableChoice);

				if (columnData==null){		/*(No connection was made).*/
					message="Unable to establish a database connection to retrieve the columns.";
					displayMessage(request, response, message, null, ERROR);
				} else if (columnData[2]!=null){/*(Exception was raised).*/
					error=(Exception)columnData[2].get(0);
					if (error!=null){
					
						message="An error occurred whilst trying to obtain the columns information.";
						displayMessage(request, response, message, error, ERROR);
						return;
					}
				} else {			/*(Successfully retrieved the columns in the table)*/
				
					if (queryType.equals("select")){
						dbm("Querytype is select");
						String preparedQuery = selectStatement(columnData, tableChoice);
						request.setAttribute("preparedQuery", preparedQuery);
						RequestDispatcher rd=request.getRequestDispatcher(SQLQUERY);
						rd.forward(request, response);
					} else if (queryType.equals("insert")){
						String preparedQuery = insertStatement(columnData, tableChoice);
						request.setAttribute("preparedQuery", preparedQuery);
						RequestDispatcher rd=request.getRequestDispatcher(SQLQUERY);
						rd.forward(request, response);
					} else if (queryType.equals("update")){
						String preparedQuery = updateStatement(columnData, tableChoice);
						request.setAttribute("preparedQuery", preparedQuery);
						RequestDispatcher rd=request.getRequestDispatcher(SQLQUERY);
						rd.forward(request, response);
					} else if (queryType.equals("delete")){
						String preparedQuery = deleteStatement(columnData, tableChoice);
						request.setAttribute("preparedQuery", preparedQuery);
						RequestDispatcher rd=request.getRequestDispatcher(SQLQUERY);
						rd.forward(request, response);
					} else if (queryType.equals("desccolumns")){
						// If it's a desc columns query, go straight to the next 'else if' block below.
						String preparedQuery = descColumnsStatement(tableChoice);
					
						String PREPAREDEXECUTEQUERY = response.encodeURL("getServlet?query="+preparedQuery+"&action=executeQuery");
						RequestDispatcher rd=request.getRequestDispatcher(PREPAREDEXECUTEQUERY);
						rd.forward(request, response);
					} else if (queryType.equals("desckeys")){
						dbm("About to do descKeysstatment.");
						// If it's a desc keys query, get the keys info and display it on queryresults.jsp.
						ArrayList []keysData=null;
					
						keysData=connect.getKeys(tableChoice);

						if (keysData==null){
							message="Unable to establish a database connection to retrieve the keys.";
							displayMessage(request, response, message, null, ERROR);
						} else if (keysData[2]!=null){
							error=(Exception)keysData[2].get(0);
							if (error!=null){
					
								message="An error occurred whilst trying to obtain the keys information.";
								displayMessage(request, response, message, error, ERROR);
								return;
							}
						}
						request.setAttribute("keysData", keysData);
						request.setAttribute("tableChoice", tableChoice);
						RequestDispatcher rd=request.getRequestDispatcher(QUERYRESULTS);
						rd.forward(request, response);
					}
				}
			}
				
		} else if (action.equals("executeQuery")){
			if (!validSession(request, response, session, LOGIN)){
				return;
			}
			ArrayList[]queryResults=null;
			String query=(String)request.getParameter("query");
			String preparedQuery=(String)request.getParameter("preparedQuery");
			if (query==null || query.trim().length()==0) {
				dbm("query = null.");
				message="You must enter a valid database query.";
				displayMessage(request, response, message, null, SQLQUERY);
				return;
			}
			dbm("query != null.");
			dbm("Action is "+action);
			String[]userDetails=getUserDetails(request, response, action);
			connect=new GetData(request, response, userDetails[1], userDetails[2], userDetails[4], userDetails[5]);
			
			queryResults=connect.getQuery(query);
			
			
			//dbm("Error? "+error.toString());
			if (queryResults==null){
				message="ERROR - unable to establish a connection with the database for the query.";
				displayMessage(request, response, message, error, ERROR);
				return;
			} else if (queryResults[2]!=null){
				dbm("QueryResults(2) successfully returned");
				
				//if (errormessage!=null){	
					//message="A database-related error occurred whilst trying to execute the query.";
					//dbm("Exception "+errormessage);
					
					
					//if (errormessage.indexOf("ORA-0")!=-1){
						//displayMessage(request, response, message, error, SQLQUERY);
						//return;
					//} else {
						//displayMessage(request, response, message, error, ERROR);
						//return;
					//}
				//}
			}

			/* Update the sql query log */			
			String log=(String)session.getAttribute("sqlLogData"); 
			if (log==null) log="";
			log=log+"\n"+query;
			session.setAttribute("sqlLogData", log);
			dbm("Set the log attribute.");
			//if (queryResults[0]==null) {	NO ROWS RETURNED BY USER QUERY
				//dbm("DBToolsServlet: columnNames contains null.");
				//request.setAttribute("message", "The query returned no rows.");
				//displayMessage(request, response, message, null, QUERYRESULTS);
				//return;
			//}

			
			if (queryResults[0]!=null) request.setAttribute("columnNames", queryResults[0]);
			dbm("Set the columnNames attribute.");
			if (queryResults[1]!=null) request.setAttribute("rowData", queryResults[1]);
			if (queryResults[2]!=null) request.setAttribute("errorData", queryResults[2]);
			
			dbm("Set the rowData attribute.");
			dbm("Set the errorData attribute.");
			if (preparedQuery!=null){
				request.setAttribute("preparedQuery", "yes");
			}
			RequestDispatcher rd=request.getRequestDispatcher(QUERYRESULTS);
			dbm("Returning to the query results page.");
			rd.forward(request, response);
 		}
	}

	private boolean validSession(HttpServletRequest request, HttpServletResponse response, HttpSession session, String LOGIN) throws ServletException, IOException{
		if (session==null || session.getAttribute("username")==null) {
			String message="Your user session has expired.";
			displayMessage(request, response, message, null, LOGIN);
			return false;
		} else {
			return true;
		}
	}

	private void displayMessage(HttpServletRequest request, HttpServletResponse response, String message, Exception exception, String url) throws ServletException, IOException {
		dbm("Inside displayMessage.");
		request.setAttribute("message", message);
		Enumeration enu=request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name=(String)enu.nextElement();
			request.setAttribute(name, request.getParameter(name));
		}
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

	/* 	This method retrieves the user's account details for the purpose of getting a connection to the database - it returns either the user's username and password OR dbusername and password, depending on which jsp they are on when they access the database. 
	 */
	protected String[] getUserDetails(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException{
		
		String username=null;
		String dbusername=null;
		String dbname=null;
		String dbhost=null;
		String password=null;
		String connectToDatabase=null;
		String saveSettings=null;
		String dbDriver=null;
		String dbURL=null;
		String[]userDetails={"", "", "", "", "", ""};
		String LOGIN = response.encodeURL("login.jsp");
		String DBLOGIN = response.encodeURL("dblogin.jsp");

		dbm("Inside getUserDetails().");
		if (action==null) dbm("Action is null");
		/*	If the user wants to access the application from the main login screen, obtain the username & password.
		/*	For all other database operations, obtain the dbusername & password. 
		 */
		
		if (action.equals("login")){
			dbm("Action is login.");
			username=(String)request.getParameter("username");
			password=(String)request.getParameter("password");
			dbm("Username supplied is: "+username);
			dbm("Password supplied is: "+password);
			connectToDatabase=(String)request.getParameter("connectToDatabase");
			/* 	(Indicates whether the user's database preferences box is checked or not) 
			 */
			userDetails[0]=username;
			userDetails[1]=password;
			userDetails[2]=connectToDatabase;
		} else if (action.equals("displayTables")){
			HttpSession session=request.getSession(false);
			username=(String)session.getAttribute("username");
			
			/*The next few lines are to cater for the fact that the user may already be connected as a certain password account when he/she visits this page.*/
			dbusername=(String)request.getParameter("dbusername");
			if (dbusername==null){
				dbusername=(String)session.getAttribute("dbusername");
			}
			password=(String)request.getParameter("password");
			if (password==null){
				password=(String)session.getAttribute("password");
			}
			dbhost=(String)request.getParameter("dbhost");
			if (dbhost==null){
				dbhost=(String)session.getAttribute("dbhost");
			}
			dbname=(String)request.getParameter("dbname");
			if (dbname==null){
				dbname=(String)session.getAttribute("dbname");
			}
			saveSettings=(String)request.getParameter("saveSettings");
			/* (To indicate whether the user wants to save the current database login settings) */
			userDetails[0]=username;
			userDetails[1]=dbusername;
			userDetails[2]=password;
			userDetails[3]=saveSettings;
			userDetails[4]=dbhost;
			userDetails[5]=dbname;
		} else if (action.equals("preparedQuery")||action.equals("executeQuery")){
			dbm("Retrieving session");
			HttpSession session=request.getSession(false);
			dbm("Retrieving username");
			username=(String)session.getAttribute("username");
			dbm("Retrieving dbusername");
			dbusername=(String)session.getAttribute("dbusername");
			dbm("Retrieving password");
			password=(String)session.getAttribute("password");
			dbm("Retrieving saveSettings");
			dbhost=(String)session.getAttribute("dbhost");
			dbm("Retrieving host");
			dbname=(String)session.getAttribute("dbname");
			dbm("Retrieving database name");
			saveSettings=(String)request.getParameter("saveSettings");
			/* (To indicate whether the user wants to save the current database login settings) */
			userDetails[0]=username;
			userDetails[1]=dbusername;
			userDetails[2]=password;
			userDetails[3]=saveSettings;
			userDetails[4]=dbhost;
			userDetails[5]=dbname;
		}
		dbm("userDetails[5]:"+userDetails[5]);

		dbm("DBToolsServlet.getUserDetails(): returning userDetails.");
		return userDetails;
	}

	private static String selectStatement(ArrayList[]columnData, String tableChoice) {
		ArrayList columnTitles=columnData[0];
		ArrayList columnTypes=columnData[1];
		dbm("Select Statement");
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT \n");
		for (int x=0; x<columnTitles.size()-1; x++){
			sb.append(columnTitles.get(x)+", \n");
		}
		sb.append(columnTitles.get(columnTitles.size()-1)+"\n");
		// (The last column has no comma after it so it's added after the for loop above).
		sb.append("FROM ");	
		sb.append(tableChoice+"\n");
		sb.append("WHERE \n");
		for (int x=0; x<columnTitles.size()-1; x++){
			sb.append(columnTitles.get(x)+" = '"+columnTypes.get(x)+"'\n");
			sb.append("AND ");
		}
		sb.append(columnTitles.get(columnTitles.size()-1)+" = '"+columnTypes.get(columnTitles.size()-1)+"'\n");
		return sb.toString();
	}
	private static String insertStatement(ArrayList[]columnData, String tableChoice) {
		ArrayList columnTitles=columnData[0];
		ArrayList columnTypes=columnData[1];
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO "+tableChoice+"\n");
		sb.append("(");
		for (int x=0; x<columnTitles.size()-1; x++){
			sb.append(columnTitles.get(x)+", \n");
		}
		sb.append(columnTitles.get(columnTitles.size()-1)+"\n");
		// (The last column has no comma after it so it's added after the for loop above).
		sb.append(") \n");	
		sb.append("VALUES \n");
		sb.append("( \n");
		for (int y=0; y<columnTitles.size()-1; y++){
			sb.append("'"+columnTypes.get(y)+"', \n");
		}
		sb.append("'"+columnTypes.get(columnTitles.size()-1)+"'");
		// (The last column has no comma after it so it's added after the for loop above).
		sb.append("\n)");
		return sb.toString();
		
	}
	private static String updateStatement(ArrayList[]columnData, String tableChoice) {
		ArrayList columnTitles=columnData[0];
		ArrayList columnTypes=columnData[1];
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE "+tableChoice+"\n");
		sb.append("SET \n");
		for (int x=0; x<columnTitles.size()-1; x++){
			sb.append(columnTitles.get(x)+" = ");
			sb.append("'"+columnTypes.get(x)+"',\n");
		}
		sb.append(columnTitles.get(columnTitles.size()-1)+" = 'x' \n");
		sb.append("WHERE \n");
		for (int y=0; y<columnTitles.size()-1; y++){
			sb.append(columnTitles.get(y)+" = ");
			sb.append("'"+columnTypes.get(y)+"' AND \n");
		}
		sb.append(columnTitles.get(columnTitles.size()-1)+" = '"+columnTypes.get(columnTitles.size()-1)+"' \n");
		// (The last column has no comma after it so it's added after the for loop above).
		return sb.toString();
	}
	private static String deleteStatement(ArrayList[]columnData, String tableChoice) {
		ArrayList columnTitles=columnData[0];
		ArrayList columnTypes=columnData[1];
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM "+tableChoice+"\n");
		sb.append("WHERE \n");
		for (int x=0; x<columnTitles.size()-1; x++){
			sb.append(columnTitles.get(x)+" = ");
			sb.append("'"+columnTypes.get(x)+"' AND \n");
		}
		sb.append(columnTitles.get(columnTitles.size()-1)+" = '"+columnTypes.get(columnTitles.size()-1)+"' \n");
		return sb.toString();
	}
	private static String descColumnsStatement(String tableChoice) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("Table_name AS \"Table\", ");
		sb.append("Column_name AS \"Column Name\", ");
		sb.append("Data_type AS \"Data Type\", ");
		sb.append("Character_Maximum_length AS \"Size\", ");
		sb.append("Numeric_scale AS \"Decimals\", ");
		sb.append("Ordinal_position AS \"Column No\", ");
		sb.append("Is_Nullable AS \"Nullable\" ");
		sb.append("FROM information_schema.columns ");
		sb.append("WHERE ");
		sb.append("table_name='"+tableChoice+"'");
		return sb.toString();
	}/*
	private static String descColumnsStatement(String tableChoice) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("column_name ");
		sb.append("FROM information_schema.columns ");
		sb.append("WHERE ");
		sb.append("table_name='"+tableChoice+"'");
		return sb.toString();
	}*/

	private static String userStatement() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("* ");
		sb.append("FROM ");
		sb.append("member ");
		sb.append("WHERE ");
		sb.append("username = ");
		sb.append("?");
		sb.append(" AND ");
		sb.append("password = ");
		sb.append("?");
	
		return sb.toString();
	}

	private static String userPreferencesStatement(String username) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("DB_USERNAME, ");
		sb.append("DB_PASSWORD, ");
		sb.append("DB_HOST, ");
		sb.append("DB_NAME ");
		sb.append("FROM ");
		sb.append("DBV_PREFERENCES ");
		sb.append("WHERE ");
		sb.append("USERNAME = '");
		sb.append(username+"'");
	
		return sb.toString();
	}

	private static String amendUserPreferencesStatement(String username, String db_username, String db_password, String db_host, String db_name) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE ");
		sb.append("DBV_PREFERENCES ");
		sb.append("SET ");
		sb.append("DB_USERNAME = '"+db_username+"', ");
		sb.append("DB_PASSWORD = '"+db_password+"', ");
		sb.append("DB_HOST = '"+db_host+"', ");
		sb.append("DB_NAME = '"+db_name+"' ");
		sb.append("WHERE ");
		sb.append("USERNAME = '");
		sb.append(username+"'");
	
		return sb.toString();
	}

	private static String deleteOldUserPreferencesStatement(String username) {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM DBV_PREFERENCES ");
		sb.append("WHERE ");
		sb.append("USERNAME='"+username+"'");
	
		return sb.toString();
	}

	private static String addUserPreferencesStatement(String username, String db_username, String db_password, String db_host, String db_name) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append("DBV_PREFERENCES ");
		sb.append("VALUES (");
		sb.append("'"+db_username+"', ");
		sb.append("'"+db_password+"', ");
		sb.append("'"+db_host+"', ");
		sb.append("'"+db_name+"', ");
		sb.append("'"+username+"')");
	
		return sb.toString();
	}

	private static void dbm(String msg) {
		System.out.println("DBToolsServlet: "+msg);
	}
}
