package base64;

import java.util.Base64;
import java.sql.SQLException;
import com.teradata.fnc.*;

public class base64 {
	/*
	 * Base64 Decoder 
	 */
	public static Blob Decoder( java.lang.String p1 ) {
		try {
			byte[] buf =Base64.getDecoder().decode(p1);
			Blob ret = null;
			int len = ret.setBytes(1, buf);
			return ret;
		} catch (SQLException e) {
			return null;
		}
	} 
}
