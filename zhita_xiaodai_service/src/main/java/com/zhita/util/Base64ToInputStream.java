package com.zhita.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.misc.BASE64Decoder;

public class Base64ToInputStream {
		/**
		 * base64转inputStream
		 * @param base64string
		 * @return
		 */
		public  InputStream BaseToInputStream(String base64string){  
		    ByteArrayInputStream stream = null;
			try {
			    BASE64Decoder decoder = new BASE64Decoder(); 
			    byte[] bytes1 = decoder.decodeBuffer(base64string);  
			    stream = new ByteArrayInputStream(bytes1);  
			} catch (Exception e) {
			// TODO: handle exception
			}
		        return stream;  
		    } 

}
