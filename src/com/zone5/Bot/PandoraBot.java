package com.zone5.Bot;

import java.util.Hashtable;

import com.zone5.Connector.Connections;
import com.zone5.Utilities.URLEncoder;
import com.zone5.Utilities.XMLParser;

/**
 * The pandorabot
 */
public class PandoraBot implements ParentBot 
{
	private Hashtable models=null;
	private String botid = null;
	private String custid = null;
	
	private final String URL = "http://www.pandorabots.com/pandora/talk-xml?botid=";
	private String baseURL = null;
	private String sessionURL = null;

	/*private boolean thinking = false;
	private boolean loading = false;
	private Thread botThread;*/
	
	private Connections connection = null;
	private XMLParser xmlParser = null;
	
	PandoraBot()
	{
		models = new Hashtable();
		populateModels();
	}
	
	private void populateModels()
	{
		models.put("Generic", "b0dafd24ee35a477");
		models.put("Einstein", "ea77c0200e365cfb");
		models.put("Professor", "935a0a567e34523c");
		models.put("Ayame", "cd44746d1e3755a1");
		models.put("Amy", "878ba74dfe34402c");
	}

	public Hashtable getModels() 
	{
		return models;
	}

	public void setModel(String modelName) 
	{
		this.botid = (String)this.models.get(modelName);
		setBaseURL(botid);
	}

	public void initiate() 
	{
		connection = new Connections();
		xmlParser = new XMLParser(false);
		
		String rawHTML;
		String custid;
			
		try
		{
			setSessionURL(null);
						
			rawHTML = sendHttpGET("%20");   //Empty to get custid
			System.out.println(rawHTML);
			xmlParser.parse(rawHTML);
			custid = xmlParser.getRootAttribute("custid");
			
			this.custid = custid;
			setSessionURL(this.custid);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	
	public String think(String message) 
	{
		String response = "I am not Loaded properly.\nPlease Reload.";
		System.out.println("Session Url:"+sessionURL);
		try
		{
			String msgInUrlFormat = URLEncoder.urlEncode(message);
			System.out.println("Encoded msg:"+msgInUrlFormat);
			String rawHTML = sendHttpGET(msgInUrlFormat);
			System.out.println(rawHTML);
			xmlParser.parse(rawHTML);
			response = xmlParser.getElementByTagName("that").getText();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Response: "+response);
		
		if(response.indexOf("<")!=-1)
		{
			response=clearAds(response);
			System.out.println("Response AdFree: "+response);
		}
			
		return response;
	}
	
	public void think(PostMan postMan) 
	{
		String response = null;
		response = think(postMan.getMessage());
		postMan.post(response);
	}
	
	private String clearAds(String input)
	{
		String output = input;
		
		int adStart = input.indexOf("<");
		
		if(adStart!=-1)
		{
			String reverse = new StringBuffer(input).reverse().toString();
			int adEnd = reverse.indexOf(">");
			if(adEnd!=-1)
			{
				adEnd=input.length()-adEnd;
				if(adEnd>adStart)
				{
					output = input.substring(0,adStart)+input.substring(adEnd);					
				}
			}
				
		}
		
		return output;
	}
	
	
	private String sendHttpGET(String input)
	{
		String rawHTML = null;
		
		if(connection!=null)
		{
			rawHTML = connection.getResponseForce(getSessionURL()+input);
		}
		
		return rawHTML;
	}
	
	private String getSessionURL() {
		return sessionURL;
	}

	private void setSessionURL(String custid) 
	{
		if(custid != null)
			this.sessionURL = getBaseURL()+"&custid="+custid+"&input=";
		else
			this.sessionURL = getBaseURL()+"&input=";
	}
	
	private String getBaseURL() 
	{
		return baseURL;
	}

	private void setBaseURL(String botid) 
	{
		this.baseURL = URL+botid;
	}

	public void closeConnection() 
	{
		connection.closeConnections();
	}

}
