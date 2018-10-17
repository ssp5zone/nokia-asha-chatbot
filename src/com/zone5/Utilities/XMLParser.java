package com.zone5.Utilities;

import org.kxml.Attribute;
import org.kxml.Xml;
import org.kxml.kdom.Document;
import org.kxml.kdom.Element;
import org.kxml.parser.XmlParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * There were no HTML parsers available for this environment.
 * However, I found an old XML parser written for eariler versions of java
 * and it works here.
 * Since HTML is merely a subset of XML we write a small wrapper on top of it. 
 */
public class XMLParser 
{
	private XmlParser parser = null;
	private Document document = null;
	
	private String xStr = null;
	private boolean file=false;
	
	public XMLParser(boolean isFile)
	{
		this.file=isFile;
	}
	
	public void parse(String xStr)
	{
		this.xStr=xStr;
		
		Reader reader = null;
		
		if(isFile())
			reader = getStreamFromFile(xStr);
		else
			reader = getStreamFromString(xStr);
		
		try 
		{
			parser = new XmlParser(reader);
			document = new Document();
			document.parse(parser);
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			parser = null;
			document = null;
		}
		
	}
	
	public Element getRoot()
	{
		
		Element root = null;
		if(document!=null)
			root = document.getRootElement();				
		return root;
	}
	
	public String getRootAttribute(String attrName)
	{
		String attrValue = null;
		
		Element root = getRoot();
		
		if(root!=null)
		{
			Attribute attr = root.getAttribute(attrName);
			attrValue=attr.getValue();
		}
		
		return attrValue;
	}
	
	public Element getElementByTagName(String tagName)
	{
		Element element = null;
		
		Element root = getRoot();
		
		if(root!=null)
		{
			  int child_count = root.getChildCount();

			  for (int i = 0; i < child_count ; i++ ) 
			  {
				  if (root.getType(i) != Xml.ELEMENT) 
				  {
					  continue;
				  }

				  Element e = root.getElement(i);
				  if (!e.getName().equals(tagName)) 
				  {
					  continue;
				  }
				  else
					  element = e;
			  }

		}
		return element;
	}
	
	private Reader getStreamFromString(String xmlString)
	{
		InputStreamReader streamReader = null;
		
		try 
		{
			InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
			streamReader = new InputStreamReader(inputStream);			
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		
		return streamReader;
		
	}
	
	private Reader getStreamFromFile(String filePath)
	{
		InputStreamReader streamReader = null;
		
		try 
		{
			InputStream inputStream = this.getClass().getResourceAsStream(filePath);
			streamReader = new InputStreamReader(inputStream);			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return streamReader;
		
	}
	
	public static void main(String[] args)
	{
		XMLParser xmlParser = new XMLParser(false);
		
		/*String url = new String("http://www.pandorabots.com/pandora/talk-xml?botid=b0dafd24ee35a477&input=Hello");
		Connections connection = new Connections();
		String rawHTML = connection.getResponse(url);*/
		
		String rawHTML = "<result status=\"0\" botid=\"b0dafd24ee35a477\" custid=\"e84ed886de98fb4f\">\n" + 
				"<input>Hello</input>\n" +
				"<that>Hello! Have you a question for me?</that>\n" +
				"</result>";
		
		System.out.println(rawHTML);
		
		xmlParser.parse(rawHTML);
		
		System.out.println("RootName: "+xmlParser.getRoot().getName());
		System.out.println("CustID attribute:" + xmlParser.getRootAttribute("custid"));
		System.out.println("Tag Text: "+ xmlParser.getElementByTagName("that").getText());
		
	}
	
	public String getxStr() {
		return xStr;
	}
	
	public boolean isFile() {
		return file;
	}
}
