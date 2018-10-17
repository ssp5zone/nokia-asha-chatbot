package com.zone5.Utilities;

import java.util.Vector;

/**
 * A set of common utilities that everyone can use.
 */
public class Utils 
{
	// Splits a string into an array given a seperator.
	// Basically what we know as String.split() in J2SE
	public final static String[] split( String str, char separatorChar ) {
	      if ( str == null ) {
	         return null;
	      }
	      int       len    = str.length();
	      if ( len == 0 ) {
	         return null;
	      }
	      Vector    list   = new Vector();
	      int       i      = 0;
	      int       start  = 0;
	      boolean   match  = false;
	      while ( i < len ) {
	         if ( str.charAt( i ) == separatorChar ) {
	            if ( match ) {
	               list.addElement( str.substring( start, i ).trim() );
	               match = false;
	            }
	            start = ++i;
	            continue;
	         }
	         match = true;
	         i++;
	      }
	      if ( match ) {
	         list.addElement( str.substring( start, i ).trim() );
	      }
	      String[]  arr    = new String[list.size()];
	      list.copyInto( arr );
	      return arr;
	   }
	
	public static String stringAtIndex(String[] strings, int index) {
        if (index >= strings.length) return "";
        return strings[index];
    }
	
}
