package com.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 客户端
 * 聊天室模板，标记各项语句功能以及不足
 * @author Administrator
 *
 */
public class Client {
	//这是聊天室的客户端     声明Send发送线程和Receive接收线程
	//客户端发送信息与接收信息不会只能是发一条接一条，所以讲发送信息和接收信息都写到线程内，使其互不影响。
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("请输入您的名字：");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//为每个客户端添加一个名字
		String name = br.readLine();
		if(name.equals("")){
			return;
		}
		//创建一个socket通道，指定其地址和端口。用来连接服务器
		Socket client = new Socket("localhost", 8888);
		//创建发送信息线程
		new Thread(new Send(client,name)).start();
		//创建接受信息的线程
		new Thread(new Receive(client)).start();
		
	}
}
