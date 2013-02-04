package com.statcon.de;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * Diese Klasse repräsentiert ein Ziel im Spiel.
 * 
 * @author Basti Hoffmeister
 *
 */
public class Destructable {

	private static final Logger log = Logger.getLogger(Destructable.class.getName());
	
	private int timeStamp;
	private Rectangle hitbox;
	private BufferedImage img;
	private BufferedImage[] deathAnimation;
	private int currImg = -1;
	
	/**
	 * Zeichnen des Ziels auf dem Bildschirm
	 */
	public void render(){
		log.info("Objekt gezeichnet!");
	};
	
	/**
	 * Bewegen des Ziels
	 */
	public void move(){
		log.info("Objekt bewegt");
	};
	
	/**
	 * Überprüft ob ein Schuss diese Ziel getroffen hat.
	 * @param p - Der Punkt auf den geschossen wurde.
	 * @return <b>true</b> wenn der Punkt p innerhalb der Hitbox des Objekts liegt, <b>false</b> sonst.
	 */
	public boolean hit(Point p) {
		if(hitbox.contains(p)){
			return true;
		}
		return false;
	};
	
}
