package com.zone5.Utilities; 

/**
 * J2ME is a very small subset of Java and does not even understand cookies.
 * No fret cookies are round, sweet and simple. 
 */

import java.lang.String;
import java.lang.IndexOutOfBoundsException;
import java.lang.ArrayIndexOutOfBoundsException;

/**
 * Parse a Cookie: header into individual tokens according to RFC 2109.
 */
public class CookieTokenizer
{
    /**
     * Upper bound on the number of cookie tokens to accept.  The limit is
     * based on the 4 different attributes (4.3.4) and the 20 cookie minimum
     * (6.3) given in RFC 2109 multiplied by 2 to accomodate the 2 tokens in
     * each name=value pair ("JSESSIONID=1234" is 2 tokens).
     */
    private static final int MAX_COOKIE_TOKENS = 4 * 20 * 2;

    /**
     * Array of cookie tokens.  Even indices contain name tokens while odd
     * indices contain value tokens (or null).
     */
    private String tokens[] = new String[MAX_COOKIE_TOKENS];

    /**
     * Number of cookie tokens currently in the tokens[] array.
     */
    private int numTokens = 0;

    /**
     * Parse a name=value pair from the Cookie: header.
     *
     * @param cookies The Cookie: header to parse
     * @param beginIndex The index in cookies to begin parsing from, inclusive
     */
    private int parseNameValue(String cookies, int beginIndex) {
        int length = cookies.length();
        int index = beginIndex;

        while (index < length) {
            switch (cookies.charAt(index)) {
            case ';':
            case ',':
                // Found end of name token without value
                tokens[numTokens] = cookies.substring(beginIndex, index).trim();
                if (tokens[numTokens].length() > 0) {
                    numTokens++;
                    tokens[numTokens] = null;
                    numTokens++;
                }
                return index + 1;

            case '=':
                // Found end of name token with value
                tokens[numTokens] = cookies.substring(beginIndex, index).trim();
                numTokens++;
                return parseValue(cookies, index + 1);

            case '"':
                // Skip past quoted span
                do index++; while (cookies.charAt(index) != '"');
                break;
            }

            index++;
        }

        if (index > beginIndex) {
            // Found end of name token without value
            tokens[numTokens] = cookies.substring(beginIndex, index).trim();
            if (tokens[numTokens].length() > 0) {
                numTokens++;
                tokens[numTokens] = null;
                numTokens++;
            }
        }

        return index;
    }

    /**
     * Parse the name=value tokens from a Cookie: header.
     *
     * @param cookies The Cookie: header to parse
     */
    public int tokenize(String cookies) {
        numTokens = 0;

        if (cookies != null) {
            try {
                // Advance through cookies, parsing name=value pairs
                int length = cookies.length();
                int index = 0;
                while (index < length)
                    index = parseNameValue(cookies, index);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                // Filled up the tokens[] array
            }
            catch (IndexOutOfBoundsException e) {
                // Walked off the end of the cookies header
            }
        }

        return numTokens;
    }

    /**
     * Return the number of cookie tokens parsed from the Cookie: header.
     */
    public int getNumTokens() {
        return numTokens;
    }

    /**
     * Returns a given cookie token from the Cookie: header.
     *
     * @param index The index of the cookie token to return
     */
    public String tokenAt(int index) {
        return tokens[index];
    }

    public String[] allTokens()
    {
    	return tokens;
    }
    
    /**
     * Parse the value token from a name=value pair.
     *
     * @param cookies The Cookie: header to parse
     * @param beginIndex The index in cookies to begin parsing from, inclusive
     */
    private int parseValue(String cookies, int beginIndex) {
        int length = cookies.length();
        int index = beginIndex;

        while (index < length) {
            switch (cookies.charAt(index)) {
            case ';':
            case ',':
                // Found end of value token
                tokens[numTokens] = cookies.substring(beginIndex, index).trim();
                numTokens++;
                return index + 1;

            case '"':
                // Skip past quoted span
                do index++; while (cookies.charAt(index) != '"');
                break;
            }

            index++;
        }

        // Found end of value token
        tokens[numTokens] = cookies.substring(beginIndex, index).trim();
        numTokens++;

        return index;
    }
}