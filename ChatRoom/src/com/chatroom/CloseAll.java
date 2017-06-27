package com.chatroom;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * 关闭数据流
 * 
 * @author Administrator
 *
 */
public class CloseAll {
	public static void closeRunnable(Closeable... io) {
		for (Closeable temp : io) {
			try {
				if (null != temp) {
					temp.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
