package com.teradata;

import java.util.Base64;
import java.sql.*;

public class b64 {
	/*
	 * Base64 Decoder 
	 */
	public static byte[] dec( java.lang.String p1) throws SQLException{
		byte[] ret = Base64.getDecoder().decode(p1);
		return ret;
	} 

	/*
	 * Base64 Encoder 
	 */
	public static java.lang.String enc( java.sql.Blob p1 ) throws SQLException{
		String ret = Base64.getEncoder().encodeToString(p1.getBytes(1, (int)p1.length()));		
		return ret;
	} 	
}
