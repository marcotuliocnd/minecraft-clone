package com.minecraft.core;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.minecraft.entities.Camera;
import com.minecraft.entities.Entity;
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
	private Entity entity;
	private Camera camera = new Camera();
	
	public Main() {
		DisplayManager.create();
		
		this.loader = new Loader();
		this.staticShader = new StaticShader();
		this.renderer = new Renderer(this.staticShader);
	}
	
	public void start() {
		/*
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
		*/
		float[] vertices = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
				
		};
		
		float[] textureCoordinates = {
				
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0

				
		};
		
		int[] indices = {
				0,1,3,	
				3,1,2,	
				4,5,7,
				7,5,6,
				8,9,11,
				11,9,10,
				12,13,15,
				15,13,14,	
				16,17,19,
				19,17,18,
				20,21,23,
				23,21,22

		};
		
		this.rawModel = loader.loadToVao(vertices, indices, textureCoordinates);
		ModelTexture texture = new ModelTexture(loader.loadTexture("grass.png"));
		this.texturedModel = new TexturedModel(this.rawModel, texture);
		
		this.entity = new Entity(this.texturedModel, new Vector3f(0, 0, -1), 3, 0, 0, 1);
		
		
		
		this.run();
	}

	public static void main(String args[]) {
		Main main = new Main();	
		main.start();
	}
	
	public void tick() {
		this.camera.move();
		//this.entity.increasePosition(0, 0, -0.1f);
		this.entity.increaseRotation(1, 1, 1);
	}
	
	public void render() {
		this.renderer.prepare();

		this.staticShader.start();
		this.staticShader.loadViewMatrix(this.camera);
		this.renderer.render(this.entity, this.staticShader);
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
