package com.test.solarsystem.solar;

import java.awt.Graphics;
import java.awt.Image;

import com.test.solarsystem.util.Constant;
import com.test.solarsystem.util.GameUtil;
import com.test.solarsystem.util.MyFrame;

/**
 * 太阳系的主窗口
 * @author Administrator
 *
 */
public class SolarFrame extends MyFrame{
	Image bg = GameUtil.getImage("images/bg.jpg");
	Star sun = new Star("images/sun.jpg", Constant.WIDTH/2, Constant.HEIGHT/2);
	Planet Mercury = new Planet(sun, "images/Mercury.jpg", 30, 30, 0.0882, false);
	Planet Venus = new Planet(sun, "images/Venus.jpg", 56.04, 56.04, 0.0645, false);
	Planet earth = new Planet(sun, "images/earth.jpg", 77.49,77.49, 0.0552, false);
	
	Planet moon = new Planet(earth, "images/moon.jpg", 20,20, 0.147, true);
	Planet Mars = new Planet(sun, "images/Mars.jpg", 118.08, 118.08, 0.0552, false);
	Planet Jupiter = new Planet(sun, "images/Jupiter.jpg", 150, 150, 0.0444, false);
	Planet Saturn = new Planet(sun, "images/Saturn.jpg", 237.6, 237.6, 0.0177, false);
	Planet Uranus = new Planet(sun, "images/Uranus.jpg", 283.08, 283.08, 0.0125, false);
	Planet Neptune = new Planet(sun, "images/Neptune.jpg",300, 300, 0.010, false);
	
	
	public void paint(Graphics g){
		g.drawImage(bg, 0, 0, null);
		sun.draw(g);

		Mercury.draw(g);
		Venus.draw(g);
		earth.draw(g);
		moon.draw(g);
		Mars.draw(g);
		Jupiter.draw(g);
		Saturn.draw(g);
		Uranus.draw(g);
		Neptune.draw(g);
	}
	public static void main(String[] args) {
		new SolarFrame().launchFrame();
	}
}
