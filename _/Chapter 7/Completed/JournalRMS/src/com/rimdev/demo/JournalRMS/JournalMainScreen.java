package com.rimdev.demo.JournalRMS;

import java.util.Enumeration;
import java.util.Vector;
import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

public class JournalMainScreen extends MainScreen implements ListFieldCallback
{
	protected Vector _EntryList = new Vector();
	protected ListField _List = new ListField();
	
	public JournalMainScreen()
	{
		setTitle(new LabelField("My Journal"));
		_List.setCallback(this);
		add(_List);
		
		// TODO: Load Data 
		try 
		{
		    LoadEntriesRMS(_EntryList);
		} 
		catch (RecordStoreFullException e) 
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} 
		catch (RecordStoreNotFoundException e) 
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} 
		catch (RecordStoreException e) 
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}	

		_List.setSize(_EntryList.size());
		
		this.addMenuItem(_NewEntryAction);
	}
	
	// Menu items ---------------------------------------------------------------
    protected MenuItem _NewEntryAction = new MenuItem("New Entry" , 100000, 10)
    {    
        public void run()
		{
    		UiApplication.getUiApplication().pushScreen(new JournalEntryScreen(_EntryList));
		}
    };
	
    protected MenuItem _ViewEntryAction = new MenuItem("View Entry" , 100000, 10)
    {    
        public void run()
        {
        	UiApplication.getUiApplication().pushScreen(
        			new JournalEntryScreen((JournalEntry)_EntryList.elementAt(_List.getSelectedIndex())));
        }
    };
    
    public void makeMenu(Menu m, int instance)
    {
		if (! _EntryList.isEmpty())
		{
			m.add(_ViewEntryAction);
		}
		super.makeMenu(m, instance);
    }
    
	public void drawListRow(ListField listField, Graphics graphics, 
			int index, int y, int width) 
	{
		JournalEntry Entry = (JournalEntry) _EntryList.elementAt(index);
	    graphics.drawText( 
	    		DateFormat.getInstance(DateFormat.DATETIME_DEFAULT).formatLocal(Entry.getEntryDate().getTime()),
	    		0, y, 0, 175);
	    graphics.drawText(Entry.getEntry(),180,y,0,width-180);
	}

	public Object get(ListField listField, int index) 
	{
		return _EntryList.elementAt(index);
	}

	public int getPreferredWidth(ListField listField) 
	{
		return Display.getWidth();
	}

	public int indexOfList(ListField listField, String prefix, int start) 
	{
		return -1;
	}
	
	public boolean onClose()
	{
		//TODO: Save data 

		try 
		{
		    SaveEntriesRMS(_EntryList);
		} 
		catch (RecordStoreNotOpenException e) 
		{
		    e.printStackTrace();
		} 
		catch (RecordStoreFullException e) 
		{
		    e.printStackTrace();
		} 
		catch (RecordStoreException e) 
		{
		    e.printStackTrace();
		}
		
		return super.onClose();
	}	
	
	protected void SaveEntriesRMS(Vector EntryList) throws RecordStoreNotOpenException, RecordStoreFullException, RecordStoreException
	{
	    //Delete the existing recordstore if there is one.
	    try 
	    {
	        RecordStore.deleteRecordStore("JournalEntries");
	    } 
	    catch (RecordStoreException e1) 
	    {
	        // Do nothing. Its entirely possible that the recordstore doesn't exist yet
	        // yet and will throw an exception. Just silently ignore it.
	    }
		
	    // create a new one.
	    RecordStore JournalStore = RecordStore.openRecordStore("JournalEntries", true);
		
	    if (JournalStore == null)
	    {
	        //big error! quit now.
	        return;
	    }

	    //TODO: Save the records
	    for (Enumeration e = EntryList.elements(); e.hasMoreElements();)
	    {
	        JournalEntry Entry = (JournalEntry) e.nextElement();
	        byte[] data = Entry.save();
	        if (data != null)
	        {
	            JournalStore.addRecord(data, 0, data.length);
	        }
	    }
		
	    JournalStore.closeRecordStore();
	}
	
	protected void LoadEntriesRMS(Vector EntryList) throws RecordStoreFullException, RecordStoreNotFoundException, RecordStoreException
	{		
	    // create a new one.
	    RecordStore JournalStore = RecordStore.openRecordStore("JournalEntries", true);

	    if (JournalStore == null)
	    {
	        //big error! quit now.
	        return;
	    }		

	    for (RecordEnumeration e = JournalStore.enumerateRecords(null, null, false); e.hasNextElement();)
	    {
	        JournalEntry Entry = new JournalEntry();
	        Entry.load(e.nextRecord());
	        EntryList.addElement(Entry);
	    }

	    JournalStore.closeRecordStore();
	}
}