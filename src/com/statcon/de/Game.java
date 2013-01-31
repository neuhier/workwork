package com.statcon.de;

import java.awt.Point;

import com.sun.tools.javac.util.List;

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
		// Ist die Zeit um? -> GameState zu ->  Score �ndern
		// Sind zu wenige Ziele da? -> neue Ziele hinzuf�gen
	};
	
	public void initializate() {};
	// private State getState();
	public void moveObjects(){
		// F�r alle Objekte: Bewegen
	};
	public void removeObj(Destructable destructable){
	};
	
}
