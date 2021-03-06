package com.mygdx.game.desktop;

import org.lwjgl.input.Keyboard;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.TheHouseOfCrimes;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ProjectRiddle";
		config.width = 770;
		new LwjglApplication(new TheHouseOfCrimes(), config);
		Keyboard.enableRepeatEvents(true); //dejar pulsado los botones
	}
}
