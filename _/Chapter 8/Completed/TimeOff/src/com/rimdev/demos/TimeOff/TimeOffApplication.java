package com.rimdev.demos.TimeOff;

import net.rim.device.api.ui.UiApplication;

public class TimeOffApplication extends UiApplication {

	public TimeOffApplication() {
		pushScreen(new TimeOffMainScreen());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UiApplication theApp = new TimeOffApplication();
		theApp.enterEventDispatcher();
	}

}
