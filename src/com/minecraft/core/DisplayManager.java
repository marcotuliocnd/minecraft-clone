package com.minecraft.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	public static final int MAX_FPS = 60;
	
	public static void create() {
		ContextAttribs contextAttributes = new ContextAttribs(3,2);
		contextAttributes.withForwardCompatible(true);
		contextAttributes.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), contextAttributes);
			Display.setTitle("Minecraft");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}
	
	public static void update() {
		Display.sync(MAX_FPS);
		Display.update();
	}
	
	public static void close( ) {
		Display.destroy();
	}

}
