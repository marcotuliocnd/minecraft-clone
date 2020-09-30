package com.minecraft.core;

import org.lwjgl.opengl.Display;

public class Main {

	public static void main(String args[]) {
		DisplayManager.create();
		
		Main.run();
	}
	
	private static void run() {
		while (!Display.isCloseRequested()) {
			DisplayManager.update();
		}
		
		DisplayManager.close();
	}
}
