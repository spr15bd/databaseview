/* DisplayQueryResultsTag.java - This tag is used in the displayresults jsp to show the results of a query.*/

package dbView;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DisplayQueryResultsTag extends TagSupport {
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		JspWriter out=pageContext.getOut();
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		 
		ArrayList columnNames=(ArrayList)request.getAttribute("columnNames");
		ArrayList rowData=(ArrayList)request.getAttribute("rowData");
		ArrayList errorData=(ArrayList)request.getAttribute("errorData");
		ArrayList []keysData=(ArrayList[])request.getAttribute("keysData");
		String tableChoice=(String)request.getAttribute("tableChoice");

		if (errorData!=null){
			dbm("ErrorData is not null");
			String errormessage=(String)errorData.get(0);
			dbm("Got errorData");
			try{
				out.print("<table bgcolor=\"white\" border=\"1\" bordercolor=\"black\" width=\"100%\">");
					out.print("<tr>");
					out.print("<th>ERROR!</th>");
					out.print("</tr>");
					out.print("<tr>");
					out.print("<td>");
					out.print(errormessage);
					out.print("<br><center><INPUT type=\"button\" value=\"Back\" onclick=\"history.back(-1)\"></center>");
					out.print("</td>");
					out.print("</tr>");
					
					
					
					out.print("</table>");
			}catch(IOException ioe){
				throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
			}
		}
		else if (keysData!=null){
			try{
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
				if (foreignKeys.size()==0){
					out.print("<TR>");
					out.print("	<TD ALIGN=\"CENTER\">");
					out.print("		<FONT COLOR=\"BLUE\">");
					out.print("			THERE ARE NO FOREIGN KEYS IN THE TABLE.");
					out.print("		</FONT>");
					out.print("	</TD>");
					out.print("</TR>");
					out.print("</TABLE>");
				} else {
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
				}
			}catch(IOException ioe){
				throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
			}
		} else if (columnNames==null){
			try{
				out.print("<table bgcolor=\"white\" border=\"1\" bordercolor=\"black\" width=\"100%\">");
					out.print("<tr>");
					out.print("<th>NO DATA!</th>");
					out.print("</tr>");
					out.print("<tr>");
					out.print("<td>");
					out.print("There are no rows to display.");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
			}catch(IOException ioe){
				throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
			}
		} else if (columnNames.get(0).equals("update query")||columnNames.get(0).equals("delete query")||columnNames.get(0).equals("insert query")){
			try{
				out.print("<table bgcolor=\"white\" border=\"1\" bordercolor=\"black\" width=\"100%\">");
					out.print("<tr>");
					out.print("<th>SUCCESSFUL UPDATE</th>");
					out.print("</tr>");
					out.print("<tr>");
					out.print("<td>");
					out.print("The change was successful.");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
			}catch(IOException ioe){
				throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
			}
		} else if (columnNames.get(0).equals("create query")){
			try{
				out.print("<table bgcolor=\"white\" border=\"1\" bordercolor=\"black\" width=\"100%\">");
					out.print("<tr>");
					out.print("<th>SUCCESSFUL UPDATE</th>");
					out.print("</tr>");
					out.print("<tr>");
					out.print("<td>");
					out.print("The table was created.");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
			}catch(IOException ioe){
				throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
			}
		} else if (columnNames.get(0).equals("drop query")){
			try{
				out.print("<table bgcolor=\"white\" border=\"1\" bordercolor=\"black\" width=\"100%\">");
					out.print("<tr>");
					out.print("<th>SUCCESSFUL UPDATE</th>");
					out.print("</tr>");
					out.print("<tr>");
					out.print("<td>");
					out.print("The table was deleted.");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
			}catch(IOException ioe){
				throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
			}
		} else {
			try{
				dbm("Not update");
				if (rowData.size()>0 && columnNames.size()>0) {
					out.print("<table>");
					out.print("<tr>");
					for (int i=0; i<columnNames.size(); i++) {
						out.print("<td>");
						out.print("<b>"+columnNames.get(i)+"</b>");
						out.print("</td>");	
					}
	
					out.print("</tr>");

		
		
					for (int n=0; n<rowData.size(); n++) {
						out.print("<tr>");
						ArrayList columnData=(ArrayList)rowData.get(n);
						for (int i=0; i<columnData.size(); i++) {
							dbm("There's columnData");
							out.print("<td>");
							
								out.print(columnData.get(i));
							
							out.print("</td>");
						}
						out.print("</tr>");
					}

					out.print("</table>");
				} else {
					out.print("<table bgcolor=\"white\" border=\"1\" bordercolor=\"black\" width=\"100%\">");
					out.print("<tr>");
					out.print("<th>NO DATA!</th>");
					out.print("</tr>");
					out.print("<tr>");
					out.print("<td>");
					out.print("There are no rows to display for this query.");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
				}
			}catch(IOException ioe){
				throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
			}
		
			
		}
		return EVAL_PAGE;
	}
	private static void dbm(String msg) {
		System.out.println("DisplayQueryResultsTag: "+msg);
	}
	
	
}
