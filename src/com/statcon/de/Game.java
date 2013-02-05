package com.statcon.de;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.statcon.de.menu.Keyboard;
import com.statcon.de.util.GameResult;
import com.statcon.de.util.Settings;
import com.statcon.de.util.SoundEffectPlayer;

/*
 * 
 * TODOS:
 *
 * - Einschussöcher beim Schießen
 * - Vernünftige Bewegung der Zielscheiben
 * - Highscore: Liste mit den Top x anzeigen
 * 
 */

/**
 * Diese Klasse repräsentiert ein Spiel. Das Spiel kann in verschiedenen
 * Zuständen sein: <li>Menu: Das Hauptmenü enthält die Möglichkeit einen Namen
 * einzugeben sowie das Spiel zu starten.</li> <li>Game: Das Spiel läuft eine
 * bestimmte Zeitspanne lang. Während dessen werden beständig neue Ziele
 * generiert bzw. entfernt.</li> <li>Score: Wenn die Zeit des Spiels um ist,
 * wird der Highscore angezeigt. Auf Knopfdruck kommt man in den Zustand "Menü".
 * </li>
 * 
 * @author Basti Hoffmeister
 * 
 */
public class Game extends JPanel {

	private int bullets = 6; // Wie viel mundition hat man noch?
	private int kills = 0; // Wie viele Kills
	private int streak = 0; // Wie viele Kills am Stück
	private int score = 0; // Punkte in diesem Spiel
	private String name = Settings.DEFAULT_PLAYER_NAME; // Name des Spielers für Highscore
	public ArrayList<GameResult> results; // Liste mit allen bisherigen Ergebnissen
	
	
	private String gender = "NA";
	private long roundStart = 0; // Zeitpunkt an dem eine Spielrunde startet
	private long paintStamp; // Zeitzähler um die anzahl der Frames / Sekunde zu
								// kontrollieren

	private Keyboard keyboard;
	public Dimension screenRes;
	
	private enum state {
		menu, game, highscore
	};


	Image background = new ImageIcon(this.getClass().getResource("/images/background_01.png")).getImage();
	
	public state gameState = state.menu;

	CopyOnWriteArrayList<Destructable> objs = new CopyOnWriteArrayList<Destructable>(); // aktuell
																						// vorhandene
																						// Ziele
	static CopyOnWriteArrayList<Destructable> removeObjs = new CopyOnWriteArrayList<Destructable>(); // zu
																										// entfernende
																										// Ziele

	/**
	 * Zeichnen des Bildschirms in Abhängigkeit des gameStates. <li>Background +
	 * Menü im gameState "menu"</li> <li>Background + Objekte im gameState
	 * "game"</li> <li>Background + Highscore Liste im gameState "highscore"</li>
	 */
	synchronized void render() {
		repaint();
	};
	
	/**
	 * Spiel starten.
	 */
	public void initializate() {
		// log.info("Mit Wiimote verbinden");

		Toolkit tk = Toolkit.getDefaultToolkit();
		screenRes = tk.getScreenSize();
		keyboard = new Keyboard(screenRes);

		results = new ArrayList<GameResult>();
		results.add(new GameResult("", "", 0)); // Leerer eintrag
		
		this.addMouseListener(new MouseAdapter() { // Mausklicks registrieren
			public void mousePressed(MouseEvent e) {
				if(gameState == state.game) {
					if (e.getButton() == MouseEvent.BUTTON1) { // Linksklick
						hit(e.getPoint());
					} else if (e.getButton() == MouseEvent.BUTTON3) { // Rechtsklick
						SoundEffectPlayer.reloadSound();
						bullets = 6;
					}
				} else if (gameState == state.menu) {
					String message = keyboard.hit(e.getPoint());
					if(message == "<-") {
						try{
						name = name.substring(0, name.length()-1);
						} catch(StringIndexOutOfBoundsException ex) {
							// Man kann halt nix mehr löschen, wenn schon alles vom Namen gelöscht wurde.
						}
					} else if(message == "Play") {
						nextGameState();
					} else if (message== "Male" || message == "NA" || message == "Female") {
						gender = message;
					} else {
						name = name.concat(message);
					}
				} else {
					if(e.getButton() == MouseEvent.BUTTON3){
						nextGameState();						
					}
				}
			};
		});

		paintStamp = System.currentTimeMillis();
		while (true) {
			render();
			if (System.currentTimeMillis() - paintStamp > 1) { // nur alle 1/100 sec bewegen oder so
				moveObjects();
			}
			checkStatus();
			paintStamp = System.currentTimeMillis();
		}
	};

