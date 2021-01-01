package com.rimdev.demos.HTTPBasics;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;



public class ServiceRequestThread extends Thread {
	protected String _URL;
	protected HTTPBasicsMainScreen _Dest = null;

	public ServiceRequestThread(String URL,HTTPBasicsMainScreen screen) {
		super();
		_Dest = screen;
		_URL = URL;
	}

public void run(){
	try {
		HttpConnection conn = (HttpConnection)Connector.open(_URL);
		conn.setRequestMethod(HttpConnection.GET);
		int responseCode = conn.getResponseCode();
		if (responseCode == HttpConnection.HTTP_OK) {
			InputStream data = conn.openInputStream();
			StringBuffer raw = new StringBuffer();
			byte[] buf = new byte[4096];
			int nRead = data.read(buf);
			while (nRead > 0) {
				raw.append(new String(buf,0,nRead));
				nRead = data.read(buf);
			}
			_Dest.updateDestination(raw.toString());
		}
		else
		{
			_Dest.updateDestination("responseCode="+Integer.toString(responseCode));
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		_Dest.updateDestination("Exception:"+e.toString());
	}
}
}
