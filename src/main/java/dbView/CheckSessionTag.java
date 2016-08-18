/* CheckSessionTag.java - makes sure that there is a valid session. If not, the user will be forwarded to the login page. */

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

public class CheckSessionTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		HttpSession session=request.getSession(false);
		String login = response.encodeURL("login.jsp");
		if (session==null || session.getAttribute("username")==null) {
			dbm("session null");
			String message="Your user session has expired.";
			dbm("Set message.");
			request.setAttribute("message", message);
			dbm("Set attribute.");
			RequestDispatcher rd=request.getRequestDispatcher(login);
			dbm("Set requestDispatcher.");
			try{
				dbm("Returning user to home page (no session).");
				rd.forward(request, response);
			}catch (ServletException se){
				System.out.println(se);
			}catch (IOException io){
				System.out.println(io);
			}
		}
	
	
		
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("CheckSessionTag: "+msg);
	}
	
	
}
