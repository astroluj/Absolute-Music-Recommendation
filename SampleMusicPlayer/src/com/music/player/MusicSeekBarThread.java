package com.music.player;

import android.os.Handler;

public class MusicSeekBarThread extends Thread {

	private Handler handler ;
	private boolean runFlag, handlerFlag ;
	
	public MusicSeekBarThread (Handler handler) {
		
		this.handler = handler ;
		this.runFlag = true ;
		this.handlerFlag = true ;
	}
	
	synchronized public void setRun (boolean runFlag) {
		this.runFlag = runFlag ;
	}

	synchronized public void setHandler (boolean handlerFlag) {
		this.handlerFlag = handlerFlag ;
	}
	
	public void run () {
		try {
			while(runFlag && !isInterrupted()) {
				try {

					sleep(1000) ;
					if (handlerFlag) handler.sendEmptyMessage(0) ;

				} catch (Exception e) {}
			}
		} catch (Exception e) {}
	}
}
