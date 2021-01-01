package com.rimdev.demos.JournalSD;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;


import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;

public class JournalMainScreen extends MainScreen implements ListFieldCallback
{

	protected Vector _EntryList = new Vector();
	protected ListField _List = new ListField();
	protected String _URL = "file:///SDCard/BlackBerry/Journal.data";
	protected String _SDCardURL = "file:///SDCard";
	protected String _BlackBerryURL = "file:///SDCard/BlackBerry";
	
	public JournalMainScreen()
	{
		setTitle(new LabelField("My Journal1"));
		_List.setCallback(this);
		add(_List);
		
		if (CheckPathExists(_SDCardURL))
		{
			if (CheckPathExists(_URL))
			{
				LoadData(_URL);
			}
		}
		
		_List.setSize(_EntryList.size());
		
		this.addMenuItem(_NewEntryAction);
	}
	
	protected boolean CheckPathExists(String path)
	{
		boolean return_value = false;
		try 
		{
			FileConnection fileConn = (FileConnection) Connector.open(path);
			if (fileConn != null)
			{
				if (fileConn.exists())
				{
					return_value = true;
				}
			}
			fileConn.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return return_value;
	}
	
	protected void LoadData(String path)
	{
		// TODO: Load Data 
		try 
		{
			FileConnection _JournalFile = (FileConnection) Connector.open(path);
			if (_JournalFile != null)
			{
				JournalEntry newEntry;
				DataInputStream istream = _JournalFile.openDataInputStream();
				int count = istream.readInt();
				for (int i=0; i<count; i++)
				{
					newEntry = new JournalEntry();
					newEntry.load(istream);
					_EntryList.addElement(newEntry);
				}
				istream.close();
			}
			_JournalFile.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        	UiApplication.getUiApplication().pushScreen(new JournalEntryScreen((JournalEntry)_EntryList.elementAt(_List.getSelectedIndex())));
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
    
	public void drawListRow(ListField listField, Graphics graphics, int index,
			int y, int width) 
	{
		JournalEntry Entry = (JournalEntry) _EntryList.elementAt(index);
	    graphics.drawText( DateFormat.getInstance(DateFormat.DATETIME_DEFAULT).formatLocal(Entry.getEntryDate().getTime()), 0, y, 0, 175);
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
		if (CheckPathExists(_SDCardURL))
		{
			try 
			{
				FileConnection _JournalFile = (FileConnection) Connector.open(_BlackBerryURL,Connector.READ_WRITE);
				if (!_JournalFile.exists())
				{
					_JournalFile.create();
				}
				_JournalFile.close();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SaveData(_URL);
		}
		else
		{
			Dialog.alert("No SDCard present. Save failed.");
		}
		return super.onClose();
	}
	protected void SaveData(String path)
	{
		try 
		{
			FileConnection _JournalFile = (FileConnection) Connector.open(path,Connector.READ_WRITE);
			if (_JournalFile != null) 
			{
				if (!_JournalFile.exists())
				{
					_JournalFile.create();
				}
				DataOutputStream ostream = _JournalFile.openDataOutputStream();
				ostream.writeInt(_EntryList.size());
		
				for (Enumeration e = _EntryList.elements(); e.hasMoreElements();)
				{
					JournalEntry Entry = (JournalEntry) e.nextElement();
					Entry.save(ostream);
				}
				ostream.flush();
				ostream.close();
			}
			_JournalFile.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

