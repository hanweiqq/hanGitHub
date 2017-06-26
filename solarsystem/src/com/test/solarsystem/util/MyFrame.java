package com.test.solarsystem.util;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 游戏类窗口
 * @author Administrator
 *
 */
public class MyFrame extends Frame{
	Image img = GameUtil.getImage("images/sun.jpg");
	
	/*
	 * 加载窗口
	 * 继承Frame基类
	 * 方便以后的窗口使用
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
	 *在线程中不停的重画窗口，实现动画 
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
