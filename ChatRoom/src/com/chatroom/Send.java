package com.chatroom;

import java.awt.image.ConvolveOp;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * ������Ϣ���߳�
 * 
 * 1���Ӽ��̽�����Ϣ 2�����͸�������
 * 
 * @author Administrator
 *
 */
public class Send implements Runnable {
	// �����������ܼ����������
	private BufferedReader con;
	// ����һ�������߳�
	private DataOutputStream dos;
	//������ʾ�Ƿ�����ѭ����������
	private boolean isRunning=true;
	//�����ͻ��˵�����
	private String name;
	// �ڹ��췽���г�ʼ����
	public Send() {
		con = new BufferedReader(new InputStreamReader(System.in));
	}

	public Send(Socket client,String name) {
		this();
		try {
			// �ڹ��췽���г�ʼ�������
			dos = new DataOutputStream(client.getOutputStream());
			this.name = name;
			//��name���Դ�����������
			send(this.name);
			
		} catch (IOException e) {
			//e.printStackTrace();
			isRunning = false;
			CloseAll.closeRunnable(dos);
		}
	}

	/**
	 * �ӿ���̨��������
	 * 
	 * @return
	 */
	private String getFromConsole() {
		try {
			return con.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * �������
	 */
	private void send(String msg) {
		// �ж�Ҫ����ı������Ƿ�������
		if (null != msg && !msg.equals("")) {
			try {
				// ���msg�е�����
				dos.writeUTF(msg);
				// �Ƴ���Ϣ
				dos.flush();
			} catch (IOException e) {
				isRunning = false;
				CloseAll.closeRunnable(dos);
			}
		}
	}

	@Override
	public void run() {
		//��Ҫһֱ���������һ���д������Ϣ�����Ƴ�ȥ��������Ҫѭ��
		while (isRunning) {
			// �������̨�������Ϣ
			send(getFromConsole());
		}
	}

}
