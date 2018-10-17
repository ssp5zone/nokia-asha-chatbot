package com.zone5.Utilities;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

/**
 * Yup, you guessed it right. Java Micro Edition - the cut short version of Standard edition
 * does not Linked Hask Tables, so we create our own implimentation. Simple Times!!
 */
public class LinkedHashtable 
{
	private Hashtable indexTable;
	private Hashtable dataTable;
	
	private int currentIndex;
	
	public LinkedHashtable()
	{
		indexTable = new Hashtable();
		dataTable = new Hashtable();
		currentIndex=0;
	}
	
	public void put(Object key, Object value)
	{
		if(!dataTable.containsKey(key))
		{
			this.indexTable.put(new Integer(currentIndex++), key);
		}
		this.dataTable.put(key, value);
	}
	
	public Enumeration keys()
	{
		ExEnum e = new ExEnum();

		for(int i=currentIndex-1;i>=0;i--)
			e.addElement(getKeyAtIndex(i));
		
		return e;
	}
	
	public Enumeration elements()
	{
		ExEnum e = new ExEnum();

		for(int i=currentIndex-1;i>=0;i--)
			e.addElement(getValueAtIndex(i));
		
		return e;
	}

	public int size()
	{
		return currentIndex;
	}
	
	public Object get(Object key)
	{
		return dataTable.get(key);
	}
	
	public Object getValueAtIndex(int index)
	{
		return dataTable.get(getKeyAtIndex(index));
	}
	
	public Object getKeyAtIndex(int index)
	{
		return indexTable.get(new Integer(index));
	}
}

class ExEnum implements Enumeration
{
	private Stack internalStack;
	
	ExEnum()
	{
		internalStack=new Stack();
	}

	public void addElement(Object obj)
	{
		internalStack.push(obj);
	}
	
	public boolean hasMoreElements() 
	{
		return !internalStack.isEmpty();
	}

	public Object nextElement() 
	{
		return internalStack.pop();
	}
	
}