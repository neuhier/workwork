package com.statcon.de;

import java.awt.Point;
import java.util.LinkedList;
import java.util.logging.Logger;

import com.statcon.de.util.Settings;

/**
 * Diese Klasse repr�sentiert ein Spiel. Das Spiel kann in verschiedenen Zust�nden sein:
 * <li> Menu: Das Hauptmen� enth�lt die M�glichkeit einen Namen einzugeben sowie das Spiel zu starten.</li>
 * <li> Game: Das Spiel l�uft eine bestimmte Zeitspanne lang. W�hrend dessen werden best�ndig neue Ziele generiert bzw. entfernt.</li>
 * <li> Score: Wenn die Zeit des Spiels um ist, wird der Highscore angezeigt. Auf Knopfdruck kommt man in den Zustand "Men�".</li>
 * 
 * @author Basti Hoffmeister
 *
 */
public class Game {

	private static final Logger log = Logger.getLogger(Game.class.getName());
			
	private enum state {menu, game, highscore};

	public state gameState = state.menu;
	
	private int kills = 0; // Wie viele Kills
	private int streak = 0; // Wie viele Kills am St�ck
	private String name = Settings.DEFAULT_PLAYER_NAME; // Name des Spielers f�r den Highscore
	private long roundStart = 0; // Zeitpunkt an dem eine Spielrunde startet
	LinkedList <Destructable> objs = new LinkedList<Destructable>(); // aktuell vorhandene Ziele
	LinkedList <Destructable> removeObjs = new LinkedList<Destructable>(); // zu entfernende Ziele
	
	/**
	 * Zeichnen des Bildschirms in Abh�ngigkeit des gameStates.
	 * <li> Background + Men� im gameState "menu"</li>
	 * <li> Background + Objekte im gameState "game"</li>
	 * <li> Background + Highscore Liste im gameState "highscore"</li>
	 */
	synchronized void render() {
		log.info("Render Background");
		if(gameState == state.game) {
			log.info("Render Game");
			for(Destructable i:objs) {
				i.render();
			}
			for(Destructable i:removeObjs) { // Abgeballerte Ziele entfernen. Bzw. Ziele, die schon zu lange ungetroffen umherirren.
				objs.remove(i);
			}
			removeObjs.clear();
		} else if(gameState == state.menu) {
			log.info("Render Menu");
		} else {
			log.info("Render Highscore");
			System.exit(0); // Logoutput �berschaubar halten.
		}
	};
	
	synchronized void hit(Point p) {};
	
	/**
	 * Diese Methode �berpr�ft ob die Spielrunde um ist. Ausserdem f�gt sie gegebenenfalls neue Ziele zur obj-Liste hinzu.
	 */
	synchronized void checkStatus() {
		if(gameState == state.game) { // W�hrend der Runde wird �berpr�ft ob die Zeit aus ist und ob Ziele hinzugef�gt werden m�ssen.
		if(System.currentTimeMillis() - roundStart >= Settings.ROUND_TIME_MS) { // Runde beenden, wenn die Spielzeit rum ist
			nextGameState(); // Highscore anzeigen
		} else { // ggf. neue Ziele erzeugen
			log.info("Populate targets");
			log.info("Entfernen von <alten> Zielen");
		}} else {
		}
	};
	
	/**
	 * Spiel starten.
	 */
	public void initializate() {
		log.info("Spiel initialisieren");
		log.info("Mit Wiimote verbinden");
		log.info("Name auf <player> setzen, Punkte auf 0, streak auf 0");
		log.info("Hintergrund malen");
		log.info("Men� zeichnen");
		// Hier eigentlich auf Mausklick reagiert:	
			log.info("Start geklickt!");
			log.info("Gamestat auf game setzen.");
			nextGameState();
		while(true){
			checkStatus();
			render();
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
	public void removeObj(Destructable destructable){
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
			log.info("Runde beginnt!");
			roundStart = System.currentTimeMillis(); //Zeitpunkt, wann die Runde beginnt
		} else if(gameState == state.game){
			gameState = state.highscore;
			log.info("Runde ist vorbei!");
		} else {
			gameState = state.menu;
			log.info("Nach dem Highscore wieder ins Men�!");
		}
		
	}
	
}
