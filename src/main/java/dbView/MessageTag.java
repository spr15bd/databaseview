/* MessageTag.java - displays any message which may have been forwarded as a request attribute. */

package dbView;

import javax.servlet.jsp.JspWriter;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MessageTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		JspWriter out=pageContext.getOut();
		try {
			String message=(String)request.getAttribute("message");
			if (message==null) {
				message="";
			} else {
				out.print(message);
			}
		} catch (IOException ioe) {
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("MessageTag: "+msg);
	}
	
	
}
