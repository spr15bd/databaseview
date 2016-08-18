/* EncodeUrlTag.java - encodes urls so they will work even when cookies are disabled. */

package dbView;

import javax.servlet.jsp.JspWriter;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public class EncodeUrlTag extends TagSupport {
	private String url = "";
	public int doStartTag() throws JspTagException {
		dbm("doStartTag() called ......");
		return SKIP_BODY;
	}
	public int doEndTag() throws JspTagException {
		dbm("doEndTag() called ......");
		HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
		String encodedUrl=response.encodeURL(url);
		JspWriter out=pageContext.getOut();
		try {
			out.print(encodedUrl);
		} catch (IOException ioe) {
			throw new JspTagException("Exception in doEndTag()"+ioe.getMessage());
		}
		
		return EVAL_PAGE;
		
	}
	private static void dbm(String msg) {
		System.out.println("HomeTag: "+msg);
	}
	
	public String getUrl(){
		return url;
	}

	public void setUrl(String address){
		this.url = address;
	}	
}
