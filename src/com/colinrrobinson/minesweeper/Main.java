package com.colinrrobinson.minesweeper;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "minesweeper";
		//cfg.useGL20 = false;
		cfg.width = 1200;
		cfg.height = 640;
		new LwjglApplication(new Minesweeper(), cfg);
	}
}
