package com.rimdev.demos.HTTPBasics;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.blackberry.api.browser.URLEncodedPostData;



public class ServiceRequestThread extends Thread {
	protected String _URL;
	protected HTTPBasicsMainScreen _Dest = null;

	public ServiceRequestThread(String URL,HTTPBasicsMainScreen screen) {
		super();
		_Dest = screen;
		_URL = URL;
	}
	
protected URLEncodedPostData _PostData = null;
public void setPOSTData(URLEncodedPostData data)
{
	_PostData = data;
}
	
	public void run(){
		HttpConnection conn = null;
		try {
			conn = (HttpConnection)Connector.open(_URL,Connector.READ_WRITE);
			if (_PostData != null) {
				conn.setRequestMethod(HttpConnection.POST);
				conn.setRequestProperty("Content-type","application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", Integer.toString(_PostData.size()));
				
				OutputStream strmOut = conn.openOutputStream();
				strmOut.write(_PostData.getBytes());
			
				strmOut.close();
			}
			else {
				conn.setRequestMethod(HttpConnection.GET);
			}
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
				data.close();
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
		finally
		{
			if (conn != null){
				try {
					conn.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
