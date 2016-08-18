/* DisplaySQLLogTag.java - This tag is used in the sql log jsp - it displays a record of all the queries made this session.*/

package dbView;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplaySQLLogTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		HttpSession session=request.getSession(false);
		String CLEARLOG=response.encodeURL("querylog.jsp?clear=yes");
		String sqlLogData=(String)session.getAttribute("sqlLogData");
		String cleared=(String)request.getParameter("clear");
		if (sqlLogData==null || sqlLogData.trim().length()==0 || (cleared!=null)){
			session.setAttribute("sqlLogData", "");
			sqlLogData="(No queries have been made this session).";
		} 
		
		
		try{
			out.print("<form method=\"post\" action=\""+CLEARLOG+"\">");
			out.print("	<table width=\"100\">");
			out.print("		<tr>");
			out.print("			<td>");
			out.print("				<textarea name=\"query\" rows=\"20\" cols=\"50\">"+sqlLogData+"</textarea>");
			out.print("			</td>");
			out.print("			<td>");
			out.print("				<input type=\"submit\" value=\"Clear SQL Log\">");
			out.print("			</td>");
			out.print("		</tr>");
			out.print("	</table>");
			out.print("</form>");
		} catch (IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
	}
	private static void dbm(String msg) {
		System.out.println("DisplaySQLLogTag: "+msg);
	}
	
	
}
