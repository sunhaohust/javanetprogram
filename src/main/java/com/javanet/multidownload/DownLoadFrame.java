package com.javanet.multidownload;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DownLoadFrame extends JFrame{
	JPanel jp1, jp2,jp3,jp4;
	JLabel download_path, save_path_label, thread_num_label, progress_label;
	JLabel info_label;
	JButton save_path_btn, download_btn;
	public JTextField filepath_jtf, savepath_jtf, threadnum_jtf;
	JProgressBar download_progress;
	public DownLoadFrame() {
		initElement();
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setSize(550,160);
		this.setLocation(600, 300);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void initElement() {
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		download_path = new JLabel("下载地址ַ");
		save_path_label  = new JLabel("保存路径");
		thread_num_label = new JLabel("线程数量");
		progress_label = new JLabel("下载进度");
		info_label = new JLabel("0%    0KB/s");
		filepath_jtf = new JTextField("https://dl.app.gtja.com/public/fy/Setup_Fuyi_simple_20200515.exe",30);
		savepath_jtf = new JTextField("D:\\",30);
		threadnum_jtf = new JTextField("3",2);
		savepath_jtf.setEditable(false);
		save_path_btn = new JButton("保存路径");
		save_path_btn.addActionListener(new MyActionListener(this));
		download_btn = new JButton("开始下载");
		download_btn.addActionListener(new MyActionListener(this));
		download_progress = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
		// jp1 ����ؼ�
		jp1.add(download_path);
		jp1.add(filepath_jtf);
		// jp2 ����ؼ�
		jp2.add(save_path_label);
		jp2.add(savepath_jtf);
		jp2.add(save_path_btn);
		// jp3 ����ؼ�
		jp3.add(thread_num_label);
		jp3.add(threadnum_jtf);
		jp3.add(download_btn);
		// jp43 ����ؼ�
		jp4.add(progress_label);
		jp4.add(download_progress);
		jp4.add(info_label);
	}
	class MyActionListener implements ActionListener{
		DownLoadFrame jFrame;
		public MyActionListener(DownLoadFrame jFrame) {
			this.jFrame = jFrame;
		}
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			if(btn.equals(save_path_btn)) {
				JFileChooser jf = new JFileChooser();
		        jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);
		        jf.showDialog(null,null);
		        File fi = jf.getSelectedFile();
		        String f = fi.getAbsolutePath();
		        this.jFrame.savepath_jtf.setText(f);
			}
			if(btn.equals(download_btn)) {
				// д���ص�ҵ���߼�, ��ȡ�ؼ���Ϣ������������
				// ʵ�����ص�ҵ���߼�
				
				//��ȡ�ļ����ص�ַ
				String filepath = jFrame.filepath_jtf.getText();
				String savepath = jFrame.savepath_jtf.getText();
				int thread_num = Integer.parseInt(jFrame.threadnum_jtf.getText());
				// ʵ����������
				System.out.println(thread_num);
				DownLoad downLoad = new DownLoad(filepath, savepath, thread_num);
				//��������
				downLoad.startDownLoad();
				//��������UI�߳�
				new UIThread(jFrame).start();
			}
		}
		
	}
	public static void main(String[] args) {
		DownLoadFrame downLoad = new DownLoadFrame();
	}
}

