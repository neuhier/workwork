package com.statcon.de.menu;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Keyboard {

	private ArrayList<MenuButton> keys;
	
	public Keyboard(Dimension screen){

	keys = new ArrayList<MenuButton>();

	// Geschlecht
	
	int center = (int)(screen.getWidth())/2;
	int height = (int)(screen.getHeight())/5;
			
	keys.add(new MenuButton(new Rectangle(center-350, height/3, 200, 200), new ImageIcon(this.getClass().getResource("/images/female.png")).getImage(), "Female"));
	keys.add(new MenuButton(new Rectangle(center-100, height/3, 200, 200), new ImageIcon(this.getClass().getResource("/images/na.png")).getImage(), "NA"));
	keys.add(new MenuButton(new Rectangle(center+150, height/3, 200, 200), new ImageIcon(this.getClass().getResource("/images/male.png")).getImage(), "Male"));
	
	// Play
	keys.add(new MenuButton(new Rectangle((int)screen.getWidth() - 250, (int)screen.getHeight() - 150, 200, 100), "Play"));

	// Tastatur

	//	int keywidth = (int) (screen.getWidth()/100 - 110); // 10 Knöpfe + Abstände
	int keywidth = 100;
	int keyheight = keywidth; // Quadratische Knöpfe
	int key_y =  3*height; // Von unten 3 Reihen mit Abstand 10px. 
	
	keys.add(new MenuButton(new Rectangle(center - 5*keywidth - 45, key_y, keywidth, keyheight), "Q"));
	keys.add(new MenuButton(new Rectangle(center - 4*keywidth - 35, key_y, keywidth, keyheight), "W"));
	keys.add(new MenuButton(new Rectangle(center - 3*keywidth - 25, key_y, keywidth, keyheight), "E"));
	keys.add(new MenuButton(new Rectangle(center - 2*keywidth - 15, key_y, keywidth, keyheight), "R"));
	keys.add(new MenuButton(new Rectangle(center - 1*keywidth - 5, key_y, keywidth, keyheight), "T"));
	keys.add(new MenuButton(new Rectangle(center + 5, key_y, keywidth, keyheight), "Z"));
	keys.add(new MenuButton(new Rectangle(center + 1*keywidth + 15, key_y, keywidth, keyheight), "U"));
	keys.add(new MenuButton(new Rectangle(center + 2*keywidth + 25, key_y, keywidth, keyheight), "I"));
	keys.add(new MenuButton(new Rectangle(center + 3*keywidth + 35, key_y, keywidth, keyheight), "O"));
	keys.add(new MenuButton(new Rectangle(center + 4*keywidth + 45, key_y, keywidth, keyheight), "P")); // Erste Reihe

	keys.add(new MenuButton(new Rectangle((int) (center - 4.5*keywidth - 40), key_y+keyheight+10, keywidth, keyheight), "A"));
	keys.add(new MenuButton(new Rectangle((int) (center - 3.5*keywidth - 30), key_y+keyheight+10, keywidth, keyheight), "S"));
	keys.add(new MenuButton(new Rectangle((int) (center - 2.5*keywidth - 20), key_y+keyheight+10, keywidth, keyheight), "D"));
	keys.add(new MenuButton(new Rectangle((int) (center - 1.5*keywidth - 10), key_y+keyheight+10, keywidth, keyheight), "F"));
	keys.add(new MenuButton(new Rectangle((int) (center - 0.5*keywidth), key_y+keyheight+10, keywidth, keyheight), "G"));
	keys.add(new MenuButton(new Rectangle((int) (center + 0.5*keywidth + 10), key_y+keyheight+10, keywidth, keyheight), "H"));
	keys.add(new MenuButton(new Rectangle((int) (center + 1.5*keywidth + 20), key_y+keyheight+10, keywidth, keyheight), "J"));
	keys.add(new MenuButton(new Rectangle((int) (center + 2.5*keywidth + 30), key_y+keyheight+10, keywidth, keyheight), "K"));
	keys.add(new MenuButton(new Rectangle((int) (center + 3.5*keywidth + 40), key_y+keyheight+10, keywidth, keyheight), "L")); // zweite Reihe

	keys.add(new MenuButton(new Rectangle(center - 4*keywidth - 35, key_y+2*keyheight+20, keywidth, keyheight), "Y"));
	keys.add(new MenuButton(new Rectangle(center - 3*keywidth - 25, key_y+2*keyheight+20, keywidth, keyheight), "X"));
	keys.add(new MenuButton(new Rectangle(center - 2*keywidth - 15, key_y+2*keyheight+20, keywidth, keyheight), "C"));
	keys.add(new MenuButton(new Rectangle(center - 1*keywidth - 5, key_y+2*keyheight+20, keywidth, keyheight), "V"));
	keys.add(new MenuButton(new Rectangle(center + 5, key_y+2*keyheight+20, keywidth, keyheight), "B"));
	keys.add(new MenuButton(new Rectangle(center + keywidth + 15, key_y+2*keyheight+20, keywidth, keyheight), "N"));
	keys.add(new MenuButton(new Rectangle(center + 2*keywidth + 25, key_y+2*keyheight+20, keywidth, keyheight), "M"));
	keys.add(new MenuButton(new Rectangle(center + 3*keywidth + 35, key_y+2*keyheight+20, keywidth, keyheight), "<-")); // dritte Reihe
	
	}
	/**
	 * Render all keyboard buttons.
	 */
	public void render(Graphics2D g) {
		for(MenuButton i:keys){
			i.render(g);
		}
	}
	
	public String hit(Point p) {
		String result = "";
		for(MenuButton i:keys){
			if(i.hit(p) != "") {
				result = i.hit(p);
			}
		}
		return result;
	}
}
