<%@taglib uri="WEB-INF/tlds/dbViewTags.tld" prefix="DBView"%>
<%@page errorPage="error.jsp"%>
<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Tables&ViewsPage</title></head>
<body link="black" alink="black" vlink="black">

<DBView:checksession/>

<table>
	<tr>
		<td>
			<DBView:displayverticalmenu/>
		</td>
		
		<td width="575" valign="top">
    
   			<table border="0" bordercolor="black" width="100%" cellspacing="0" cellpadding="1">

   				<tr bgcolor="#336699">
      					<td>
 						<DBView:displaycurrentuser/>
       					</td>
    				</tr>
    				<tr>
      					<td>
      						<DBView:displayhorizontalmenu/>
     					</td>
   				</tr>
			</table>
    
 			<DBView:displayschema/>
       
		</td>
	</tr>
</table>

</body>
</html>