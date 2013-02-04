package com.statcon.de;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import com.statcon.de.util.Settings;

/**
 * Diese Klasse repräsentiert ein Ziel im Spiel.
 * 
 * @author Basti Hoffmeister
 *
 */
public class Destructable {

	private static final Logger log = Logger.getLogger(Destructable.class.getName());
	
	private long timeStamp;
	private Rectangle hitbox;
	private BufferedImage img;
	private BufferedImage[] deathAnimation;
	private int currImg = -1;
	
	private Random gen = new Random(); // Zum Generieren von Zufallszahlen für Größe des Ziels und seine Bewegung.
	
	/**
	 * Konstruktor.
	 * 
	 * Erstellt ein neues Ziel. Diese Ziel hat eine zufällige Größe (klein, mittel, groß).
	 */
	public Destructable() {
		timeStamp = System.currentTimeMillis(); // Geburtszeit des Ziels
		int size = gen.nextInt(6); // Welche Größe hat das neue Ziel?
		
		Dimension screenSize = Settings.SCREEN_SIZE;
		Point location = new Point(gen.nextInt(screenSize.width), gen.nextInt(screenSize.height)); // Wo erscheint das neue Ziel?

		if(size==0) { // kleines Ziel
			hitbox = new Rectangle(location, Settings.SMALL_TARGET);
			//@TODO: Zuweisen des entsprechenden Bildes
		} else if (size == 1 || size == 2) { // mittleres Ziel
			hitbox = new Rectangle(location, Settings.MEDIUM_TARGET);
		} else { // großes Ziel
			hitbox = new Rectangle(location, Settings.BIG_TARGET);
		}
	}
	
	/**
	 * Zeichnen des Ziels auf dem Bildschirm
	 */
	public void render(Graphics2D g){
		Image target = new ImageIcon(this.getClass().getResource("/images/target_small.png")).getImage();
		g.drawImage(target, hitbox.x, hitbox.y, null);
		log.info("Objekt gezeichnet!");
	};
	
	/**
	 * Bewegen des Ziels
	 */
	public void move(){

		int newX = hitbox.x;
		int newY = hitbox.y;
		
		if(newX == Settings.SCREEN_SIZE.width) {
			newX--;
		} else {
			newX = newX + gen.nextInt(3)-1; // Entweder nichts tun, oder einen pixel nach links oder rechts
		}
		
		if(newY == Settings.SCREEN_SIZE.height) {
			newY--;
		} else {
			newY = newY + gen.nextInt(3)-1; // Entweder nichts, hoch oder runter (1px).
		}
		hitbox.setLocation(newX, newY);
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
