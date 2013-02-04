package com.statcon.de.util;

import java.awt.Dimension;

public class Settings {

	public static final int MIN_TARGETS = 3;
	public static final int ROUND_TIME_MS = 10000;
	
	public static final String DEFAULT_PLAYER_NAME = "player000";
	
	public static final Dimension SMALL_TARGET = new Dimension(32, 32);
	public static final Dimension MEDIUM_TARGET = new Dimension(64, 64);
	public static final Dimension BIG_TARGET = new Dimension(96, 96);
	
	public static final Dimension SCREEN_SIZE = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
}
