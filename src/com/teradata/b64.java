package com.teradata;

import java.util.Base64;
import java.sql.*;
import java.io.IOException;

public class b64 {
	/*
	 * Base64 Decoder 
	 */
	public static java.sql.Blob dec(java.sql.Clob p1) throws SQLException{
		String s = "";
		try {
			java.io.BufferedReader rd = new java.io.BufferedReader(p1.getCharacterStream());
			s = rd.readLine();
			rd.close();
		} catch(IOException e) {
		}
		byte[] b = Base64.getDecoder().decode(s);
		java.sql.Blob ret = com.teradata.fnc.Blob.getUDFOutputBlob();
		ret.setBytes(1, b);
		return ret;
	} 

	/*
	 * Base64 Encoder 
	 */
	public static java.sql.Clob enc( java.sql.Blob p1 ) throws SQLException{
		String s = Base64.getEncoder().encodeToString(p1.getBytes(1, (int)p1.length()));
		java.sql.Clob ret = com.teradata.fnc.Clob.getUDFOutputClob();
		ret.setString(1, s);
		return ret;
	} 	
}
