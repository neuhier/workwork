package com.statcon.de.menu;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.statcon.de.util.Settings;

public class Keyboard {

	private ArrayList<MenuButton> keys;
	
	public Keyboard(){

	keys = new ArrayList<MenuButton>();
		
	int bwidth = 100;
	int bheight = bwidth;
	int margin = 650;
	
	int firstrow_y =  (int) Settings.SCREEN_SIZE.getHeight() - 3*bheight - 30; // Von unten 3 Reihen mit Abstand 10px. 

	keys.add(new MenuButton(new Rectangle(margin-250, firstrow_y - 400, 200, 200), new ImageIcon(this.getClass().getResource("/images/female.png")).getImage(), "Female"));
	keys.add(new MenuButton(new Rectangle(margin, firstrow_y - 400, 200, 200), new ImageIcon(this.getClass().getResource("/images/na.png")).getImage(), "NA"));
	keys.add(new MenuButton(new Rectangle(margin+250, firstrow_y - 400, 200, 200), new ImageIcon(this.getClass().getResource("/images/male.png")).getImage(), "Male"));
	
	keys.add(new MenuButton(new Rectangle(margin, firstrow_y - 150, 200 ,bheight), "Play"));
	
	margin = 250;
	
	keys.add(new MenuButton(new Rectangle(margin + 10, firstrow_y, bwidth, bheight), "Q"));
	keys.add(new MenuButton(new Rectangle(margin +bwidth+20, firstrow_y, bwidth, bheight), "W"));
	keys.add(new MenuButton(new Rectangle(margin +2*bwidth+30, firstrow_y, bwidth, bheight), "E"));
	keys.add(new MenuButton(new Rectangle(margin +3*bwidth+40, firstrow_y, bwidth, bheight), "R"));
	keys.add(new MenuButton(new Rectangle(margin +4*bwidth+50, firstrow_y, bwidth, bheight), "T"));
	keys.add(new MenuButton(new Rectangle(margin +5*bwidth+60, firstrow_y, bwidth, bheight), "Z"));
	keys.add(new MenuButton(new Rectangle(margin +6*bwidth+70, firstrow_y, bwidth, bheight), "U"));
	keys.add(new MenuButton(new Rectangle(margin +7*bwidth+80, firstrow_y, bwidth, bheight), "I"));
	keys.add(new MenuButton(new Rectangle(margin +8*bwidth+90, firstrow_y, bwidth, bheight), "O"));
	keys.add(new MenuButton(new Rectangle(margin +9*bwidth+100, firstrow_y, bwidth, bheight), "P")); // Erste Reihe

	margin = 300;
	keys.add(new MenuButton(new Rectangle(margin +10, firstrow_y+bheight+10, bwidth, bheight), "A"));
	keys.add(new MenuButton(new Rectangle(margin +bwidth+20, firstrow_y+bheight+10, bwidth, bheight), "S"));
	keys.add(new MenuButton(new Rectangle(margin +2*bwidth+30, firstrow_y+bheight+10, bwidth, bheight), "D"));
	keys.add(new MenuButton(new Rectangle(margin +3*bwidth+40, firstrow_y+bheight+10, bwidth, bheight), "F"));
	keys.add(new MenuButton(new Rectangle(margin +4*bwidth+50, firstrow_y+bheight+10, bwidth, bheight), "G"));
	keys.add(new MenuButton(new Rectangle(margin +5*bwidth+60, firstrow_y+bheight+10, bwidth, bheight), "H"));
	keys.add(new MenuButton(new Rectangle(margin +6*bwidth+70, firstrow_y+bheight+10, bwidth, bheight), "J"));
	keys.add(new MenuButton(new Rectangle(margin +7*bwidth+80, firstrow_y+bheight+10, bwidth, bheight), "K"));
	keys.add(new MenuButton(new Rectangle(margin +8*bwidth+90, firstrow_y+bheight+10, bwidth, bheight), "L")); // zweite Reihe

	
	margin = 350;
	keys.add(new MenuButton(new Rectangle(margin +10, firstrow_y+2*bheight+20, bwidth, bheight), "Y"));
	keys.add(new MenuButton(new Rectangle(margin +bwidth+20, firstrow_y+2*bheight+20, bwidth, bheight), "X"));
	keys.add(new MenuButton(new Rectangle(margin +2*bwidth+30, firstrow_y+2*bheight+20, bwidth, bheight), "C"));
	keys.add(new MenuButton(new Rectangle(margin +3*bwidth+40, firstrow_y+2*bheight+20, bwidth, bheight), "V"));
	keys.add(new MenuButton(new Rectangle(margin +4*bwidth+50, firstrow_y+2*bheight+20, bwidth, bheight), "B"));
	keys.add(new MenuButton(new Rectangle(margin +5*bwidth+60, firstrow_y+2*bheight+20, bwidth, bheight), "N"));
	keys.add(new MenuButton(new Rectangle(margin +6*bwidth+70, firstrow_y+2*bheight+20, bwidth, bheight), "M"));
	keys.add(new MenuButton(new Rectangle(margin +7*bwidth+80, firstrow_y+2*bheight+20, bwidth, bheight), "<-")); // dritte Reihe
	
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
