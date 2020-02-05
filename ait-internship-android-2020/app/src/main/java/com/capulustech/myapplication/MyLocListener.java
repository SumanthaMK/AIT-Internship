package com.capulustech.myapplication;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by NK on 22-Mar-16.
 */
public interface MyLocListener
{
    public void onLocReceived(LatLng latLng);
}