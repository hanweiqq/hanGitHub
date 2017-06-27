package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * �������˿�
 * 
 * @author Administrator
 *
 */
public class Server {
	private List<MyServer> all = new ArrayList<MyServer>();

	public static void main(String[] args) throws IOException {

		new Server().start();

	}

	/**
	 * �������̵߳��������� ������static��̬�����У�����ֱ�Ӵ����Ǿ�̬����
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		// ����һ����������ָ���˿�Ϊ8888
		ServerSocket server = new ServerSocket(8888);
		while (true) {// ����Ƕ���ͻ��ˣ�����Ҫѭ�����Ӷ��������socketͨ���Թ��ͻ�����Ϣ�Ľ���
			// ����һ��socketͨ�����������տͻ��˴��ݵ�socketͨ��
			Socket client = server.accept();
			// �����������˵��̣߳�Ҫ���ܶ���ͻ��˵���Ϣ�����ͻ��˷�����Ϣû���Ⱥ�˳��������Ҫ�Է�����Ҳ�����߳�
			MyServer ms = new MyServer(client);
			// �����пͻ��˵���Ϣ�浽list������ͳһ����
			all.add(ms);
			// �����߳�
			new Thread(ms).start();
		}
	}

	/**
	 * ����һ���ڲ��࣬ʵ�ַ��������߳�
	 */
	private class MyServer implements Runnable {

		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning = true;
		private String name;

		public MyServer(Socket client) {
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				this.name = dis.readUTF();
				// ���Լ����ͻ�ӭ��Ϣ
				send("��ӭ����" + this.name);
				// ������������������
				sendOther(this.name + "����������",true);
			} catch (IOException e) {
				isRunning = false;
				CloseAll.closeRunnable(dis,dos);
			}
		}

		/**
		 * ���ܿͻ��˵���Ϣ
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

		/**
		 * �ѽ��յ�����Ϣ���͸������ͻ���
		 */
		private void sendOther(String msg,boolean sys) {
			// �ж��Ƿ�����Ϣ��Ҫ����
			if (null != msg && !msg.equals("")) {// ����Ϣ��Ҫ����
				// �ж��Ƿ�Ϊ˽��
				if (msg.startsWith("@") && msg.indexOf(":") > -1) {
					String name = msg.substring(1, msg.indexOf(":"));
					String content = msg.substring(msg.indexOf(":") + 1);

					// ����Ѱ��ָ����name
					for (MyServer temp : all) {
						if(temp.name.equals(name)){
							temp.send(this.name+"�������ĵ�˵"+content);
						}
					}

				} else {

					// ����������������Լ�������������������˾ͷ���
					for (MyServer temp : all) {
						if (temp == this) {
							continue;// �������Լ�������
						} 
						if(sys){//�ж��Ƿ�Ϊϵͳ��Ϣ
							temp.send("ϵͳ��Ϣ��"+msg);
						}else {//����ϵͳ��Ϣ�����������ͻ���
							temp.send(this.name+"��������˵��"+msg);
						}
					}
				}
			}
		}

		/**
		 * �ѽ��յ�����Ϣ���͸��ͻ���
		 */
		private void send(String msg) {
			if(null==msg ||msg.equals("")){
				return ;
			}
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				isRunning = false;
				CloseAll.closeRunnable(dos);
			}
		}

		@Override
		public void run() {
			while (isRunning) {
				// ����Ϣ���͸��ͻ���
				sendOther(receive(),false);
			}

		}

	}
}
