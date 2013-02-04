package com.statcon.de;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.statcon.de.util.Settings;


/*
 * 
 * TODOS:
 * - Bug mit Liste beseitigen
 * - Einschussöcher beim Schießen
 * - Vernünftige Bewegung der Zielscheiben
 * - Musik + Soundeffekte
 * - Menü: Geschlecht wählen, Namen eingeben, Start-Knopf
 * - Highscore: Liste mit den Top x anzeigen
 * 
 */


/**
 * Diese Klasse repräsentiert ein Spiel. Das Spiel kann in verschiedenen Zuständen sein:
 * <li> Menu: Das Hauptmenü enthält die Möglichkeit einen Namen einzugeben sowie das Spiel zu starten.</li>
 * <li> Game: Das Spiel läuft eine bestimmte Zeitspanne lang. Während dessen werden beständig neue Ziele generiert bzw. entfernt.</li>
 * <li> Score: Wenn die Zeit des Spiels um ist, wird der Highscore angezeigt. Auf Knopfdruck kommt man in den Zustand "Menü".</li>
 * 
 * @author Basti Hoffmeister
 *
 */
public class Game extends JPanel{

	
	private int kills = 0; // Wie viele Kills
	private int streak = 0; // Wie viele Kills am Stück
	private String name = Settings.DEFAULT_PLAYER_NAME; // Name des Spielers für den Highscore
	private long roundStart = 0; // Zeitpunkt an dem eine Spielrunde startet
	private long paintStamp; // Zeitzähler um die anzahl der Frames / Sekunde zu kontrollieren
	
	private enum state {menu, game, highscore};
	public state gameState = state.menu;

	LinkedList <Destructable> objs = new LinkedList<Destructable>(); // aktuell vorhandene Ziele
	static LinkedList <Destructable> removeObjs = new LinkedList<Destructable>(); // zu entfernende Ziele
	
	/**
	 * Zeichnen des Bildschirms in Abhängigkeit des gameStates.
	 * <li> Background + Menü im gameState "menu"</li>
	 * <li> Background + Objekte im gameState "game"</li>
	 * <li> Background + Highscore Liste im gameState "highscore"</li>
	 */
	synchronized void render() {
		
		if(gameState == state.game) {
			repaint();
			for(Destructable i:removeObjs) { // Abgeballerte Ziele entfernen. Bzw. Ziele, die schon zu lange ungetroffen umherirren.
				objs.remove(i);
			}
			removeObjs.clear();
		} else if(gameState == state.menu) {
		} else {
			// Im Moment: Spiel verlassen wenn die Runde vorbei ist
			System.exit(0);
		}
	};
	
	/**
	 * Wird ausgelöst, immer wenn ein Button auf dem Wiimote gedrückt wird.
	 * 
	 * @param p - die Position des "Cursors" im Moment als der Button gedrückt wird.
	 */
	synchronized void hit(Point p) {
		for(Destructable i:objs) {
			if(i.hit(p)) { // Wenn ein existierendes Objekt getroffen wurde
				kills++;
			}
		}
	};
	
	/**
	 * Diese Methode überprüft ob die Spielrunde um ist. Ausserdem fügt sie gegebenenfalls neue Ziele zur obj-Liste hinzu.
	 */
	synchronized void checkStatus() {
		if(gameState == state.game) { // Während der Runde wird überprüft ob die Zeit aus ist und ob Ziele hinzugefügt werden müssen.
		if(System.currentTimeMillis() - roundStart >= Settings.ROUND_TIME_MS) { // Runde beenden, wenn die Spielzeit rum ist
			nextGameState(); // Highscore anzeigen
		} else { // ggf. neue Ziele erzeugen
			populate();	
		}} else {
		}
	};
	
	/**
	 * Spiel starten.
	 */
	public void initializate() {
//		log.info("Mit Wiimote verbinden");
//		log.info("Name auf <player> setzen, Punkte auf 0, streak auf 0");
//		log.info("Menü zeichnen");
// Hier eigentlich auf Mausklick reagiert:	
//		log.info("Start geklickt!");
//		log.info("Gamestat auf game setzen.");
		nextGameState();

		this.addMouseListener(new MouseAdapter() { 
		    public void mousePressed(MouseEvent me) { 
		    	hit(me.getLocationOnScreen());
		    } 
		}); 

		paintStamp = System.currentTimeMillis();
		while(true){
			render();
			if(System.currentTimeMillis() - paintStamp > 1) { // nur alle so und so viel sekunden rendern
				moveObjects();
			}
			checkStatus();
			paintStamp = System.currentTimeMillis();
		}
	};
	
	/**
	 * Bewegen der lebenden Ziele.
	 */
	public void moveObjects(){
		for(Destructable i:objs) {
			i.move();
		}
	};
	
	/**
	 * Entfernen von Objekten die verschwinden sollen. Entweder weil sie abgeschossen wurden, oder weil sie schon zu lange "leben".
	 * 
	 * @param destructable
	 */
	public static void removeObj(Destructable destructable){
		removeObjs.add(destructable);
	};

	/**
	 * Change the game state. If the game is currently in "menu"-mode it starts the round. This happens if the player presses the "play"-button.
	 * If the game is in "game"-mode the gameState changes to "highscore". This happens after 30 sec of playing.
	 * If the game is in "highscore"-mode the gameState changes to "menu". This happens whenever a button is pressed in "highscore"-mode.
	 */
	private void nextGameState() {
		if(gameState == state.menu) {
			gameState = state.game;
			roundStart = System.currentTimeMillis(); //Zeitpunkt, wann die Runde beginnt
		} else if(gameState == state.game){
			gameState = state.highscore;
		} else {
			gameState = state.menu;
		}
		
	}
	
	/**
	 *  Je geringer die Anzahl der Ziele, desto höher die Wkt. ein neues hinzuzufügen.
	 */
	private void populate() {
		Random gen = new Random();
		switch(objs.size()) {
		case 0: objs.add(new Destructable());
		case 1: if(gen.nextInt(6) == 0) {objs.add(new Destructable());};
		case 2: if(gen.nextInt(5) == 0) {objs.add(new Destructable());};
		case 3: if(gen.nextInt(4) == 0) {objs.add(new Destructable());};
		case 4: if(gen.nextInt(3) == 0) {objs.add(new Destructable());};
		case 5: if(gen.nextInt(2) == 0) {objs.add(new Destructable());};
		default:;
		}
	}
	
	/**
	 * Paint-Methode für die Spiel Klasse
	 */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		Image background = new ImageIcon(this.getClass().getResource("/images/background_01.png")).getImage();
		g.drawImage(background, 0, 0, null);
		
		for(Destructable i:objs) {
			i.render(g2d);
		}
	}
	
}
