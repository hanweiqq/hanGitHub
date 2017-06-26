package com.test.solarsystem.solar;

import java.awt.Graphics;
import java.awt.Image;

import com.test.solarsystem.util.GameUtil;

/**
 * 星星类
 * @author Administrator
 *
 */
public class Star {
	Image img;
	double x,y;
	int width,height;
	
	public void draw(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
	}
	public Star() {
		// TODO Auto-generated constructor stub
	}
	
	public Star(Image img){
		this.img = img;

		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
	}
	/*
	 * 练习方法的重载
	 */
	public Star(Image img, double x, double y) {
		this(img);
		this.x = x;
		this.y = y;
	}
	public Star(String imgpath, double x, double y) {
		//使用this可以调用同一个类的其它构造函数
		//super调用上一级的方法
		this(GameUtil.getImage(imgpath),(int)x,(int)y);
	}
	
}
