/* DisplayKeysTag.java - this tag is used on the queryresults page when the user requests key information on a table. */

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

public class DisplayKeysTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		
		ArrayList []keysData=(ArrayList[])request.getAttribute("keysData");
		String tableChoice=(String)request.getAttribute("tableChoice");

		
		if (keysData!=null){
			JspWriter out=pageContext.getOut();
			try {
				// display primary and foreign keys data - keysData[0] is the primary key, keysData[1] contains foreign keys
				ArrayList primaryKeys=keysData[0];
				ArrayList foreignKeys=keysData[1];
		
				out.print("<table bgcolor=\"white\" border=\"1\" bordercolor=\"black\" cellspacing=\"0\" cellpadding=\"1\" width=\"100%\">");
				out.print("<caption><b>KEYS INFO</b></caption>");
				out.print("	<TR>");
				out.print("		<TD ALIGN=\"CENTER\">");
				out.print("			<FONT COLOR=\"RED\">");
				if (primaryKeys.size()>0){
					out.print("			THE PRIMARY KEY (PK) FOR THE TABLE '"+tableChoice+"' IS: ");
					out.print("			</FONT>");
					out.print("			<FONT COLOR=\"BLUE\"> "+keysData[0].get(0));
				} else {
					out.print("			THERE ARE NO PRIMARY KEYS IN THE TABLE.");
				}
				out.print("			</FONT>");
				out.print("		</TD>");
				out.print("	</TR>");
				out.print("	<TR>");
				out.print("		<TD>");
				out.print("			<TABLE border=\"1\" bordercolor=\"black\" cellspacing=\"0\" cellpadding=\"1\" width=\"100%\">");
				out.print("				<TR ALIGN=\"CENTER\">");
				out.print("					<TH WIDTH=\"50%\">FK FROM OTHER TABLES</TH>");
				out.print("					<TH WIDTH=\"50%\">FK TABLE</TH>");
				out.print("				</TR>");
				for(int x=0; x<keysData[1].size(); x=x+2){
					out.print("				<TR>");
					out.print("					<TD>"+foreignKeys.get(x)+"</TD>");		
					out.print("					<TD>"+foreignKeys.get(x+1)+"</TD>");
					out.print("				</TR>");
				}
				out.print("			</TABLE>");
				out.print("		</TD>");
				out.print("	</TR>");
				out.print("</TABLE>");
			} catch (IOException ioe){
				throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
			}
		}
		return EVAL_PAGE;
	}
	private static void dbm(String msg) {
		System.out.println("DisplayKeysTag: "+msg);
	}
	
	
}
