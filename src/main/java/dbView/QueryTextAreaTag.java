/* QueryTextAreaTag.java - displays the textbox used to type SQL queries. */

package dbView;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.io.IOException;

public class QueryTextAreaTag extends TagSupport {
	private String jspPage = "";
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		JspWriter out = pageContext.getOut();
		String preparedQuery=(String)request.getAttribute("preparedQuery");
		Exception exception=(Exception)request.getAttribute("javax.servlet.jsp.jspException");
		if (preparedQuery==null) preparedQuery="";
		String text="";
		if (exception==null){
			text=preparedQuery;
		} else {
			text=exception.toString();
		}
		try {
			out.print("	<table width=\"100\">");
			out.print("		<tr>");
			out.print("			<td>");
			out.print("				<textarea name=\"query\" rows=\"12\" cols=\"36\">"+text+"</textarea>");
			out.print("			</td>");
			out.print("		</tr>");
			out.print("	</table>");
			
		} catch (IOException ioe) {
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
		
	}
	static void dbm(String msg) {
		System.out.println("QueryTextAreaTag: "+msg);
	}

	public String getJspPage(){
		return jspPage;
	}

	public void setJspPage(String jspPage){
		this.jspPage = jspPage;
	}
}


