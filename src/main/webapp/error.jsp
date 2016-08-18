<%@page isErrorPage="true"%>
<%@taglib uri="WEB-INF/tlds/dbViewTags.tld" prefix="DBView"%>


<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>ErrorPage</title></head>
<body link="black" alink="black" vlink="black">

<table><tr>
<td>
	
</td>
<td width="575" valign="top">
   	
	<BR>
	       	<fieldset>
	       	<legend align="center"><b>Error</b></legend>
			<TABLE>
		   		<tr>
		     			
					<td><h1>An error has occurred on the page</h1></td>
			
				</tr>

				<tr>
					<td><DBView:displayerrordetails/></td>
					
					



				</tr>

				<tr>
					<td height="35"><td>
				</tr>
				
		   		<tr>
					<td>
		     				<INPUT type="button" value="Back" onclick="history.back(-1)"> 
					</td>
		     
		   		</tr>
			</TABLE>
               	</fieldset>
    
	  <BR><BR><BR><BR>
</td>
</tr></table>

</body>
</html>