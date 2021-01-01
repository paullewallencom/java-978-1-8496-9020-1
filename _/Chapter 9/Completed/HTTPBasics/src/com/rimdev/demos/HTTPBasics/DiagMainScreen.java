package com.rimdev.demos.HTTPBasics;

import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.WLANInfo;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class DiagMainScreen extends MainScreen 
{
	public DiagMainScreen() 
	{
		if (DeviceInfo.isSimulator())
		{
			add(new LabelField ("This is running in the simulator."));
		}
		
		if(WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED)
		{
			add(new LabelField ("There is a WiFi connection available."));
		}
		if(CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_DIRECT))
		{
			add(new LabelField ("Direct TCP/IP Connections are available (Including WAP)."));        	        	
		}
	
	    if(CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_MDS))
	    {
			add(new LabelField ("Connections via MDS are available."));        	
	    }
	
		ServiceBook sb = ServiceBook.getSB();
		ServiceRecord[] records = sb.findRecordsByCid("WPTCP");
	    
		for (int i = 0; i < records.length; i++) 
		{
			String uid = records[i].getUid().toLowerCase();
			if ( uid.indexOf("wifi") == -1 && uid.indexOf("mms") == -1) 
			{
				add(new LabelField ("A WAP 2.0 connection is available using UID="+records[i].getUid()));    
			}
		}
	}
}

