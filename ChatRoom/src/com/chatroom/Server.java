package com.chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器端口
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
	 * 服务器线程的启动方法 由于在static静态方法中，不能直接创建非静态方法
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		// 创建一个服务器，指定端口为8888
		ServerSocket server = new ServerSocket(8888);
		while (true) {// 如果是多个客户端，则需要循环，从而创建多个socket通道以供客户端信息的接受
			// 创建一个socket通道，用来接收客户端传递的socket通道
			Socket client = server.accept();
			// 创建服务器端的线程，要接受多个客户端的信息，但客户端发送信息没有先后顺序，所以需要对服务器也创建线程
			MyServer ms = new MyServer(client);
			// 把所有客户端的信息存到list容器中统一管理
			all.add(ms);
			// 启动线程
			new Thread(ms).start();
		}
	}

	/**
	 * 创建一个内部类，实现服务器的线程
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
				// 给自己发送欢迎信息
				send("欢迎您：" + this.name);
				// 告诉聊天室内其他人
				sendOther(this.name + "进入聊天室",true);
			} catch (IOException e) {
				isRunning = false;
				CloseAll.closeRunnable(dis,dos);
			}
		}

		/**
		 * 接受客户端的信息
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
		 * 把接收到的信息发送给其它客户端
		 */
		private void sendOther(String msg,boolean sys) {
			// 判断是否有信息需要发出
			if (null != msg && !msg.equals("")) {// 有信息需要发出
				// 判断是否为私聊
				if (msg.startsWith("@") && msg.indexOf(":") > -1) {
					String name = msg.substring(1, msg.indexOf(":"));
					String content = msg.substring(msg.indexOf(":") + 1);

					// 遍历寻找指定的name
					for (MyServer temp : all) {
						if(temp.name.equals(name)){
							temp.send(this.name+"对你悄悄的说"+content);
						}
					}

				} else {

					// 遍历容器，如果是自己就跳过，如果是其他人就发送
					for (MyServer temp : all) {
						if (temp == this) {
							continue;// 容器是自己，跳过
						} 
						if(sys){//判断是否为系统信息
							temp.send("系统信息："+msg);
						}else {//不是系统信息，发送其它客户端
							temp.send(this.name+"对所有人说："+msg);
						}
					}
				}
			}
		}

		/**
		 * 把接收到的信息发送给客户端
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
				// 将信息发送给客户端
				sendOther(receive(),false);
			}

		}

	}
}
