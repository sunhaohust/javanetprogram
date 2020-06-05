package com.javanet.multidownload;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownLoad {
	private String filepath; 
	private String savepath;
	private int thread_num;
	public static long fileLength;
	public static long currentLength;
	public DownLoad(String filepath, String savepath, int thread_num) {
		this.filepath = filepath;
		this.savepath = savepath;
		this.thread_num = thread_num;
	}
	// ִ�������߼�
	public void startDownLoad() {
		setFileLength();
		//Ϊ�̷߳�����������
		long blockSize = fileLength/ thread_num;
		for(int i=0;i<thread_num;i++) {
			long startPos = i* blockSize;
			long endPos = 0;
			if(i==thread_num-1) {
				endPos = (i + 1)* blockSize + fileLength % thread_num;
			}else {
				endPos = (i + 1)* blockSize;
			}
			DownLoadRunnable dr = new DownLoadRunnable(startPos, endPos, filepath, savepath);
			new Thread(dr).start();
		}
	}
	// ��ȡ�����ļ��Ĵ�С��Ŀ��ʱΪ���̷߳�������
	public void setFileLength() {
		try {
			URL url = new URL(filepath);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			this.fileLength = conn.getContentLength();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
