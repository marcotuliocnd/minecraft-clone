package com.minecraft.core;

import org.lwjgl.opengl.Display;

import com.minecraft.models.RawModel;
import com.minecraft.models.TexturedModel;
import com.minecraft.shaders.StaticShader;
import com.minecraft.textures.ModelTexture;

public class Main {
	
	public static int framesPerSecond = 0;
	
	private Loader loader;
	private Renderer renderer;
	private RawModel rawModel;
	private StaticShader staticShader;
	private TexturedModel texturedModel;
	
	public Main() {
		DisplayManager.create();
		
		this.loader = new Loader();
		this.renderer = new Renderer();
		this.staticShader = new StaticShader();
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
		
		float[] textureCoordinates = {
				0,0,				//Vertice 0
				0,1,				//Vertice 1
				1,1,				//Vertice 2
				1,0 				//Vertice 3
		};
		
		
		this.rawModel = loader.loadToVao(vertices, indices, textureCoordinates);
		ModelTexture texture = new ModelTexture(loader.loadTexture("grass.png"));
		this.texturedModel = new TexturedModel(this.rawModel, texture);
		
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

		this.staticShader.start();
		this.renderer.render(this.texturedModel);
		this.staticShader.stop();
		
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
		
		
		this.staticShader.cleanUp();
		this.loader.cleanUp();
		DisplayManager.close();
	}
	
}
