package com.chatroom;

import java.awt.image.ConvolveOp;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 发送信息的线程
 * 
 * 1、从键盘接受信息 2、发送给服务器
 * 
 * @author Administrator
 *
 */
public class Send implements Runnable {
	// 声明用来接受键盘输入的流
	private BufferedReader con;
	// 声明一条发送线程
	private DataOutputStream dos;
	//用来表示是否无线循环发送数据
	private boolean isRunning=true;
	//声明客户端的名字
	private String name;
	// 在构造方法中初始化流
	public Send() {
		con = new BufferedReader(new InputStreamReader(System.in));
	}

	public Send(Socket client,String name) {
		this();
		try {
			// 在构造方法中初始化输出流
			dos = new DataOutputStream(client.getOutputStream());
			this.name = name;
			//把name属性传出到服务器
			send(this.name);
			
		} catch (IOException e) {
			//e.printStackTrace();
			isRunning = false;
			CloseAll.closeRunnable(dos);
		}
	}

	/**
	 * 从控制台输入数据
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
	 * 输出方法
	 */
	private void send(String msg) {
		// 判断要输出的变量中是否有内容
		if (null != msg && !msg.equals("")) {
			try {
				// 输出msg中的内容
				dos.writeUTF(msg);
				// 推出信息
				dos.flush();
			} catch (IOException e) {
				isRunning = false;
				CloseAll.closeRunnable(dos);
			}
		}
	}

	@Override
	public void run() {
		//需要一直运行输出，一旦有传入的信息流就推出去。所以需要循环
		while (isRunning) {
			// 输出控制台传入的信息
			send(getFromConsole());
		}
	}

}
