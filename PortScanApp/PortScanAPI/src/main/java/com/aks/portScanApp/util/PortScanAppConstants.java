/**
 * 
 */
package com.aks.portScanApp.util;

import java.util.regex.Pattern;

/**
 * @author Amit.Sharma
 *
 */
public class PortScanAppConstants {
	
	public static final Pattern IP_ADD_PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	public static final String FORWARD_SLASH = "/";
	
	public static final String NEW_LINE = "\n";
	
	public static final String SYSTEM_USER = "SYSTEM"; 
	
	public static final String BLANK_STRING = ""; 
	
	public static final String HYPHEN_STRING ="-";
	
	public static final String COLON_STRING =":";

	public static final String COMMA_STRING =",";

	public static final String SPACE =" ";
	
}
