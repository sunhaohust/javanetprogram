package com.javanet.multidownload;

import javax.swing.SwingUtilities;

public class UIThread extends Thread{
	private DownLoadFrame jFrame;
	private long currentLength;
	private long startLength = 0;
	public UIThread(DownLoadFrame jFrame) {
		this.jFrame = jFrame;
	}
	
	public void run() {
		while(true) {
			currentLength = DownLoad.currentLength;

			final float percent = (float)currentLength / DownLoad.fileLength * 100;

			float speed = (float)(currentLength - startLength) / 1024 /1024;
			startLength = currentLength;
			final String msg = String.format("%d"+"%% / " + "%.2f" + "MB/S", (int)percent,speed);

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					jFrame.download_progress.setValue((int)percent);
					jFrame.info_label.setText(msg);
				}
			});
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(currentLength>DownLoad.fileLength) {
				break;
			}
		}
	}
	

}
