package com.rimdev.demos.Hansel;


import javax.microedition.location.Coordinates;
import javax.microedition.location.Location;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;

public class CalcDistanceLocationListener implements LocationListener {
	protected HanselMainScreen _Screen = null;
	protected LocationProvider _CurrentProvider = null;
	protected Coordinates _StartLocationCoordinates = null;
	
	CalcDistanceLocationListener(HanselMainScreen screen, LocationProvider prov) {
		_Screen = screen;
		_CurrentProvider=prov;
	}
	
	public void reset() {
		_StartLocationCoordinates = null;
		if (_CurrentProvider != null) {
			_CurrentProvider.setLocationListener(null,10,10,10);
		}
	}
	
	public void getStartLocation() {
		_CurrentProvider.setLocationListener(this, 1, 1, 1);
	}
	
	public void getEndLocation() {
		_CurrentProvider.setLocationListener(this, 1, 1, 1);
	}
	
	public void locationUpdated(LocationProvider provider, Location location) {
		// stop listening
		_CurrentProvider.setLocationListener(null, 1, 1, 1);
		
		if (_StartLocationCoordinates == null) {
			QualifiedCoordinates QC = location.getQualifiedCoordinates();
			_StartLocationCoordinates = new Coordinates(QC.getLatitude(),QC.getLongitude(),QC.getAltitude());
			final String output= Double.toString(QC.getLatitude()) + 
				" "+Double.toString(QC.getLongitude());
			Runnable r = new Runnable () {
				public void run() {
					_Screen._StartLocation.setText(output);
				}
			};
			_Screen.getApplication().invokeLater(r);
		}
		else {
			Coordinates start = _StartLocationCoordinates;
			Coordinates end = location.getQualifiedCoordinates();
		
			double dist = start.distance(end);
			double bear = start.azimuthTo(end);

			String units = " Meters";
			if (dist > 1000.0){
				// The distance greater than 1000 meters
				// turn it into Kilometers
				units = " Kilometers";
				dist = dist / 1000.0;
			}

			// round to two decimal places
			dist = ((double)((int)(dist*100.00)))/100.00; 
			bear = ((double)((int)(bear*100.00)))/100.00; 
			final String bearing = "Bearing: " + 
				Double.toString(bear) + " degress from true north.";
			final String distance = "Distance is: "+Double.toString(dist) + units;
			final String endoutput= Double.toString(end.getLatitude())+
				" "+Double.toString(end.getLongitude());
			
			Runnable r = new Runnable () {
				public void run() {
					_Screen._Distance.setText(distance);
					_Screen._Bearings.setText(bearing);
					_Screen._EndLocation.setText(endoutput);
				}
			};
			_Screen.getApplication().invokeLater(r);
		}
	}

	public void providerStateChanged(LocationProvider provider, int newState) {
		// TODO Auto-generated method stub

	}

}
