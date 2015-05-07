package com.amr.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AMRService extends Service{

	public void onCreate () {
		super.onCreate() ;
		
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId) ;
		
		return START_NOT_STICKY ;
	}
	
	// 바인더를 통해 연결
	public IBinder onBind (Intent intent) {
		return null ;
	}
	
	public void onDestory () {
		super.onDestroy() ;
	}
}
