<%@taglib uri="WEB-INF/tlds/dbViewTags.tld" prefix="DBView"%>
<%@page errorPage="error.jsp"%>
<!doctype html public "-//W3C//DTD html 4.01 Transitional//EN"
			"http://www.w3.org/TR/html4/loose.dtd">
<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>QueryPage</title></head>

<DBView:checksession/>
<body link="black" alink="black" vlink="black">

	<table>
		<tr>
			<td>
				<DBView:displayverticalmenu jspPage="sqlquery" />
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
				
				<table width="95%">
					<% String encodedUrl=response.encodeURL("getServlet?action=executeQuery");

					out.print("<form NAME=sqldisplay method=post action="+encodedUrl+"&tableChoice="+request.getParameter("tableChoice")); %>
    						<tr>
      							<td>
								<DBView:querytextarea/>
							</td>
      							<td>
								<table height="100%" width="257">
        								<tr>
          									<td valign="top"></td>
        								</tr>
        				
									<tr>
           									<td valign="bottom">
              										<font size="1" face="verdana">
			Each one of the four Gen.Xxxx buttons (select a table from the 
			List of tables page to see these buttons) generates relevant Sql code 
			for a selected table. The values, which by default appear as x, 9 or 9.9 
			should be changed to the neccessary values which you wish to delete/update etc. 
			Alternatively, you can type your own Sql code directly in the textarea. Once
			you are satisfied with your sql code, press the Execute Query button. You
			will then be forwarded to a page displaying the results of your Sql query. Selecting
			the Clear Text button refreshes the page.
           	   									</font>
            									</td>
          								</tr>
         							</table>
       							</td>
    						</tr>
    			
						<tr>
      							<td>
        							<table>
          								<tr>
            									<td valign="middle">
											<input type="submit" value="Execute Query">
										</td>
            						
            		  
            									<form NAME=cleardisplay method=post action=<DBView:encodeurl url="sqlquery.jsp"/>>
										<td valign="bottom">
											<input type="reset" value="Clear Text">
										</td>
										</form>
         								</tr>
								</table>
							</td>
         					</tr>
					</form>
        			</table>
      			</td>
		</tr>
	</table>
</body>
</html>