package com.test.solarsystem.solar;

import java.awt.Graphics;
import java.awt.Image;

import com.test.solarsystem.util.GameUtil;

/**
 * ������
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
	 * ��ϰ����������
	 */
	public Star(Image img, double x, double y) {
		this(img);
		this.x = x;
		this.y = y;
	}
	public Star(String imgpath, double x, double y) {
		//ʹ��this���Ե���ͬһ������������캯��
		//super������һ���ķ���
		this(GameUtil.getImage(imgpath),(int)x,(int)y);
	}
	
}
