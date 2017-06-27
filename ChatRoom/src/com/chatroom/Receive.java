package com.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * �����߳�
 * 
 * @author Administrator
 *
 */
public class Receive implements Runnable {

	private DataInputStream dis;
	// ������ʾ�Ƿ�����ѭ����������
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
	 * ��������
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
			// �ѽ��յ������ݴ�ӡ����
			System.out.println(receive());
		}
	}

}
