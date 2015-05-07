package com.amr.service;

import com.amr.aidl.amrAIDL;
import com.dlx.aidl.dataAIDL;
import com.dlx.espeed.Shareds;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class aidlService extends Service {

	amrAIDL.Stub dataService  ;	// AIDLService
	
	public void onCreate () {
		super.onCreate () ;
		
		// stub로 연결하여 미리 작성한 interface대로 작성
		dataService =new amrAIDL.Stub() {
		} ;
	}
	
	// 바인더를 통해 연결
	public IBinder onBind (Intent intent) {
		return dataService ;
	}
	
	public void onDestory () {
		super.onDestroy() ;
	}

}
