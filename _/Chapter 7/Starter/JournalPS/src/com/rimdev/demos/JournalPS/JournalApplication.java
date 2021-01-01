package com.rimdev.demos.JournalPS;

import net.rim.device.api.ui.UiApplication;

public class JournalApplication extends UiApplication 
{
	public static void main(String[] args) 
	{
		JournalApplication theApp = new JournalApplication();
		theApp.enterEventDispatcher();
	}
	
	public JournalApplication()
	{
		pushScreen(new JournalMainScreen());
	}

}
