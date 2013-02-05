package com.statcon.de.menu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

import com.statcon.de.util.Settings;

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
	public void render(Graphics2D g) {
		if(img == null) { // Text-Buttons
			g.setColor(Color.white);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f)); // Halbdurchsichtige "Tasten"
			g.fill(new RoundRectangle2D.Float(hitbox.x, hitbox.y, hitbox.width, hitbox.height, 10, 10));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			g.setColor(Settings.BUTTON_TEXT_COLOR); // Dunkelblaue Schrift auf den Tasten
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
