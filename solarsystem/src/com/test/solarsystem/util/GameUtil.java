package com.test.solarsystem.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
/***
 * 载入图片地址的工具类
 * @author Administrator
 *
 */

public class GameUtil {
	private GameUtil(){};
	
	public static Image getImage(String path){
		//bufferedImage是一个图像缓冲区
		BufferedImage bi = null;
		try {
			
			URL url = GameUtil.class.getClassLoader().getResource(path);
			bi= javax.imageio.ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bi;
		
		
	}
}
