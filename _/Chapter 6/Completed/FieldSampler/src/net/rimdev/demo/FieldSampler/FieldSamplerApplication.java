package net.rimdev.demo.FieldSampler;

import net.rim.device.api.ui.UiApplication;

public class FieldSamplerApplication extends UiApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		FieldSamplerApplication theApp = new FieldSamplerApplication();
		theApp.enterEventDispatcher();
	}
	public FieldSamplerApplication()
	{
		pushScreen(new FieldSamplerScreen());
	}

}
