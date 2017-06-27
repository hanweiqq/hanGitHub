package com.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 接收线程
 * 
 * @author Administrator
 *
 */
public class Receive implements Runnable {

	private DataInputStream dis;
	// 用来表示是否无线循环接收数据
	private boolean isRunning = true;

	public Receive(Socket client) {
		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			isRunning = false;
			CloseAll.closeRunnable(dis);
		}
	}

	/**
	 * 接收数据
	 * 
	 * @return
	 */
	private String receive() {
		String msg = "";
		try {
			msg = dis.readUTF();

		} catch (IOException e) {
			isRunning = false;
			CloseAll.closeRunnable(dis);
		}
		return msg;
	}

	@Override
	public void run() {
		while (isRunning) {
			// 把接收到的数据打印出来
			System.out.println(receive());
		}
	}

}
