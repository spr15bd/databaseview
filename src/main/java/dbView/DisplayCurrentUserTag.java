/* DisplayCurrentUserTag.java - This tag is used in to display the name of the user currently logged onto the db account.*/

package dbView;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DisplayCurrentUserTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession(false);
		
		

		try{
			out.print("<table CELLPADDING=\"0\" CELLSPACING=\"0\" WIDTH=\"100%\">");
	 			out.print("<tr>");
	    				out.print("<td width=\"100%\"><font color=\"white\">");
	      					out.print("You are connected as:&nbsp;&nbsp;"+session.getAttribute("dbusername"));
						out.print("<br>");
						String tableChosen=(String)request.getParameter("tableChoice");
						if (tableChosen==null||tableChosen.equals("null")){
							out.print("Selected table:&nbsp;&nbsp;  ......</font>");
							
						} else{
							out.print("Selected table:&nbsp;&nbsp;  "+tableChosen+"</font>");
						}
	    				out.print("</td>");  
         			out.print("</tr>");
        		out.print("</table>");
		}catch(IOException ioe){
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("DisplayCurrentUserTag: "+msg);
	}
	
	
}
