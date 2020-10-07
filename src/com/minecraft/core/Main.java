package com.minecraft.core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.minecraft.entities.Camera;
import com.minecraft.entities.Entity;
import com.minecraft.models.RawModel;
import com.minecraft.models.TexturedModel;
import com.minecraft.shaders.StaticShader;
import com.minecraft.textures.ModelTexture;
import com.minecraft.utils.OpenSimplexNoise;

public class Main {
	
	public static int framesPerSecond = 0;
	
	private Loader loader;
	private Renderer renderer;
	private RawModel rawModel;
	private StaticShader staticShader;
	private TexturedModel texturedModel;
	private List<Entity> entity = new ArrayList<Entity>();
	private Camera camera = new Camera();
	
	private static final int WIDTH = 64;
	private static final int HEIGHT = 64;
	private static final double FEATURE_SIZE = 24;
	
	public Main() {
		DisplayManager.create();
		
		this.loader = new Loader();
		this.staticShader = new StaticShader();
		this.renderer = new Renderer(this.staticShader);
	}
	
	public void start() {
		
		OpenSimplexNoise noise = new OpenSimplexNoise();
		
		
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
				
				0	,	0,
				0	,	0.5f,
				0.5f,	0.5f,
				0.5f,	0,	
				
				0	,	0,
				0	,	0.5f,
				0.5f,	0.5f,
				0.5f,	0,
			
				0	,	0,
				0	,	0.5f,
				0.5f,	0.5f,
				0.5f,	0,
				
				0	,	0,
				0	,	0.5f,
				0.5f,	0.5f,
				0.5f,	0,
				
				0.5f,	0,	//Parte de cima
				1	,	0,
				1	,	0.5f,
				0.5f,	0.5f,
				
				0.5f,	0.5f,	//Parte de baixo
				1	,	0.5f,
				1	,	1,
				0.5f,	1

				
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
		
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				float value = (float) noise.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
				
				value += 1;
				value *= -10;
				value = (float) Math.round(value);
				
				this.entity.add(new Entity(this.texturedModel, new Vector3f(x, value - 5, y), 3, 0, 0, 1));
			}
		}
		
		this.run();
	}

	public static void main(String args[]) {
		Main main = new Main();	
		main.start();
	}
	
	public void tick() {
		this.camera.move();
		for(Entity entity:this.entity) {
			//entity.increaseRotation(1, 0, 0);
		}
	}
	
	public void render() {
		this.renderer.prepare();

		this.staticShader.start();
		this.staticShader.loadViewMatrix(this.camera);
		for(Entity entity:this.entity) {
			this.renderer.render(entity, this.staticShader);
		}
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
				System.out.println("FPS: " + framesPerSecond);
				framesPerSecond = 0;
				timer += 1000;
			}
		}
		
		
		this.staticShader.cleanUp();
		this.loader.cleanUp();
		DisplayManager.close();
	}
	
}
