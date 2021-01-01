package com.rimdev.demos.HTTPBasics;

import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;

public class HTTPBasicsMainScreen extends MainScreen {
	protected RichTextField _Output = new RichTextField();
	public HTTPBasicsMainScreen() {
		setTitle(new LabelField ("HTTP Basics"));
		_Output.setLabel("Output");
		_Output.setMaxSize(4000);
		add(_Output);		
	}

}
