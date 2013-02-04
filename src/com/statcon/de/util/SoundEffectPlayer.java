package com.statcon.de.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Klasse die sich um das Abspielen von Sounds kümmert. Zum Abspielen wird jeweils ein eigener Thread erstellt.
 * 
 * (http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java)
 * 
 * @author Basti Hoffmeister
 *
 */
public class SoundEffectPlayer {

	/**
	 * Immer wenn ein Ziel getroffen wird.
	 */
	public static synchronized void hitSound() {
		new Thread(new Runnable() { 
					public void run() {
						try {
							Clip clip = AudioSystem.getClip();
							AudioInputStream inputStream = AudioSystem
									.getAudioInputStream(SoundEffectPlayer.class
											.getResourceAsStream("/sounds/hit.wav"));
							clip.open(inputStream);
							clip.start();
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
				}).start();
	}
	
	/**
	 * Wenn man daneben schiesst. 
	 */
	public static synchronized void failSound() {
		new Thread(new Runnable() { 
					public void run() {
						try {
							Clip clip = AudioSystem.getClip();
							AudioInputStream inputStream = AudioSystem
									.getAudioInputStream(SoundEffectPlayer.class
											.getResourceAsStream("/sounds/fail.wav"));
							clip.open(inputStream);
							clip.start();
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
				}).start();
	}
	
	/**
	 * Nachladen-sound.
	 */
	public static synchronized void reloadSound() {
		new Thread(new Runnable() { 
					public void run() {
						try {
							Clip clip = AudioSystem.getClip();
							AudioInputStream inputStream = AudioSystem
									.getAudioInputStream(SoundEffectPlayer.class
											.getResourceAsStream("/sounds/reload1.wav"));
							clip.open(inputStream);
							clip.start();
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
				}).start();
	}
}
