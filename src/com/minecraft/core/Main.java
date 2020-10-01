package com.minecraft.core;

import org.lwjgl.opengl.Display;

public class Main {
	
	public static int framesPerSecond = 0;
	
	private Loader loader;
	private Renderer renderer;
	private RawModel rawModel;
	
	public Main() {
		DisplayManager.create();
		
		this.loader = new Loader();
		this.renderer = new Renderer();
	}
	
	public void start() {
		float[] vertices = {
				-0.5f, 0.5f, 0f,	// vertice 0
				-0.5f, -0.5f, 0f,	// vertice 1
				0.5f, -0.5f, 0f,	// vertice 2
				0.5f, 0.5f, 0f		// vertice 3
		};
		
		int[] indices = {
				0, 1, 3, // top triangle (vertice 0, vertice 1, vertice 3)
				3, 1, 2	 // bottom triangle (vertice 3, vertice 1, vertice 2)
		};
		
		this.rawModel = loader.loadToVao(vertices, indices);
		
		this.run();
	}

	public static void main(String args[]) {
		Main main = new Main();	
		main.start();
	}
	
	public void tick() {
		
	}
	
	public void render() {
		this.renderer.prepare();

		this.renderer.render(rawModel);
		
		DisplayManager.update();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double ns = 1000000000 / DisplayManager.MAX_FPS;
		double delta = 0;
		double timer = System.currentTimeMillis();
		
		while (!Display.isCloseRequested()) {	
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				this.tick();
				this.render();
				framesPerSecond += 1;
				delta -= 1;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				framesPerSecond = 0;
				timer += 1000;
			}
		}
		
		this.loader.cleanUp();
		DisplayManager.close();
	}
	
}