	/**
	 * Wird ausgelöst, immer wenn ein Button auf dem Wiimote gedrückt wird.
	 * 
	 * @param p
	 *            - die Position des "Cursors" im Moment als der Button gedrückt
	 *            wird.
	 */
	synchronized void hit(Point p) {
		if (bullets >= 1) {
			bullets--;
			boolean anyHit = false;
			for (Destructable i : objs) {
				if (i.hit(p)) { // Wenn ein existierendes Objekt getroffen wurde
					anyHit = true;
				}
			}
			if (anyHit) { // Treffer
				SoundEffectPlayer.hitSound();
				kills++;
				streak++;
				score = score + 10 * streak * streak;
			} else { // Fehlschuss
				SoundEffectPlayer.failSound();
				streak = 0;
			}
		} else { // keine Ammo mehr
			// no-ammo-sound abspielen
		}
	};

	/**
	 * Diese Methode überprüft ob die Spielrunde um ist. Ausserdem fügt sie
	 * gegebenenfalls neue Ziele zur obj-Liste hinzu.
	 */
	synchronized void checkStatus() {
		if (gameState == state.game) { // Während der Runde wird überprüft ob
										// die Zeit aus ist und ob Ziele
										// hinzugefügt werden müssen.
			if (System.currentTimeMillis() - roundStart >= Settings.ROUND_TIME_MS) { 
				nextGameState(); // Highscore anzeigen
			} else { // ggf. neue Ziele erzeugen
				populate();
				for (Destructable i : removeObjs) { 
					objs.remove(i);
				}
				removeObjs.clear();
			}
		} else {
		}
	};

	/**
	 * Bewegen der lebenden Ziele.
	 */
	public void moveObjects() {
		for (Destructable i : objs) {
			i.move();
		}
	};

	/**
	 * Entfernen von Objekten die verschwinden sollen. Entweder weil sie
	 * abgeschossen wurden, oder weil sie schon zu lange "leben".
	 * 
	 * @param destructable
	 */
	public static void removeObj(Destructable destructable) {
		removeObjs.add(destructable);
	};

	/**
	 * Change the game state. If the game is currently in "menu"-mode it starts
	 * the round. This happens if the player presses the "play"-button. If the
	 * game is in "game"-mode the gameState changes to "highscore". This happens
	 * after 30 sec of playing. If the game is in "highscore"-mode the gameState
	 * changes to "menu". This happens whenever a button is pressed in
	 * "highscore"-mode.
	 */
	private void nextGameState() {
		if (gameState == state.menu) {
			roundStart = System.currentTimeMillis(); // Zeitpunkt, wann die Runde beginnt
			gameState = state.game;
		} else if (gameState == state.game) {
			results.add(new GameResult(name, gender, score)); // Neues Ergebniss in die Liste eintragen.
			Collections.sort(results, new Comparator<GameResult>(){ // Sortieren
				public int compare(GameResult r1, GameResult r2) {
					return(r1.score-r2.score);
				}
			});
			if(results.size() > 10) { // Nur die Top 10 abspeichern
				results.remove(0);
			}
			gameState = state.highscore;
		} else {
			name = Settings.DEFAULT_PLAYER_NAME;
			score = 0;
			kills = 0;
			streak = 0;
			bullets = 6;
			gameState = state.menu;
		}

	}

	/**
	 * Je geringer die Anzahl der Ziele, desto höher die Wkt. ein neues
	 * hinzuzufügen.
	 */
	private void populate() {
		Random gen = new Random();
		switch (objs.size()) {
		case 0:
			objs.add(new Destructable(screenRes));
		case 1:
			if (gen.nextInt(6) == 0) {
				objs.add(new Destructable(screenRes));
			}
			;
		case 2:
			if (gen.nextInt(5) == 0) {
				objs.add(new Destructable(screenRes));
			}
			;
		case 3:
			if (gen.nextInt(4) == 0) {
				objs.add(new Destructable(screenRes));
			}
			;
		case 4:
			if (gen.nextInt(3) == 0) {
				objs.add(new Destructable(screenRes));
			}
			;
		case 5:
			if (gen.nextInt(2) == 0) {
				objs.add(new Destructable(screenRes));
			}
			;
		default:
			;
		}
	}

