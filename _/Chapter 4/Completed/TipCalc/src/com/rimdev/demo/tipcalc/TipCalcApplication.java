package com.rimdev.demo.tipcalc;

import net.rim.device.api.ui.UiApplication;

public class TipCalcApplication extends UiApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        // Create a new instance of the application.
		TipCalcApplication theApp = new TipCalcApplication();
        
        // To make the application enter the event thread and start processing messages, 
        // we invoke the enterEventDispatcher() method.
        theApp.enterEventDispatcher();
	}
	
    private TipCalcApplication()
    {
        // Push the main screen instance onto the UI stack for rendering.
        pushScreen(new TipCalcMainScreen());
    }    


}
