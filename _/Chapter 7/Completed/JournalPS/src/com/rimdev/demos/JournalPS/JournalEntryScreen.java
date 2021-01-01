package com.rimdev.demos.JournalPS;

import java.util.Date;
import java.util.Vector;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.AutoTextEditField;
import net.rim.device.api.ui.component.DateField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class JournalEntryScreen extends MainScreen 
{
	protected DateField _EntryDateField = new DateField();
	protected AutoTextEditField _EntryField = new AutoTextEditField();
	protected Vector _EntryList = null;
	
	public JournalEntryScreen(Vector EntryList)
	{
		_EntryList = EntryList;
		setupScreen();
		setTitle("New Journal Entry");
	}
	
	public JournalEntryScreen(JournalEntry Entry)
	{
		setupScreen();

		setTitle("View Journal Entry");
		
		_EntryDateField.setEditable(false);
		_EntryDateField.setDate(Entry.getEntryDate());
		
		_EntryField.setEditable(false);
		_EntryField.setText(Entry.getEntry());
	}
	
	protected void setupScreen()
	{
		_EntryDateField.setLabel("Entry Date:");
		_EntryDateField.setFormat(DateFormat.getInstance(DateFormat.DATETIME_DEFAULT));
		_EntryDateField.setDate(new Date());
		
		add(_EntryDateField);
		add(new SeparatorField());
		
		_EntryField.setMaxSize(4000);
		add(_EntryField);
		
		addMenuItem(_SaveEntryAction);	
	}

	// Menu items ---------------------------------------------------------------
    protected MenuItem _SaveEntryAction = new MenuItem("Save Entry" , 100000, 10)
    {    
        public void run()
        {
        	onSave();
        	UiApplication.getUiApplication().getActiveScreen().close();
        }
    };

	protected boolean onSave()
	{
		JournalEntry Entry = new JournalEntry();
		Entry.setEntryDate(new Date(_EntryDateField.getDate()));
		Entry.setEntry(_EntryField.getText());
		
		_EntryList.addElement(Entry);
		UiApplication.getUiApplication().invokeLater(new Runnable() 
		{
			public void run() 
			{

				JournalMainScreen main = (JournalMainScreen)UiApplication.getUiApplication().getActiveScreen();
				 main._List.setSize( _EntryList.size());
			}
		});
		return true;
	}
}
