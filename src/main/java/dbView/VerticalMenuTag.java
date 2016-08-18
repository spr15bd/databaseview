/* VerticalMenuTag.java - displays the menu on the laft hand side of every jsp. Urls are encoded. */

package dbView;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.io.IOException;

public class VerticalMenuTag extends TagSupport {
	private String jspPage = "";
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		HttpSession session=request.getSession(false);
		JspWriter out = pageContext.getOut();
		String preparedQuery=(String)request.getAttribute("preparedQuery");
		if (preparedQuery==null) preparedQuery="";

		// Encode all the urls in this menu so it will work even without cookies on the user's pc.
		String stesolutions = response.encodeURL("http://personalprojects-spr15bd.rhcloud.com");
		String logout= response.encodeURL("login.jsp");
		String select = response.encodeURL("getServlet?action=preparedQuery&tableChoice="+request.getParameter("tableChoice")+"&queryType=select");
		String insert = response.encodeURL("getServlet?action=preparedQuery&tableChoice="+request.getParameter("tableChoice")+"&queryType=insert");
		String update = response.encodeURL("getServlet?action=preparedQuery&tableChoice="+request.getParameter("tableChoice")+"&queryType=update");
		String delete = response.encodeURL("getServlet?action=preparedQuery&tableChoice="+request.getParameter("tableChoice")+"&queryType=delete");
		String desccolumns = response.encodeURL("getServlet?action=preparedQuery&tableChoice="+request.getParameter("tableChoice")+"&queryType=desccolumns");
		String desckeys = response.encodeURL("getServlet?action=preparedQuery&tableChoice="+request.getParameter("tableChoice")+"&queryType=desckeys");
		String admin = response.encodeURL("sqlquery.jsp");

		try {
			out.print("	<table width=\"100\">");
			out.print("	<tr>");
			out.print("	<td width=\"100\" height=\"15\">");
			out.print("		<a href=\""+stesolutions+"\">Projects Page</a>");
			out.print("	</td>");
			out.print("	</tr>");
			
			out.print("	<tr>");
			out.print("	<td width=\"100\" height=\"15\">");
			out.print("		<a href=\""+logout+"\">Logout</a>");
			out.print("	</td>");
			out.print("	</tr>");

			if (jspPage=="sqlquery"){
				if (request.getParameter("tableChoice")!=null){
					out.print("	<tr>");
					out.print("	<td height=\"15\">");
					out.print("		<a href=\""+select+"\">Gen. Select");
					out.print("		</a>");
					out.print("	</td>");
					out.print("	</tr>");
				
					out.print("	<tr>");
					out.print("	<td height=\"15\">");
					out.print("		<a href=\""+insert+"\">Gen. Insert");				out.print("		</a>");
					out.print("	</td>");
					out.print("	</tr>");
				
					out.print("	<tr>");
					out.print("	<td height=\"15\">");
					out.print("		<a href=\""+update+"\">Gen. Update");
					out.print("		</a>");
					out.print("	</td>");
					out.print("	</tr>");
			
					out.print("	<tr>");
					out.print("	<td height=\"15\">");
					out.print("		<a href=\""+delete+"\">Gen. Delete");
					out.print("		</a>");
					out.print("	</td>");
					out.print("	</tr>");
			
					out.print("	<tr>");
					out.print("	<td height=\"15\">");
					out.print("		<a href=\""+desccolumns+"\">Desc. Columns");
					out.print("		</a>");
					out.print("	</td>");
					out.print("	</tr>");
			
					out.print("	<tr>");
					out.print("	<td height=\"15\">");
					out.print("		<a href=\""+desckeys+"\">Desc. Keys");
					out.print("		</a>");
					out.print("	</td>");
					out.print("	</tr>");
				} else {
					out.print("	<tr>");
					out.print("	<td height=\"15\">");
					out.print("		<a href=\""+admin+"\"");
					out.print("	</td>");
				}
			} 
			out.print("	</table>");
			
		} catch (IOException ioe) {
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		return EVAL_PAGE;
		
	}
	static void dbm(String msg) {
		System.out.println("VerticalMenuTag: "+msg);
	}

	public String getJspPage(){
		return jspPage;
	}

	public void setJspPage(String jspPage){
		this.jspPage = jspPage;
	}
}


