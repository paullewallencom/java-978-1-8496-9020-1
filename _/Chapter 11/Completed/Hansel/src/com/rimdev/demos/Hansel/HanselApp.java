package com.rimdev.demos.Hansel;

import net.rim.blackberry.api.homescreen.HomeScreen;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.UiApplication;

public class HanselApp extends UiApplication {
	
	public HanselApp(boolean setIcon){
		if (setIcon) {
	         invokeLater(new Runnable() {
	             public void run()  
	             {  
	                  ApplicationManager myApp = 
	                	  ApplicationManager.getApplicationManager();  
	                  boolean inStartup = true;  
	  
					  while (inStartup) {  	                         
						  if (myApp.inStartup()) {
							  try {
								  Thread.sleep(1000);
							  } catch (InterruptedException e) {
								  // TODO Auto-generated catch block
								  e.printStackTrace();
							  }
						  }
					      else {  	                            
					          setIcons();  
					          inStartup = false;  
					      }  
					   }  
	                   //Exit the application.  
	                   System.exit(0);  
	              }  
	          });  
	
		}
		else {
			pushScreen(new HanselMainScreen());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    if ( args != null && args.length > 0 && args[0].equals("Icon") ) {
//			UiApplication theApp = new HanselApp(true);
//			theApp.enterEventDispatcher();
			
			// The commands to HomeScreen can be executed directly on OS 4.2 and greater
	        HomeScreen.updateIcon(Bitmap.getBitmapResource("Icon.PNG"),0);  
	        HomeScreen.setRolloverIcon(Bitmap.getBitmapResource("IconSelected.PNG"),0);  
	
	    }
	    else {
			UiApplication theApp = new HanselApp(false);
			theApp.enterEventDispatcher();
	    }
	}

	private void setIcons() {
		//Set the rollover icons.  
		try {
			HomeScreen.updateIcon(Bitmap.getBitmapResource("Icon.PNG"),0);  
			HomeScreen.setRolloverIcon(Bitmap.getBitmapResource("IconSelected.PNG"),0);
		}
		catch(Exception e){}  
	}

}
