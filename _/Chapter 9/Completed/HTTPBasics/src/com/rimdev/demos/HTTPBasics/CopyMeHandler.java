package com.rimdev.demos.HTTPBasics;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class CopyMeHandler extends DefaultHandler {
	private String currentElement;
	public String copymeResponse;
	
	public void startElement(String uri, String localName, String qName, Attributes attributes){
		currentElement = localName;
	}
	    
	public void characters(char[] ch, int start, int length) {  
	    if ( currentElement.equals( "string" ) ){
	        copymeResponse = new String( ch, start, length );
	    }
	}
}
