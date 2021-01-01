package com.rimdev.demos.JournalSD;

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
}