/**
 * 
 */
package com.aks.portScanApp.excception;

/**
 * @author Amit.Sharma
 *
 */
public class ErrorMessageConstants {
	
	public static String defaultErrorMessage = "There seems to be some issue in processing of this request. Please bear with us to come out with more analysis.";
	
	public static String noDataFoundErrorMessage = "No data available for input request";

	public static String resourceNotFoundDeveloperMessage = "Resource you are looking for does not exist at our side. Please do check your request path along with existence of data element.";

	public static String badRequestDeveloperMessage = "Please do check your request path , path variable or parameters definition along with data source existence.";

	public static String defaultSQLDeveloperLevelMessage = "There seems to be some issue while interacting with the source database. Please do check logs corresponding to this transaction.";

}
