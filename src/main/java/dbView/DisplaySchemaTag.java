/* DisplaySchemaTag.java - this tag is used on the tables&views jsp to display all the user's tables and views. */

package dbView;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.util.ArrayList;

public class DisplaySchemaTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		
		ArrayList tableData=(ArrayList)request.getAttribute("schemaData");
		if (tableData==null) {
			String LISTOFTABLES=response.encodeURL("getServlet?action=displayTables");
			RequestDispatcher rd=request.getRequestDispatcher(LISTOFTABLES);
			try{
				rd.forward(request, response);
			} catch (ServletException se){
				System.out.println(se);
			} catch (IOException io){
				System.out.println(io);
			}
		}
		JspWriter out=pageContext.getOut();
		try {
			out.print("<table bgcolor=\"white\" BORDER=\"1\" bordercolor=\"black\" CELLPADDING=\"0\" CELLSPACING=\"0\" WIDTH=\"100%\">");
 	   		out.print("<tr>");

	       		out.print("	<th>Name</th>");
	
	        	out.print("	<th>Type</th>");
	
	        	out.print("	<th>Schema</th>");
	
	   		out.print("</tr>");
			for (int i=0; i<tableData.size(); i=i+3) {
				out.print("<tr bgcolor=\"#cccccc\">");
				out.print("	<td>");
      	        		out.print("		<a href=\"sqlquery.jsp?tableChoice="+tableData.get(i)+"\">"+tableData.get(i)+"</a>");
      	     			out.print("	</td>");
				
      	     			out.print("	<td align=\"center\">"+tableData.get(i+1)+"</td>");
	         		out.print("	<td align=\"center\">"+tableData.get(i+2)+"</td>");
          			out.print("</tr>");
			}
            		out.print("</table>");
		} catch (IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
	
		
			return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("DisplaySchemaTag: "+msg);
	}
	
	
}
