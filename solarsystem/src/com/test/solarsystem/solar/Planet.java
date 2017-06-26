package com.test.solarsystem.solar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.test.solarsystem.util.GameUtil;

/**
 * 行星类
 * 计算出所有行星围绕太阳旋转的轨迹和距离
 * @author Administrator
 *
 */
public class Planet extends Star{
	
	private double longAxis;
	private double shortAxis;
	private double speed;//移动速度
	private double degree;
	Star center;	//星球的圆心
	
	boolean satellite;
	public void draw(Graphics g){
		super.draw(g);
		move();
		
		//如果不是卫星，则画出相应的轨迹
		if(!satellite){
			//drawTrace(g);
		}
	}
	//飞行轨迹位置
	private void move(){
		x = (center.x+center.width/2)+longAxis*Math.cos(degree);
		y = (center.y+center.height/2)+shortAxis*Math.sin(degree);
		
		degree +=speed;
	}
	//画出轨迹
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
	 * 初始化星星的所有参数
	 * 中心，图片地址，长轴，短轴，速度
	 * @param center 行星围绕旋转的中心
	 * @param imgpath	行星图片的地址
	 * @param longAxis	行星的长轴
	 * @param shortAxis	行星的短轴
	 * @param speed	行星旋转的速度
	 */
	public Planet(Star center,String imgpath,double longAxis, double shortAxis, double speed) {
		super(GameUtil.getImage(imgpath));
		//获取图片的长和宽
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		this.center = center;
		//注意定义XY坐标时是已sun的左上角为坐标的，需要找到中心
		this.x = center.x+longAxis+width/2;
		this.y = center.y+shortAxis+height/2;
		this.longAxis = longAxis;
		this.shortAxis = shortAxis;
		this.speed = speed;
	}
	/**
	 * 初始化星星的所有参数
	 * 中心，图片地址，长轴，短轴，速度
	 * @param center 行星围绕旋转的中心
	 * @param imgpath	行星图片的地址
	 * @param longAxis	行星的长轴
	 * @param shortAxis	行星的短轴
	 * @param speed	行星旋转的速度
	 * @param satellite	是否是卫星
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
