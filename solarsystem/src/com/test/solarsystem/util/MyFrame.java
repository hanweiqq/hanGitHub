package com.test.solarsystem.util;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ��Ϸ�ര��
 * @author Administrator
 *
 */
public class MyFrame extends Frame{
	Image img = GameUtil.getImage("images/sun.jpg");
	
	/*
	 * ���ش���
	 * �̳�Frame����
	 * �����Ժ�Ĵ���ʹ��
	 */
	public void launchFrame(){
		setSize(Constant.WIDTH,Constant.HEIGHT);
		setLocation(100,100);
		setVisible(true);
		new PaintThread().start();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	/*
	 *���߳��в�ͣ���ػ����ڣ�ʵ�ֶ��� 
	 */
	public class PaintThread extends Thread{
		@Override
		public void run() {
			while (true) {
				repaint();
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
			}
		}
		
	}
}
