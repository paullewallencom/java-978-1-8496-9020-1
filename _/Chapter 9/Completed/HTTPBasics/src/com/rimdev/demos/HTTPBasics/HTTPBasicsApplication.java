package com.rimdev.demos.HTTPBasics;

import net.rim.device.api.ui.UiApplication;

public class HTTPBasicsApplication extends UiApplication {
	public HTTPBasicsApplication() {
		pushScreen( new HTTPBasicsMainScreen());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UiApplication theApp = new HTTPBasicsApplication();
		theApp.enterEventDispatcher();
	}

}
