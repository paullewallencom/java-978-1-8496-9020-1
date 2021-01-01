package com.rimdev.demos.HTTPBasics;

import com.rimdev.demos.HTTPBasics.ServiceRequestThread;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;

public class HTTPBasicsMainScreen extends MainScreen {
	protected RichTextField _Output = new RichTextField();
	public HTTPBasicsMainScreen() {
		setTitle(new LabelField ("HTTP Basics"));
		_Output.setLabel("Output: ");
		_Output.setMaxSize(4000);
		add(_Output);	
		
		addMenuItem(_GetDataAction);
	}
	
	protected MenuItem _GetDataAction = new MenuItem("GetData" , 100000, 10)
	{    
	    public void run()
	    {
	    	String URL = "http://www.google.com;deviceside=true";
	    	ServiceRequestThread svc = new ServiceRequestThread(URL,(HTTPBasicsMainScreen)UiApplication.getUiApplication().getActiveScreen());
	    	svc.start();
	    }
	};



	public void updateDestination(final String text) {
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run()
			{
				_Output.setText(text);
			}
		});
	}
}
