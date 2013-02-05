package com.statcon.de.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Calendar;

public class GameResult{

	public String name, gender, date;
	public int score;
	
	public GameResult(String n, String g, int s) {
		name = n;
		gender = g;
		score=s;
		Calendar today = Calendar.getInstance();
		date = today.get(Calendar.DAY_OF_MONTH) + "." + today.get(Calendar.MONTH) + "." + today.get(Calendar.YEAR);
	}
	
	/**
	 * Darstellung der Spielergebnisse in einer Highscoreliste.
	 */
	public void render(Graphics2D g, int x, int y, int width, int height) {
		g.setColor(Color.white);
		String message = score + " - " + name + " - " + date;
		g.drawString(message, x, y);
	}

}
