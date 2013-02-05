package com.statcon.de.menu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

public class MenuButton {

	public Rectangle hitbox;
	public Image img;
//	public Image selectedImg;
	public Image pressed;
	public String message;
	public int state = 0;
	
	/**
	 * Konstruktor.
	 */
	public MenuButton(Rectangle hb, Image image,String pressed_message) {
		hitbox = hb;
		img = image;
		message = pressed_message;
	}
	
	public MenuButton(Rectangle hb, String pressed_message){
		hitbox = hb;
		message = pressed_message;
	}
	
	
	/**
	 * Button zeichnen.
	 */
	public void render(Graphics g) {
		if(img == null) { // Text-Buttons
			g.setColor(Color.white);
			g.fillRoundRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height, 10, 10);
			g.setColor(Color.black);
			g.setFont(new Font("Arial Black", Font.BOLD, 30));
			g.drawString(message, hitbox.x+35, hitbox.y+60);
		} else {
			g.drawImage(img, hitbox.x, hitbox.y, hitbox.width, hitbox.height, null);
		}
	}
	
	/**
	 *  Wurde der Button gedrückt?
	 */
	public String hit(Point p) {
		if(hitbox.contains(p)) {
			// State auf 1
			return message;
		} else {
			return "";
		}
	}
}
