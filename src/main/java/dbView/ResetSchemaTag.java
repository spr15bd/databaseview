/* ResetSchemaTag.java - resets the session attributes 'dbusername', 'password', and 'schemaData' to null, whenever the user visits the connection jsp to access a database account. */

package dbView;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ResetSchemaTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpSession session=request.getSession(false);
		session.setAttribute("dbusername", null);
		session.setAttribute("password", null);
		session.setAttribute("schemaData", null);
		
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("ResetSchemaTag: "+msg);
	}
	
	
}
