package com.example.kidquest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {

    public static boolean check (final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if ( network!= null && network.isConnected())
        {
            return true;
        }
        network = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ( network!= null && network.isConnected())
        {
            return true;
        }
        network = cm.getActiveNetworkInfo();
        if ( network!= null && network.isConnected())
        {
            return true;
        }
        return false;
    }
}
