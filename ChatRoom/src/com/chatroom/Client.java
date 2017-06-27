package com.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * �ͻ���
 * ������ģ�壬��Ǹ�����书���Լ�����
 * @author Administrator
 *
 */
public class Client {
	//���������ҵĿͻ���     ����Send�����̺߳�Receive�����߳�
	//�ͻ��˷�����Ϣ�������Ϣ����ֻ���Ƿ�һ����һ�������Խ�������Ϣ�ͽ�����Ϣ��д���߳��ڣ�ʹ�以��Ӱ�졣
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("�������������֣�");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//Ϊÿ���ͻ������һ������
		String name = br.readLine();
		if(name.equals("")){
			return;
		}
		//����һ��socketͨ����ָ�����ַ�Ͷ˿ڡ��������ӷ�����
		Socket client = new Socket("localhost", 8888);
		//����������Ϣ�߳�
		new Thread(new Send(client,name)).start();
		//����������Ϣ���߳�
		new Thread(new Receive(client)).start();
		
	}
}
