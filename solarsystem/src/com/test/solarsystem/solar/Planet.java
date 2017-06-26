package com.test.solarsystem.solar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.test.solarsystem.util.GameUtil;

/**
 * ������
 * �������������Χ��̫����ת�Ĺ켣�;���
 * @author Administrator
 *
 */
public class Planet extends Star{
	
	private double longAxis;
	private double shortAxis;
	private double speed;//�ƶ��ٶ�
	private double degree;
	Star center;	//�����Բ��
	
	boolean satellite;
	public void draw(Graphics g){
		super.draw(g);
		move();
		
		//����������ǣ��򻭳���Ӧ�Ĺ켣
		if(!satellite){
			//drawTrace(g);
		}
	}
	//���й켣λ��
	private void move(){
		x = (center.x+center.width/2)+longAxis*Math.cos(degree);
		y = (center.y+center.height/2)+shortAxis*Math.sin(degree);
		
		degree +=speed;
	}
	//�����켣
	private void drawTrace(Graphics g){
		double ovalX,ovalY,ovalWidth,ovalHeight;
		ovalWidth = longAxis*2;
		ovalHeight = shortAxis*2;
		ovalX = (center.x+center.width/2)-longAxis;
		ovalY = (center.y+center.height/2)-shortAxis;
		
		Color c = g.getColor();
		g.setColor(Color.blue);
		g.drawOval((int)ovalX, (int)ovalY, (int)ovalWidth, (int)ovalHeight);
		g.setColor(c);
	}
	

	/**
	 * ��ʼ�����ǵ����в���
	 * ���ģ�ͼƬ��ַ�����ᣬ���ᣬ�ٶ�
	 * @param center ����Χ����ת������
	 * @param imgpath	����ͼƬ�ĵ�ַ
	 * @param longAxis	���ǵĳ���
	 * @param shortAxis	���ǵĶ���
	 * @param speed	������ת���ٶ�
	 */
	public Planet(Star center,String imgpath,double longAxis, double shortAxis, double speed) {
		super(GameUtil.getImage(imgpath));
		//��ȡͼƬ�ĳ��Ϳ�
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		this.center = center;
		//ע�ⶨ��XY����ʱ����sun�����Ͻ�Ϊ����ģ���Ҫ�ҵ�����
		this.x = center.x+longAxis+width/2;
		this.y = center.y+shortAxis+height/2;
		this.longAxis = longAxis;
		this.shortAxis = shortAxis;
		this.speed = speed;
	}
	/**
	 * ��ʼ�����ǵ����в���
	 * ���ģ�ͼƬ��ַ�����ᣬ���ᣬ�ٶ�
	 * @param center ����Χ����ת������
	 * @param imgpath	����ͼƬ�ĵ�ַ
	 * @param longAxis	���ǵĳ���
	 * @param shortAxis	���ǵĶ���
	 * @param speed	������ת���ٶ�
	 * @param satellite	�Ƿ�������
	 */
	public Planet(Star center,String imgpath,double longAxis,double shortAxis,double speed,boolean satellite){
		this(center,imgpath,longAxis,shortAxis,speed);
		this.satellite = satellite;
	}
	public Planet(String imgpath, double x, double y){
		super(imgpath,x,y);
	}
	public Planet(Image img, double x, double y){
		super(img,x,y);
	}
	
	
	

}
