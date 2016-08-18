// Utility class to format column names.....
package dbView;

public class StringUtil {
	public static String capitalise(String s) {
   		if (s.length()==0) {
        		return s;
   		} else {
       			return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
   		}
	}
}
