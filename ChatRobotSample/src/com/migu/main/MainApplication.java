package com.migu.main;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application{
	private static Context mContext;
	// global context
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = getApplicationContext();
	}
	
	public static Context getmContext(){
		return mContext;
	}
	
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}
	
}
