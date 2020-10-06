package com.minecraft.shaders;

public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE_PATH = "src/com/minecraft/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE_PATH = "src/com/minecraft/shaders/fragmentShader.txt";

	public StaticShader() {
		super(VERTEX_FILE_PATH, FRAGMENT_FILE_PATH);
	}

	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	
	
}
