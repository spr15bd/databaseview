<%@taglib uri="WEB-INF/tlds/dbViewTags.tld" prefix="DBView"%>
<%@page errorPage="error.jsp"%>
<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>LoginPage</title></head>
<body link="black" alink="black" vlink="black">





<table>
	<tr>
		<td>
			<DBView:displayverticalmenu/>
		</td>
 		
		<td width="575" valign="top">
   	
	

  		<FORM action=<DBView:encodeurl url="getServlet?action=login"/> method = POST>
		<BR>
	    
		<fieldset>
	    		<legend align="center"><b>Login Page</b></legend>
				<TABLE>
		   			<tr>
		     				<td align="right">Username:</td>
		     				<td><INPUT type="text" name="username" value="johnsmith" size="20"></td>
		   			</tr>
		   	
					<tr>
		      				<td align="right">Password:</td>
		      				<td><INPUT type="password" name="password" value="passw" size="20"></td>
		   			</tr>
		   
					<tr>
		      				<td colspan="2">
		         			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	     
			     				<font color="#336699">
								<b>Use default database preferences:</b>
							</font>
			     				<INPUT type="checkbox" name="connectToDatabase" unchecked>
		      				</td>
		   			</tr>
		   
					<tr>
		      				<td></td>
		      				<td><INPUT type = "submit" value ="Sign In"></td>
		   			</tr>
		   
					
		   
					<tr>
		     				<td colspan="2" align="center">
		        			Please use a valid account to log into the application
		     				</td>
		  			</tr>
       				</TABLE>
      		</fieldset>
     		</FORM>
		
		<b><DBView:displaymessage/></b>
		<br><font color=red>[For demo purposes you may use the login details already entered above].</font>
  		</td>
	</tr>
</table>

</body>
</html>