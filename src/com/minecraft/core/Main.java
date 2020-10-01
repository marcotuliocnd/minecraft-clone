package com.minecraft.core;

import org.lwjgl.opengl.Display;

public class Main {

	public static void main(String args[]) {
		DisplayManager.create();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,

				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f,
				-0.5f, 0.5f, 0f,
		};
		
		RawModel rawModel = loader.loadToVao(vertices);
			
		while (!Display.isCloseRequested()) {
			renderer.prepare();

			renderer.render(rawModel);
			DisplayManager.update();
		}
		
		loader.cleanUp();
		DisplayManager.close();
	}
	
}
