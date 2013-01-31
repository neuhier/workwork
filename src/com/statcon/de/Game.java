package com.statcon.de;

import java.awt.Point;

import com.sun.tools.javac.util.List;

/**
 * Diese Klasse repräsentiert ein Spiel. Das Spiel kann in verschiedenen Zuständen sein:
 * <li> Menu: Das Hauptmenü enthält die Möglichkeit einen Namen einzugeben sowie das Spiel zu starten.</li>
 * <li> Game: Das Spiel läuft eine bestimmte Zeitspanne lang. Während dessen werden beständig neue Ziele generiert bzw. entfernt.</li>
 * <li> Score: Wenn die Zeit des Spiels um ist, wird der Highscore angezeigt. Auf Knopfdruck kommt man in den Zustand "Menü".</li>
 * 
 * @author Basti Hoffmeister
 *
 */
public class Game {

	// private State gameState;
	private int kills = 0;
	private int streak = 0;
	List <Destructable> objs;
	List <Destructable> removeObjs;
	
	synchronized void render() {
		// Je nach Gamestate: Menu = "male Menu" 
		// In Game: 
		// Jedes Objekt: -> obj.render
		// Jedes Objekt auf der Remove-Liste -> entfernen
		// remove liste clearen.
	};
	synchronized void hit(Point p) {};
	synchronized void checkStatus() {
		// Ist die Zeit um? -> GameState zu ->  Score ändern
		// Sind zu wenige Ziele da? -> neue Ziele hinzufügen
	};
	
	public void initializate() {};
	// private State getState();
	public void moveObjects(){
		// Für alle Objekte: Bewegen
	};
	public void removeObj(Destructable destructable){
	};
	
}
