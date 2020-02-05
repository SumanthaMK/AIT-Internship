package com.capulustech.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

public class FusedLocation implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener
{
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final long UPDATE_INTERVAL = 10000; //10 Sec
    private static final long FASTEST_INTERVAL = 5000; //5 Sec
    private static final float DISPLACEMENT = 10; // 10 Meters
    private Location mLastLocation;

    // Google client to interact with Google API
    private static GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean requestingLocationUpdates = false;
    private boolean requestedLocationUpdates = false;

    private LocationRequest mLocationRequest;
    double latitude = 0;
    double longitude = 0;

    Context context;

    MyLocListener locListener;

    /**
     * @param mContext
     * @param periodicUpdates to start tracking
     */
    public FusedLocation(Context mContext, boolean periodicUpdates)
    {
        this.context = mContext;
        this.requestedLocationUpdates = periodicUpdates;

        // First we need to check availability of play services
        boolean playServiceAvailable = checkPlayServices();
        if (playServiceAvailable)
        {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices()
    {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (googleAPI.isUserResolvableError(resultCode))
            {
                googleAPI.getErrorDialog(((Activity) context), resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Toast.makeText(context, "This device is not supported.", Toast.LENGTH_LONG).show();
                ((Activity) context).finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Creating google api client object
     */
    private synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        mGoogleApiClient.connect();
    }


    public void onLocReceived(MyLocListener locListener)
    {
        this.locListener = locListener;
    }

    public boolean isGPSEnabled()
    {
        /*Old Method*/
        LocationManager locationManager;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network provider status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return isGPSEnabled;
    }


    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        Log.d("NK", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0)
    {
        if (requestingLocationUpdates)
        {
            //startLocationUpdates();
            Log.d("nk", "Periodic location updates started!");
        }
        else
        {
            getLocation();
        }

        Log.d("NK", "Google API connected");
    }

    @Override
    public void onConnectionSuspended(int arg0)
    {
        Log.d("NK", "Google API suspended");
        mGoogleApiClient.connect();
    }


    /**
     * Method to display the location on UI
     */
    private LatLng getLocation()
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

            FusedLocationProviderClient mFusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(context);

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>()
                    {
                        @Override
                        public void onSuccess(Location location)
                        {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null)
                            {
                                // Logic to handle location object
                                mLastLocation = location;
                                latitude = mLastLocation.getLatitude();
                                longitude = mLastLocation.getLongitude();

                                if (locListener != null)
                                {
                                    locListener.onLocReceived(new LatLng(latitude, longitude));
                                }
                            }
                        }
                    });
        }
        return new LatLng(latitude, longitude);
    }

    public boolean isTracking()
    {
        return requestingLocationUpdates;
    }


    /**
     * Starting the location updates
     *//*
	private void startLocationUpdates()
	{
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setSmallestDisplacement(DISPLACEMENT); // 10 meters

		if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
		{
			LocationServices.getFusedLocationProviderClient(context)
					.requestLocationUpdates(mLocationRequest, this);
		}
	}*/

    /*	*/

    /**
     * Stopping location updates
     *//*
	public void stopLocationUpdates()
	{
		LocationServices.FusedLocationApi.removeLocationUpdates(
				mGoogleApiClient, this);
	}*/
    @Override
    public void onLocationChanged(Location location)
    {
        Log.d("nk", "Location Changed");
        if (locListener != null)
        {
            locListener.onLocReceived(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    public void stopLocationUpdates()
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }
}