package com.rimdev.demos.JournalSD;

import net.rim.device.api.ui.UiApplication;

public class JournalApplication extends UiApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JournalApplication theApp = new JournalApplication();
		theApp.enterEventDispatcher();
	}
	
	public JournalApplication()
	{
		pushScreen(new JournalMainScreen());
	}

}
