package com.statcon.de;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

import com.statcon.de.util.Settings;

/**
 * Diese Klasse repräsentiert ein Ziel im Spiel.
 * 
 * @author Basti Hoffmeister
 *
 */
public class Destructable {

	private long timeStamp;
	private Rectangle hitbox;
	private Image img;
	private Image[] deathAnimation;
	private int currImg = -1;
	private int size; // großes Ziel = 2, mittleres Ziel = 1, kleines Ziel = 0;
	
	private Random gen = new Random(); // Zum Generieren von Zufallszahlen für Größe des Ziels und seine Bewegung.
	
	/**
	 * Konstruktor.
	 * 
	 * Erstellt ein neues Ziel. Diese Ziel hat eine zufällige Größe (klein, mittel, groß).
	 */
	public Destructable(Dimension screenSize) {
		timeStamp = System.currentTimeMillis(); // Geburtszeit des Ziels
		size = gen.nextInt(6); // Welche Größe hat das neue Ziel?
		
		deathAnimation = new Image[3];
		
		if(size==0) { // kleines Ziel
			hitbox = new Rectangle(new Point(0,0), Settings.SMALL_TARGET);
			img = new ImageIcon(this.getClass().getResource("/images/target_small.png")).getImage();
			deathAnimation[0] = new ImageIcon(this.getClass().getResource("/images/target_small_death1.png")).getImage();
			deathAnimation[1] = new ImageIcon(this.getClass().getResource("/images/target_small_death2.png")).getImage();
			deathAnimation[2] = new ImageIcon(this.getClass().getResource("/images/target_small_death3.png")).getImage();
		} else if (size == 1 || size == 2) { // mittleres Ziel
			hitbox = new Rectangle(new Point(0,0), Settings.MEDIUM_TARGET);
			img = new ImageIcon(this.getClass().getResource("/images/target_medium.png")).getImage();
			deathAnimation[0] = new ImageIcon(this.getClass().getResource("/images/target_medium_death1.png")).getImage();
			deathAnimation[1] = new ImageIcon(this.getClass().getResource("/images/target_medium_death2.png")).getImage();
			deathAnimation[2] = new ImageIcon(this.getClass().getResource("/images/target_medium_death3.png")).getImage();
		} else { // großes Ziel
			hitbox = new Rectangle(new Point(0,0), Settings.BIG_TARGET);
			img = new ImageIcon(this.getClass().getResource("/images/target_big.png")).getImage();
			deathAnimation[0] = new ImageIcon(this.getClass().getResource("/images/target_big_death1.png")).getImage();
			deathAnimation[1] = new ImageIcon(this.getClass().getResource("/images/target_big_death2.png")).getImage();
			deathAnimation[2] = new ImageIcon(this.getClass().getResource("/images/target_big_death3.png")).getImage();
		}
		hitbox.setLocation(genStartingPoint(screenSize));
	}
	
	/**
	 * Erzeugen eines passenden Punktes um ein neues Ziel erscheinen zu lassen. Irgendwo auf dem Bildschirm, aber nicht so, dass es am Rand klebt!
	 * 
	 * @param screenSize
	 * @return - der Punkt!
	 */
	private Point genStartingPoint(Dimension screenSize) {
		Point p = new Point(gen.nextInt(screenSize.width), gen.nextInt(screenSize.height));
		
		if(p.x == 0) { // Wenn das Ziel zu weit am linken Rand ist -> versetzen
			p.setLocation(p.x + 10, p.y);
		}
		if(p.x >= screenSize.width - hitbox.width){ // Wenn das Ziel zu weit am rechten Rand ist
			p.setLocation(p.x - hitbox.width - 10, p.y);
		}
		if(p.y == 0) { // Wenn das Ziel zu weit open ist
			p.setLocation(p.x, p.y + 10);
		}
		if(p.y >= screenSize.height - hitbox.height) { // wenn das Ziel zu weit unten ist
			p.setLocation(p.x, p.y - hitbox.height - 10);
		}
		return p;
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
		newX = newX + gen.nextInt(3)-1; // Entweder nichts tun, oder einen pixel nach links oder rechts
		newY = newY + gen.nextInt(3)-1; // Entweder nichts, hoch oder runter (1px).
		hitbox.setLocation(newX, newY);
	};
	
	/**
	 * Überprüft ob ein Schuss diese Ziel getroffen hat. Gibt die entsprechende Punktzahl aus.
	 * 
	 * @param p - Der Punkt auf den geschossen wurde.
	 * @return 0 - daneben geschossen, oder die in den Settings.java hinterlegten Punkte für kleine/mittlere/große Ziele
	 */
	public int hit(Point p) {
		if(hitbox.contains(p)){
			currImg = 0;
			switch(size) {
			case 0: return Settings.SCORE_SMALL_TARGET;
			case 1: return Settings.SCORE_MEDIUM_TARGET;
			case 2: return Settings.SCORE_BIG_TARGET;
			}
		}
		return 0;
	};
	
}
