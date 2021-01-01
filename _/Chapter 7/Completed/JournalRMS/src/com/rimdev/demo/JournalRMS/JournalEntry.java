package com.rimdev.demo.JournalRMS;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

public class JournalEntry 
{
	public JournalEntry()
	{
	}
	
	protected long _EntryDate;
	protected String _Entry;
	
	public Date getEntryDate()
	{
		return new Date(_EntryDate);
	}
	
	public String getEntry()
	{
		return _Entry;
	}
	
	public void setEntry(String Entry)
	{
		_Entry = Entry;
	}
	
	public void setEntryDate(Date EntryDate)
	{
		_EntryDate = EntryDate.getTime();
	}
	
	public byte[] save() 
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    DataOutputStream outputStream = new DataOutputStream(baos);
	    byte[] data = null;
	    try 
	    {
	        // Add the Entry Date as a long integer.
	        outputStream.writeLong(getEntryDate().getTime());
	        // Then add the entry itself
	        outputStream.writeUTF(getEntry());
			
	        // Extract the byte array
	        data = baos.toByteArray();			
	    }
	    catch (IOException ioe) 
	    {
	        System.out.println(ioe);
	        ioe.printStackTrace();
	    }
	    return data;
	}
	public void load(byte[] data) 
	{
	    ByteArrayInputStream bais = new ByteArrayInputStream(data);
	    DataInputStream inputStream = new DataInputStream(bais);
	    try 
	    {
	        setEntryDate(new Date(inputStream.readLong()));
	        setEntry(inputStream.readUTF());
	    } 
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    }
	}


}
