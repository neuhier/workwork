package com.statcon.de;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
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
	private Image img;
	private Image[] deathAnimation;
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
		
		deathAnimation = new Image[3];
		
		if(size==0) { // kleines Ziel
			hitbox = new Rectangle(location, Settings.SMALL_TARGET);
			img = new ImageIcon(this.getClass().getResource("/images/target_small.png")).getImage();
			deathAnimation[0] = new ImageIcon(this.getClass().getResource("/images/target_small_death1.png")).getImage();
			deathAnimation[1] = new ImageIcon(this.getClass().getResource("/images/target_small_death2.png")).getImage();
			deathAnimation[2] = new ImageIcon(this.getClass().getResource("/images/target_small_death3.png")).getImage();
		} else if (size == 1 || size == 2) { // mittleres Ziel
			hitbox = new Rectangle(location, Settings.MEDIUM_TARGET);
			img = new ImageIcon(this.getClass().getResource("/images/target_medium.png")).getImage();
			deathAnimation[0] = new ImageIcon(this.getClass().getResource("/images/target_medium_death1.png")).getImage();
			deathAnimation[1] = new ImageIcon(this.getClass().getResource("/images/target_medium_death2.png")).getImage();
			deathAnimation[2] = new ImageIcon(this.getClass().getResource("/images/target_medium_death3.png")).getImage();
		} else { // großes Ziel
			hitbox = new Rectangle(location, Settings.BIG_TARGET);
			img = new ImageIcon(this.getClass().getResource("/images/target_big.png")).getImage();
			deathAnimation[0] = new ImageIcon(this.getClass().getResource("/images/target_big_death1.png")).getImage();
			deathAnimation[1] = new ImageIcon(this.getClass().getResource("/images/target_big_death2.png")).getImage();
			deathAnimation[2] = new ImageIcon(this.getClass().getResource("/images/target_big_death3.png")).getImage();
		}
	}
	
	/**
	 * Zeichnen des Ziels auf dem Bildschirm
	 */
	public void render(Graphics2D g){
		if(currImg == -1) {
			g.drawImage(img, hitbox.x, hitbox.y, null);
//			g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		} 
		if(currImg != -1 && System.currentTimeMillis() - timeStamp > 1) {
			timeStamp = System.currentTimeMillis();
			if(currImg == deathAnimation.length) {
				Game.removeObj(this);
			} else {
				g.drawImage(deathAnimation[currImg], hitbox.x, hitbox.y, null);				
				currImg++;
			}
		}
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
	//	log.info("Objekt bewegt");
	};
	
	/**
	 * Überprüft ob ein Schuss diese Ziel getroffen hat.
	 * @param p - Der Punkt auf den geschossen wurde.
	 * @return <b>true</b> wenn der Punkt p innerhalb der Hitbox des Objekts liegt, <b>false</b> sonst.
	 */
	public boolean hit(Point p) {
		if(hitbox.contains(p)){
			currImg = 0;
			return true;
		}
		return false;
	};
	
}