	/**
	 * Paint-Methode für die Spiel Klasse
	 */
	public void paint(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		// Hintergrund immer malen
		g2d.drawImage(background, 0, 0, (int)screenRes.getWidth(), (int)screenRes.getHeight(), null); 
		//TODO: Hintergrund in verschiedenen auflösungen zur Verfügung stellen
		
		if (gameState == state.game) { // Spiel-Modus
		
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			
			// Bullets
			g2d.setPaint(Color.white);
			for (int i = 1; i <= 6; i++) {
				if (i > bullets) {
					g2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, 0.1f)); // Transparent
				}
				g2d.fill(new RoundRectangle2D.Double(screenRes.getWidth() - i * 45, 60, 30, 60, 10, 10));
				g2d.fillArc((int) screenRes.getWidth() - i * 45, 30,
						30, 50, 0, 180);
			}

			// Score & Name & Highscore
			g2d.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.75f)); 
			g.setFont(new Font("Arial Black", Font.BOLD, 30));
			g.setColor(Color.white);
			g.drawString("Player: " + name, 20, 40);
			g.drawString("Score: " + score, 20, 80);
			g.drawString("Highscore: " + results.get(results.size()-1).score + "(" + results.get(results.size()-1).name + ")", 20, 120);
			
			
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); 

			
			// Ziele
			for (Destructable i : objs) {
				i.render(g2d);
			}

			// Rest Zeit anzeigen
			int timeLeft = (int)(Settings.ROUND_TIME_MS - (System.currentTimeMillis() - roundStart));
			FontMetrics fm = g.getFontMetrics();
			String timeLeftString = ""+timeLeft; // Verbleibende Zeit in der Form: "10:334" anzeigen. Wobei am Anfang die Sekunden stehen, dahinter die Millisekunden
			try{
				timeLeftString = timeLeftString.substring(0, timeLeftString.length()-3)+ ":" + timeLeftString.substring(timeLeftString.length()-3, timeLeftString.length());
			} catch (StringIndexOutOfBoundsException e) {
				// Passiert halt, wenn die Zeit zu klein wird. Dann kann man den String nicht mehr splitten.
			}
			Rectangle2D r = fm.getStringBounds(timeLeftString, g);
			g.setFont(new Font("Arial Black", Font.BOLD, 50));
			if(timeLeft >= 5000) {
				g.setColor(Color.white);
			} else if (timeLeft < 5000 & timeLeft > 3000) {
				g.setColor(Color.yellow);
			} else {
				g.setColor(Color.red);
			}
			g.drawString(timeLeftString, (int) (screenRes.width/2 - r.getWidth()/2), 40);
			
		} else if (gameState == state.menu) { // Menü zeichnen

			keyboard.render(g2d);

			// Spieler Namen zeichnen (zentriert)
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
			g.setFont(new Font("Arial Black", Font.BOLD, 100));
			g.setColor(Color.black);
			FontMetrics fm = g.getFontMetrics(); // Wie lang ist der Name?  
			Rectangle2D r = fm.getStringBounds(name, g); // WIe lang ist der Name?
			g.drawString(name, (int) (screenRes.width/2 - r.getWidth()/2), screenRes.height/2-50);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		} else if(gameState == state.highscore) { // Highscores
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g.setFont(new Font("Arial Black", Font.BOLD, 50));
			g.setColor(Color.black);
			String highscore_msg = "Your score is " + score + "," + System.getProperty("line.separator") + name + "!"; 
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(highscore_msg, g);
			g.drawString(highscore_msg, (int) (screenRes.width/2 - r.getWidth()/2), screenRes.height/2-50);
			// Weiter mit Rechtsklick!
			g.setFont(new Font("Arial Black", Font.BOLD, 30));
			g.setColor(Color.gray);
			String proceed = "Right-Click to proceed!";
			g.drawString(proceed, (int) (screenRes.width - 400), screenRes.height-20);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			
			// Highscores anzeigen
			if(results.size() > 0) { // erst wenn auch ein paar in der Liste sind.
			int xpos = (int) (screenRes.getWidth()/2 - 200);
			int ybase = screenRes.height/2;
					
			int max = Math.min(results.size(), Settings.MAX_NUMBER_IN_HIGHSCORES);
			for(int i = max-1; i > 0; i--) {
				results.get(i).render(g2d, xpos, ybase + (int)50*(max-i), 100, 100);
				}
			}			
		}
	}
}
