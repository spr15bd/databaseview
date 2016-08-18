/* HorizontalMenuTag - displays the horizontal menu at the top of most of the jsps.*/

package dbView;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

public class HorizontalMenuTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		
		String connect=response.encodeURL("dblogin.jsp");
		String listoftables=response.encodeURL("tables&views.jsp");
		String sqlstatement=response.encodeURL("sqlquery.jsp");
		String sqlquerylogs=response.encodeURL("querylog.jsp");

		try{
			out.print("<table bgcolor=\"white\" BORDER=0  bordercolor=\"black\" CELLPADDING=\"0\" CELLSPACING=\"0\" WIDTH=\"100%\">");
      			out.print("<tr VALIGN=\"MIDDLE\">");
      			out.print("	<td align='center' NOWRAP>&nbsp;&nbsp;<A HREF=\""+connect+"\" onMouseOver=\"window.status='Connect to database'; return true;\" onMouseOut=\"window.status=''; return true;\">Connect</A>&nbsp;&nbsp;</td>");
     			out.print("	<td align='center' NOWRAP>&nbsp;&nbsp;<A HREF=\""+listoftables+"\"");
      			out.print(" onMouseOver=\"window.status='List of tables'; return true;\" onMouseOut=\"window.status=''; return true;\">List Of Tables</A>&nbsp;&nbsp;</td>");
      			out.print("	<td align='center' NOWRAP>&nbsp;&nbsp;<A HREF=\""+sqlstatement+"\"onMouseOver=\"window.status='Select SQL statement'; return true;\" onMouseOut=\"window.status=''; return true;\">Sql Statement</A>&nbsp;&nbsp;</td>");
      			out.print("	<td align='center' NOWRAP>&nbsp;&nbsp;<A HREF=\""+sqlquerylogs+"\"  onMouseOver=\"window.status='SQL Log'; return true;\" onMouseOut=\"window.status=''; return true;\">Sql Query Logs</A>&nbsp;&nbsp;</td>");
      			out.print("	<br>");
      			out.print("</tr>");
      			out.print("</table>");
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("DisplaySQLMenuTag: "+msg);
	}
	
	
}
