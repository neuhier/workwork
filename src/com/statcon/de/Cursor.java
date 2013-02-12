package com.statcon.de;

import java.awt.Graphics2D;
import java.awt.Point;

public class Cursor {

	public Point position;
	
	public Cursor() {
		position = new Point(0,0);
	}
	
	public void render(Graphics2D g) {
	
		g.drawOval(position.x, position.y, 10, 10);
	}
	
}
