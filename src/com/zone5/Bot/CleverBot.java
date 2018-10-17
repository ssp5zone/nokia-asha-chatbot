package com.zone5.Bot;

import java.io.IOException;
import java.util.Hashtable;

import com.zone5.Connector.Connections;
import com.zone5.Utilities.URLEncoder;
import com.zone5.Utilities.Utils;

/**
 * The cleverbot
 */
public class CleverBot implements ParentBot 
{
	private Hashtable models=null;
	
	private int endIndex;
	private String baseUrl;
	private String serviceUrl;
	
	private StringBuffer baseCookies;
	private StringBuffer sessionCoookies;
	
	private String param1;
	private String param2;
	private String param3;
	//private LinkedHashtable vars;
	
	private Connections connection = null;
	
	public CleverBot(String baseUrl,String serviceUrl,int endIndex)
	{
		models = new Hashtable();
		populateModels();
		
		this.baseUrl=baseUrl;
		this.serviceUrl=serviceUrl;
		this.endIndex=endIndex;
	}
	
	private void populateModels()
	{
		models.put("Generic", "");
	}

	public void initiate() 
	{
		param1="start=y&icognoid=wsf&fno=0&sub=Say&islearning=1&cleanslate=false&stimulus=";
		param2="&icognocheck=AF71393CE00D9126A247DF2F53948E79";
		param3="";

		connection = new Connections();
		sessionCoookies = new StringBuffer();
		baseCookies = new StringBuffer();
		try 
		{
			connection.getResponseCookie(baseUrl, null, baseCookies);
			System.out.println("Base Cookies: "+baseCookies);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public String think(String message) 
	{
		String response = "Not Loaded Properly. Please Reload";
		
		String msgInUrlFormat = URLEncoder.urlEncode(message);
		String finalParam = param1+msgInUrlFormat+param2+param3;
		
		System.out.println("Final Param:"+finalParam);
		
		sessionCoookies.insert(0, baseCookies.toString());
		
		System.out.println("Session Cookie:"+sessionCoookies);
		
		try 
		{
			String rawHTML = connection.getResponseCookie(serviceUrl, finalParam, sessionCoookies);
			
			//System.out.println("Raw HTML: "+rawHTML);
			
			String[] responseValues = Utils.split(rawHTML, '\r');
			
			StringBuffer sb= new StringBuffer();
			
	        sb.append("&sessionid="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 1)));
	           sb.append("&logurl="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 2)));
	           sb.append("&vText8="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 3)));
	           sb.append("&vText7="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 4)));
	           sb.append("&vText6="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 5)));
	           sb.append("&vText5="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 6)));
	           sb.append("&vText4="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 7)));
	           sb.append("&vText3="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 8)));
	           sb.append("&vText2="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 9)));
	           sb.append("&prevref="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 10)));
	            //vars.put("&="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 11))); ??
	           sb.append("&emotionalhistory="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 12)));
	           sb.append("&ttsLocMP3="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 13)));
	           sb.append("&ttsLocTXT="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 14)));
	           sb.append("&ttsLocTXT3="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 15)));
	           sb.append("&ttsText="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 16)));
	           sb.append("&lineRef="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 17)));
	           sb.append("&lineURL="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 18)));
	           sb.append("&linePOST="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 19)));
	           sb.append("&lineChoices="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 20)));
	           sb.append("&lineChoicesAbbrev="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 21)));
	           sb.append("&typingData="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 22)));
	           sb.append("&divert="+URLEncoder.urlEncode(Utils.stringAtIndex(responseValues, 23)));
	           
	       param3=sb.toString();
	       
	       response = Utils.stringAtIndex(responseValues, 0);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return response;
	}

	public void think(PostMan postMan) 
	{
		String response = null;
		response = think(postMan.getMessage());
		postMan.post(response);
	}

	public void closeConnection() 
	{
		connection.closeConnections();
	}

	public Hashtable getModels() 
	{
		return models;
	}

	public void setModel(String modelName) {
		// future use...
	}
}
