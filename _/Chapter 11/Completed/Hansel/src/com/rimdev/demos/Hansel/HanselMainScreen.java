package com.rimdev.demos.Hansel;

import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;

import net.rim.device.api.i18n.ResourceBundle;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;

public class HanselMainScreen extends MainScreen implements LocationListener,HanselResource{
	protected Criteria _CellCriteria = new Criteria();
	protected Criteria _AGPSCriteria = new Criteria();
	protected Criteria _AutonomousGPSCriteria = new Criteria();
	protected Criteria _AGPS_AutonomousGPSCriteria = new Criteria();
	
	protected ObjectChoiceField _AccuracyField;
	protected String[] AccuracyChoices = {"Cell Site","AGPS","Autonomous"};
	protected RichTextField _CurrentCoords;
	
	protected boolean _Started = false;
	protected LocationProvider _CurrentProvider = null;
	protected boolean _GPSSupport = false;
	
	protected CalcDistanceLocationListener _CalcListener = null;
	protected RichTextField _EndLocation;
	protected RichTextField _StartLocation;
	protected RichTextField _Distance;
	protected RichTextField _Bearings;
	ResourceBundle _resources;
	
	public HanselMainScreen() {
		// Set up the Criteria objects for each type.
		_CellCriteria.setHorizontalAccuracy(Criteria.NO_REQUIREMENT);
		_CellCriteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);
		_CellCriteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_LOW);
		_CellCriteria.setCostAllowed(true);
		
		_AGPSCriteria.setHorizontalAccuracy(Criteria.NO_REQUIREMENT);
		_AGPSCriteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);
		_AGPSCriteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_MEDIUM);
		_AGPSCriteria.setCostAllowed(true);
		
		_AutonomousGPSCriteria.setHorizontalAccuracy(50);
		_AutonomousGPSCriteria.setVerticalAccuracy(50);
		_AutonomousGPSCriteria.setPreferredPowerConsumption(Criteria.NO_REQUIREMENT);
		_AutonomousGPSCriteria.setCostAllowed(false);
		
	    _resources = ResourceBundle.getBundle(BUNDLE_ID, BUNDLE_NAME);

		
		setTitle(new LabelField ("Hansel"));
		
		try {
			if (LocationProvider.getInstance(null) != null) {
				_GPSSupport = true;
			}
		} catch (LocationException e) { }
		if (_GPSSupport)
		{
			_AccuracyField = new ObjectChoiceField("Accuracy", AccuracyChoices);
			_AccuracyField.setSelectedIndex(2);
			add(_AccuracyField);
			
			_CurrentCoords = new RichTextField();
			add(_CurrentCoords);
			
			_StartLocation = new RichTextField();
			_StartLocation.setLabel("Start: ");
			add(_StartLocation);
			_EndLocation = new RichTextField();
			_EndLocation.setLabel("End: ");
			add(_EndLocation);
			_Distance = new RichTextField();
			add(_Distance);
			_Bearings = new RichTextField();
			add(_Bearings);
		}
		else
		{	
			add (new LabelField (_resources.getString(SCREEN_NO_GPS_SUPPORT)));   //"No GPS Support is available."));
		}
	}
	
	public void makeMenu (Menu m, int context){
		if (_GPSSupport) {
			if (_Started){
				m.add(_StopAction);
			}
			else {
				m.add(_StartAction);
			}
			m.add(_ResetAction);
			if (_CalcListener == null)
			{
				m.add(_GetStartAction);
			}
			else
			{
				m.add(_FindStartAction);
			}
		}
		super.makeMenu(m, context);
	}
	
	protected MenuItem _StartAction = new MenuItem("Start Logging" , 100000, 10)
	{    
	    public void run()
	    {
			Criteria selectedCriteria = null;
			switch (_AccuracyField.getSelectedIndex())
			{
			case 0:
				selectedCriteria = _CellCriteria;
				break;
			case 1:
				selectedCriteria = _AGPSCriteria;
				break;
			case 2:
				selectedCriteria = _AutonomousGPSCriteria;
				break;
			}
			try {
				_CurrentProvider = LocationProvider.getInstance(selectedCriteria);
				_CurrentProvider.setLocationListener(HanselMainScreen.this, 1, 1, 1);
			} catch (LocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (_CurrentProvider !=null)
			{
				_Started= true;
			}
	    }
	};
	
	protected MenuItem _StopAction = new MenuItem("Stop Logging" , 100000, 10)
	{    
	    public void run()
	    {
	    	_CurrentProvider.reset();
			_CurrentProvider.setLocationListener(null, 1, 1, 1);
			_CurrentProvider=null;
			_Started = false;
	
	    }
	};
	
	protected MenuItem _ResetAction = new MenuItem("Clear Start Location" , 100000, 10)
	{    
	    public void run()
	    {
	    	if (_CalcListener != null)
	    	{
	    		_CalcListener.reset();
	    		_CalcListener = null;
	    		_Distance.setText("");
	    		_Bearings.setText("");
	    		_StartLocation.setText("");
	    		_EndLocation.setText("");
	    		
	    	}
	    }
	};
	
	protected MenuItem _GetStartAction = new MenuItem("Get Start Location" , 100000, 10)
	{    
	    public void run()
	    {
			Criteria selectedCriteria = null;
			switch (_AccuracyField.getSelectedIndex())
			{
			case 0:
				selectedCriteria = _CellCriteria;
				break;
			case 1:
				selectedCriteria = _AGPSCriteria;
				break;
			case 2:
				selectedCriteria = _AutonomousGPSCriteria;
				break;
			}
			try {
				LocationProvider Provider = LocationProvider.getInstance(selectedCriteria);
				_CalcListener = new CalcDistanceLocationListener(HanselMainScreen.this,Provider);
				_CalcListener.getStartLocation();
			} catch (LocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	};
		
	protected MenuItem _FindStartAction = new MenuItem("Find Start Location" , 100000, 10)
	{    
	    public void run()
	    {
	    	if (_CalcListener != null)
	    	{
	    		_CalcListener.getEndLocation();
	    	}
	    }
	};
	
	
	public void updateCoordinates(Location loc)
	{
		String coords;
		QualifiedCoordinates qc = loc.getQualifiedCoordinates();
		coords = Double.toString(qc.getLongitude()) + " " + 
			Double.toString(qc.getLatitude());
		_CurrentCoords.setText(coords);		
	}
	
	public void locationUpdated(LocationProvider arg0, final Location arg1) {
		Runnable r = new Runnable () {
			public void run() {
				HanselMainScreen.this.updateCoordinates(arg1);
			}
		};
		HanselMainScreen.this.getApplication().invokeLater(r);
	}
	
	public void providerStateChanged(LocationProvider arg0, int arg1) {}	
}
