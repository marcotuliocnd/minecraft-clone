package com.minecraft.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE_PATH = "src/com/minecraft/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE_PATH = "src/com/minecraft/shaders/fragmentShader.txt";
	
	private int locationTransformationMatrix;
	
	public StaticShader() {
		super(VERTEX_FILE_PATH, FRAGMENT_FILE_PATH);
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}


	protected void getAllUniformLocations() {
		this.locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(this.locationTransformationMatrix, matrix);
	}
	
}
