/* ErrorPageTag.java - displays error info on exceptions thrown. */

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
import java.io.StringWriter;
import java.io.PrintWriter;

public class ErrorPageTag extends TagSupport {
	
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		
		StringWriter sw = new StringWriter();
    		PrintWriter pw = new PrintWriter(sw);
		dbm("1");
		JspWriter out=pageContext.getOut();
		dbm("2");
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		dbm("3");
		//Exception exception = (Exception) request.getAttribute("javax.servlet.jsp.jspException");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		
			dbm("4");
		try {
			dbm("Exception object is "+exception);
			dbm("5");
			out.println( "" );
    			dbm("6");
			if (exception!=null) {
				out.print(exception);
    				exception.printStackTrace( pw );
				dbm("7");
			} else {
				out.print("There is no information on the error which was raised.");
			}

    			out.print(sw);
			dbm("8");
    			sw.close();
			dbm("9");
    			pw.close();
			dbm("10");
    			out.println( "" );
    		     	dbm("Finished ErrorPage try block");
			
		} catch (IOException ioe) {
			dbm("IOException has been raised");
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		dbm("About to return to jsp");
		return EVAL_PAGE;
		
	}
	static void dbm(String msg) {
		System.out.println("ErrorPageTag: "+msg);
	}
}


