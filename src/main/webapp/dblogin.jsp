<%@taglib uri="WEB-INF/tlds/dbViewTags.tld" prefix="DBView"%>
<%@page errorPage="error.jsp"%>
<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>DatabaseLoginPage</title></head>
<body link="black" alink="black" vlink="black">

<DBView:checksession/>

<!--	This next tag ensures that whenever the user visits this page (to connect to a database account), their session attributes for the user account they've just been logged onto are reset to null.	
-->
<DBView:resetschema/>

<table>
	<tr>
		<td>
			<DBView:displayverticalmenu/>
		</td>
 		
		<td width="575" valign="top">
   	
	

  		<FORM action=<DBView:encodeurl url="getServlet?action=displayTables"/> method = POST>
		<BR>
	    
		<fieldset>
	    		<legend align="center"><b>Database Account Information</b></legend>
				<TABLE>
		   			<tr>
		     				<td align="right">Database Username:</td>
		     				<td><INPUT type="text" name="dbusername" value="u£!sR561lE7" size="20"></td>
		   			</tr>
		   	
					<tr>
		      				<td align="right">Database Password:</td>
		      				<td><INPUT type="password" name="password" value="!jT5sE69LkD" size="20"></td>
		   			</tr>
		   	
					<tr>
		      				<td align="right">Database Host/Port:</td>
		      				<td><INPUT type="text" name="dbhost" value="jdbc:mysql://127.2.210.2:3306" size="20"></td>
		   			</tr>
		   	
					<tr>
		      				<td align="right">Database Name:</td>
		      				<td><INPUT type="text" name="dbname" value="databaseview" size="20"></td>
		   			</tr>
		   
					<tr>
		      				<td colspan="2">
		         			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	     
			     				<font color="#336699">
								<b>Store these parameters as my default database settings:</b>
							</font>
			     				<INPUT type="checkbox" name="saveSettings" checked>
		      				</td>
		   			</tr>
		   
					<tr>
		      				<td></td>
		      				<td><INPUT type = "submit" value ="Connect"></td>
		   			</tr>
		   
					<tr>
		     				<td colspan="2" align="center"><br><br>
		        				(Default Ports:Oracle-1521;MySql-3306;SqlServer-1433;Sybase-1498)		        				</td>
		  			</tr>
		   
					<tr>
		     				<td colspan="2" align="center">
		        			
		     				</td>
		  			</tr>
       				</TABLE>
      		</fieldset>
     		</FORM>
		
		<b><DBTools:displaymessage/></b>
  		</td>
	</tr>
</table>

</body>
</html>