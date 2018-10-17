package com.zone5.Bot;

/**
 * All bots have their own setting.
 * This guy provides a contract so as everyone maintains
 * a standard way delivering and retreiving the data
 */
public interface PostMan 
{
	public void post(String message);
	public String getMessage();
}
