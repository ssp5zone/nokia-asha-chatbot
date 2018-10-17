package com.zone5.Connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 * A raw, cut-shorted headerless browser that can talk to web servers.
 * It was just named Connections as it was written using HTTP Connections layer.
 * No it is not a browser, it just mimics bare minimum capabilities of one.
 */
public class  Connections
{
	private HttpConnection httpConnection = null;
	private InputStream inputStream = null;
		
	StringBuffer sb = null;
	
	public Connections()
	{
		sb = new StringBuffer();
	}

	public void connectHTTP(String url, String mode)
	{
		try 
		{
			this.setHttpConnection((HttpConnection)Connector.open(url,Connector.READ_WRITE, true));
			this.getHttpConnection().setRequestMethod(mode);
			this.getHttpConnection().setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; " +
					"WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public void connectHTTP(String url)
	{
		connectHTTP(url,HttpConnection.GET);
	}
	
	public String getResponseCookie(String address,String finalParam,StringBuffer cookies) throws IOException
	{
		sb = new StringBuffer();		
		connectHTTP(address, HttpConnection.POST);
		
		if(cookies!=null && cookies.length()>0)
		{
			getHttpConnection().setRequestProperty("Cookie", cookies.toString());
		}
		
		if(finalParam!=null && !finalParam.equals(""))
		{
			OutputStreamWriter osw = new OutputStreamWriter(getHttpConnection().openOutputStream());
			osw.write(finalParam);
            osw.flush();
            osw.close();
		}
		
		cookies.delete(0, cookies.length());
		
		int fields=0;		
		while(getHttpConnection().getHeaderField(fields)!=null)
		{
			
			if(getHttpConnection().getHeaderFieldKey(fields).equalsIgnoreCase("Set-Cookie"))
			{
				String cookieStream = getHttpConnection().getHeaderField(fields);
				System.out.println("Recieved Cookie data-"+fields+": "+cookieStream);
				cookies.append(cookieStream);
				cookies.append(";");
			}
			
			fields++;
		}
		
	   setInputStream(getHttpConnection().openInputStream());

	   int ch = 0;
	  
	   while ((ch = getInputStream().read()) != -1) 
	   {
		  sb.append((char)ch);
	   }
		  
		
		return sb.toString();
		
	}
	
	public String getResponse(String address)
	{
		if(getHttpConnection()==null)
			connectHTTP(address);
		
		if(getInputStream()==null)
		{
			try 
			{
				setInputStream(getHttpConnection().openInputStream());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		try 
		{
		  
		 int ch = 0;
		  
		   while ((ch = getInputStream().read()) != -1) 
		  {
			  sb.append((char)ch);
	      }
		  
		}
		catch (IOException x)
		{
			x.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public String getResponseForce(String address)
	{
		sb = new StringBuffer();
		
		connectHTTP(address);
		
		
			try 
			{
				setInputStream(getHttpConnection().openInputStream());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		
		
		try 
		{
		  
		 int ch = 0;
		  
		   while ((ch = getInputStream().read()) != -1) 
		  {
			  sb.append((char)ch);
	      }
		  
		}
		catch (IOException x)
		{
			x.printStackTrace();
		}
		
		return sb.toString();
	}
	
	
	public void closeConnections()
	{
		try     
	     {
			if(this.getInputStream()!=null)
				getInputStream().close();
			if(this.getHttpConnection()!=null)
				getHttpConnection().close();
	     } 
	     catch (IOException x)
	     {
	          x.printStackTrace();
	     }
		
	}
	
	private HttpConnection getHttpConnection() {
		return httpConnection;
	}

	private void setHttpConnection(HttpConnection httpConnection) {
		this.httpConnection = httpConnection;
	}
	
	private InputStream getInputStream() {
		return inputStream;
	}

	private void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
