package com.rimdev.demos.JournalPS;

import java.util.Date;
import net.rim.device.api.util.Persistable;

public class JournalEntry implements Persistable 
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
