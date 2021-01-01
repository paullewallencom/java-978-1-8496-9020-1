package com.rimdev.demos.HTTPBasics;

import java.io.ByteArrayInputStream;

import com.rimdev.demos.HTTPBasics.ServiceRequestThread;

import net.rim.blackberry.api.browser.URLEncodedPostData;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.xml.jaxp.SAXParserImpl;

public class HTTPBasicsMainScreen extends MainScreen {
	protected RichTextField _Output = new RichTextField();
	protected EditField _CopyString = new EditField();
	
	public HTTPBasicsMainScreen() 
	{
		setTitle(new LabelField ("HTTP Basics"));
		
		_CopyString.setLabel("Copy Source: ");
		_CopyString.setMaxSize(50);
		add(_CopyString);
		
		_Output.setLabel("Output: ");
		_Output.setMaxSize(4000);
		add(_Output);	
		
		addMenuItem(_GetDataAction);
		addMenuItem(_PostDataAction);
		addMenuItem(_DisplayDiagAction);
	}
	
	protected MenuItem _GetDataAction = new MenuItem("GetData" , 100000, 10)
	{    
	    public void run()
	    {
	    	String URL="http://localhost:2997/CopycatWebServiceCSharp/Service.asmx/HelloWorld;deviceside=true";
	    	ServiceRequestThread svc = new ServiceRequestThread(URL,(HTTPBasicsMainScreen)UiApplication.getUiApplication().getActiveScreen());
	    	svc.start();
	    }
	};

	protected MenuItem _PostDataAction = new MenuItem("PostData" , 100000, 10)
	{    
	    public void run()
	    {
			URLEncodedPostData oPostData = new URLEncodedPostData(URLEncodedPostData.DEFAULT_CHARSET, false);
			oPostData.append("Value",_CopyString.getText());
	
	    	String URL = "http://localhost:2997/CopycatWebServiceCSharp/Service.asmx/CopyMe;deviceside=true";
	    	ServiceRequestThread svc = new ServiceRequestThread(URL,(HTTPBasicsMainScreen)UiApplication.getUiApplication().getActiveScreen());
	    	svc.setPOSTData(oPostData);
	    	svc.start();
	    }
	};

	protected MenuItem _DisplayDiagAction = new MenuItem("Display Diagnostics" , 90000, 10)
	{    
	    public void run()
	    {
	    	UiApplication.getUiApplication().pushScreen(new DiagMainScreen());
	    }
	};


	public void updateDestination(final String text) {
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run()
			{
				//_Output.setText(text);
				
				SAXParserImpl parser = new SAXParserImpl();
				CopyMeHandler handler = new CopyMeHandler();
				ByteArrayInputStream inputStream = new ByteArrayInputStream(text.getBytes());
				try { 
					parser.parse( inputStream, handler );
				} catch ( Exception e ) {
				    _Output.setText( "Unable to parse response.");
				    return;
				}      
				_Output.setText( handler.copymeResponse );
			}
		});
	}
}
