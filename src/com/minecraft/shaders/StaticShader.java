package com.minecraft.shaders;

import org.lwjgl.util.vector.Matrix4f;

import com.minecraft.entities.Camera;
import com.minecraft.utils.Maths;

public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE_PATH = "src/com/minecraft/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE_PATH = "src/com/minecraft/shaders/fragmentShader.txt";
	
	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	
	public StaticShader() {
		super(VERTEX_FILE_PATH, FRAGMENT_FILE_PATH);
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}


	protected void getAllUniformLocations() {
		this.locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		this.locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
		this.locationViewMatrix = super.getUniformLocation("viewMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(this.locationTransformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(this.locationViewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(this.locationProjectionMatrix, projection);
	}
	
}
