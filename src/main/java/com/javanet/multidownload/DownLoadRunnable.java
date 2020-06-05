package com.javanet.multidownload;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadRunnable implements Runnable{
	private long startPos;
	private long endPos;
	private String filepath;
	private String savepath;
	private long partLength;
	public DownLoadRunnable(long startPos, long endPos,String filepath,String savepath) {
		this.startPos = startPos;
		this.endPos = endPos;
		this.filepath = filepath;
		this.savepath = savepath;
		partLength = 0;
	}
	@Override
	public void run() {
		String filename = filepath.substring(filepath.trim().lastIndexOf("/"));
		try {
			RandomAccessFile file = new RandomAccessFile(savepath + filename, "rws");
			file.seek(startPos);
			URL url = new URL(filepath);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
			InputStream inputStream = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int ch = 0;
			while((ch = inputStream.read(buffer))!=-1) {
				synchronized (DownLoad.class) {
					DownLoad.currentLength += ch;
				}
				file.write(buffer, 0, ch);
			}
			file.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
